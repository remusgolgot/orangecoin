package api.services;

import com.btc.api.services.PriceService;
import com.btc.api.services.SystemService;
import com.btc.api.model.Price;
import com.btc.model.Occurrence;
import com.btc.model.SystemInput;
import com.btc.model.SystemResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SystemServiceTest {

    @InjectMocks
    private SystemService systemService;

    @Mock
    private PriceService priceService;

    @Test
    public void getSystemResultNoInput() throws Exception {

        SystemInput systemInput = new SystemInput();
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertNull(systemResult);
    }

    @Test
    public void getSystemResultSimple() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1l, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2l, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3l, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(1515.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(1515.0, systemResult.getOverall());
        assertEquals(10.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultSimpleWithFullMode() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1l, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2l, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3l, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        systemInput.setMode("full");
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        List<Occurrence> occurrenceList = systemResult.getOccurrenceList();
        assertNotNull(occurrenceList);
        assertEquals("2020-01-02", occurrenceList.get(0).getDate());
        assertEquals(15150.0, occurrenceList.get(0).getPriceAtCondition());
        assertEquals(16665.0, occurrenceList.get(0).getPriceAtTarget());
    }

    @Test
    public void getSystemResultSimpleNoResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1l, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2l, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3l, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(10.01);
        systemInput.setMax(11.00);
        SystemResult systemResult = systemService.getSystemResult(systemInput);
        assertNull(systemResult);
    }

    @Test
    public void getSystemResultWithTarget() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1l, "2020-01-01", 15000.00, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2l, "2020-01-02", 15150.00, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3l, "2020-01-03", 16665.00, 30000.0, 250000.0, 1515.0, 10.0));
        prices.add(new Price(4l, "2020-01-04", 16498.35, 30000.0, 250000.0, -166.65, -1.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        systemInput.setTarget(2);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(2, systemResult.getTarget());
        assertEquals(1348.0, systemResult.getAverageUp());  // rounded
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(1348.0, systemResult.getOverall());    // rounded
        assertEquals(8.9, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultMultipleOccurrencesLastOneIsIgnored() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1l, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2l, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3l, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        prices.add(new Price(4l, "2020-01-04", 20000.0, 30000.0, 250000.0, 3335.0, 20.01));
        prices.add(new Price(5l, "2020-01-05", 20400.0, 30000.0, 250000.0, 400.0, 2.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(2.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(1515.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(1515.0, systemResult.getOverall());
        assertEquals(10.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultMultipleOccurrences() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1l, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2l, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3l, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        prices.add(new Price(4l, "2020-01-04", 20000.0, 30000.0, 250000.0, 3335.0, 20.01));
        prices.add(new Price(5l, "2020-01-05", 20400.0, 30000.0, 250000.0, 400.0, 2.0));
        prices.add(new Price(6l, "2020-01-06", 20400.0, 30000.0, 250000.0, 0.0, 0.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(2.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(2, systemResult.getNrOccurrences());
        assertEquals(2, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(758.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(1515.0, systemResult.getOverall());
        assertEquals(5.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    // one down occurrence
    // multiple down occurrences
    // one up + one down
    // multiple up + multiple down
    // min = max (exacta)
    // target = 2
    // target = 7
    // timespan = 3
    // timespan = 7
    // streak = 1 (explicit)
    // streak = 3
    // streak = 7
    // from explicit
    // to explicit
    // from + to
    // from + to no results

}
