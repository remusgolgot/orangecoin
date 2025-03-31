package api.services;

import com.btc.api.model.Price;
import com.btc.api.services.PriceService;
import com.btc.api.services.SystemService;
import com.btc.model.Occurrence;
import com.btc.model.SystemInput;
import com.btc.model.SystemResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.btc.api.messages.Responses.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private SystemService systemService;

    @Mock
    private PriceService priceService;

    @Test
    public void getSystemResultSimple() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
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
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
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
    public void getSystemResultSimpleWithNonFullMode() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        systemInput.setMode("fully");
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        List<Occurrence> occurrenceList = systemResult.getOccurrenceList();
        assertNull(occurrenceList);
    }

    @Test
    public void getSystemResultSimpleNoResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
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
        prices.add(new Price(1L, "2020-01-01", 15000.00, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.00, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.00, 30000.0, 250000.0, 1515.0, 10.0));
        prices.add(new Price(4L, "2020-01-04", 16498.35, 30000.0, 250000.0, -166.65, -1.0));
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
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        prices.add(new Price(4L, "2020-01-04", 20000.0, 30000.0, 250000.0, 3335.0, 20.01));
        prices.add(new Price(5L, "2020-01-05", 20400.0, 30000.0, 250000.0, 400.0, 2.0));
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
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        prices.add(new Price(4L, "2020-01-04", 20000.0, 30000.0, 250000.0, 3335.0, 20.01));
        prices.add(new Price(5L, "2020-01-05", 20400.0, 30000.0, 250000.0, 400.0, 2.0));
        prices.add(new Price(6L, "2020-01-06", 20400.0, 30000.0, 250000.0, 0.0, 0.0));
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

    @Test
    public void getSystemResultSimpleDownOccurrence() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 20000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 19000.0, 30000.0, 250000.0, -1000.0, -5.00));
        prices.add(new Price(3L, "2020-01-03", 17000.0, 30000.0, 250000.0, -2000.0, -10.53));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-5.5);
        systemInput.setMax(-4.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(0, systemResult.getUpOccurrences());
        assertEquals(1, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(0.0, systemResult.getAverageUp());
        assertEquals(-2000.0, systemResult.getAverageDown());
        assertEquals(-2000.0, systemResult.getOverall());
        assertEquals(-10.53, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultMultipleDownOccurrences() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 20000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 19000.0, 30000.0, 250000.0, -1000.0, -5.00));
        prices.add(new Price(3L, "2020-01-03", 18500.0, 30000.0, 250000.0, -500.0, -2.63));
        prices.add(new Price(4L, "2020-01-04", 17700.0, 30000.0, 250000.0, -800.0, -4.32));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-5.5);
        systemInput.setMax(-2.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(2, systemResult.getNrOccurrences());
        assertEquals(0, systemResult.getUpOccurrences());
        assertEquals(2, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(0.0, systemResult.getAverageUp());
        assertEquals(-650.0, systemResult.getAverageDown());
        assertEquals(-1300.0, systemResult.getOverall());
        assertEquals(-3.48, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultOneUpOneDown() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 20000.0, 30000.0, 250000.0, 100.0, -10.0));
        prices.add(new Price(2L, "2020-01-02", 19000.0, 30000.0, 250000.0, -1000.0, -5.00));
        prices.add(new Price(3L, "2020-01-03", 19950.0, 30000.0, 250000.0, 950.0, 5.0));
        prices.add(new Price(4L, "2020-01-04", 19000.0, 30000.0, 250000.0, -950.0, -4.76));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-5.5);
        systemInput.setMax(5.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(2, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(1, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(950.0, systemResult.getAverageUp());
        assertEquals(-950.0, systemResult.getAverageDown());
        assertEquals(0.0, systemResult.getOverall());
        assertEquals(0.12, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultMultipleUpMultipleDown() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 20000.0, 30000.0, 250000.0, 100.0, -10.0));
        prices.add(new Price(2L, "2020-01-02", 19000.0, 30000.0, 250000.0, -1000.0, -5.00));
        prices.add(new Price(3L, "2020-01-03", 19950.0, 30000.0, 250000.0, 950.0, 5.0));
        prices.add(new Price(4L, "2020-01-04", 19000.0, 30000.0, 250000.0, -950.0, -4.76));
        prices.add(new Price(5L, "2020-01-05", 19100.0, 30000.0, 250000.0, 100.0, 0.52));
        prices.add(new Price(6L, "2020-01-06", 18400.0, 30000.0, 250000.0, -700.0, -3.66));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-5.5);
        systemInput.setMax(5.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(4, systemResult.getNrOccurrences());
        assertEquals(2, systemResult.getUpOccurrences());
        assertEquals(2, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(525.0, systemResult.getAverageUp());
        assertEquals(-825.0, systemResult.getAverageDown());
        assertEquals(-600.0, systemResult.getOverall());
        assertEquals(-0.73, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultMinMaxEqualOneOccurrence() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 20000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 19000.0, 30000.0, 250000.0, -1000.0, -5.00));
        prices.add(new Price(3L, "2020-01-03", 19950.0, 30000.0, 250000.0, 950.0, 5.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-5.0);
        systemInput.setMax(-5.0);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(950.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(950.0, systemResult.getOverall());
        assertEquals(5.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultMinMaxEqualNoOccurrence() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 20000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 19000.0, 30000.0, 250000.0, -1000.0, -5.00));
        prices.add(new Price(3L, "2020-01-03", 19950.0, 30000.0, 250000.0, 950.0, 5.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-4.99);
        systemInput.setMax(-4.99);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertNull(systemResult);
    }

    @Test
    public void getSystemResultTargetOfTwo() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 18191.25, 30000.0, 250000.0, 1653.75, 10.0));
        prices.add(new Price(5L, "2020-01-05", 20010.375, 30000.0, 250000.0, 1819.125, 10.0));
        prices.add(new Price(6L, "2020-01-06", 16675.3125, 30000.0, 250000.0, -3335.0625, -20.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(4.6);
        systemInput.setMax(5.1);
        systemInput.setTarget(2);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(2, systemResult.getNrOccurrences());
        assertEquals(2, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(2, systemResult.getTarget());
        assertEquals(2957.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(5914.0, systemResult.getOverall());
        assertEquals(18.25, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultTargetOfThreeUpAndDown() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 18191.25, 30000.0, 250000.0, 1653.75, 10.0));
        prices.add(new Price(5L, "2020-01-05", 20010.375, 30000.0, 250000.0, 1819.125, 10.0));
        prices.add(new Price(6L, "2020-01-06", 16008.25, 30000.0, 250000.0, 1515.0, -25.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(4.6);
        systemInput.setMax(5.1);
        systemInput.setTarget(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(2, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(1, systemResult.getDownOccurrences());
        assertEquals(3, systemResult.getTarget());
        assertEquals(4260.0, systemResult.getAverageUp());
        assertEquals(-529.0, systemResult.getAverageDown());
        assertEquals(3731.0, systemResult.getOverall());
        assertEquals(11.92, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultTargetOfThreeAfterTheEndOfInterval() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 18191.25, 30000.0, 250000.0, 1653.75, 10.0));
        prices.add(new Price(5L, "2020-01-05", 20010.375, 30000.0, 250000.0, 1819.125, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(4.6);
        systemInput.setMax(5.1);
        systemInput.setTarget(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(2, systemResult.getNrOccurrences());
        assertEquals(2, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(3, systemResult.getTarget());
        assertEquals(3867.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(7733.0, systemResult.getOverall());
        assertEquals(24.03, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultTimespanOfThreeNoResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 18191.25, 30000.0, 250000.0, 1653.75, 10.0));
        prices.add(new Price(5L, "2020-01-05", 20010.375, 30000.0, 250000.0, 1819.125, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(4.6);
        systemInput.setMax(5.1);
        systemInput.setTimespan(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertNull(systemResult);
    }

    @Test
    public void getSystemResultTimespanOfThreeWithResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 150.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 15750.0, 30000.0, 250000.0, 0.0, 0.0));
        prices.add(new Price(4L, "2020-01-04", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(5L, "2020-01-05", 18191.25, 30000.0, 250000.0, 1653.75, 10.0));
        prices.add(new Price(6L, "2020-01-06", 20010.375, 30000.0, 250000.0, 1819.125, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(9.7);
        systemInput.setMax(10.3);
        systemInput.setTimespan(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(1654, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(1654.0, systemResult.getOverall());
        assertEquals(10.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultTimespanOfThreeWithResultsEndingAtTheLastOccurrenceMeansNoResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 150.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 15750.0, 30000.0, 250000.0, 0.0, 0.0));
        prices.add(new Price(4L, "2020-01-04", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(9.7);
        systemInput.setMax(10.3);
        systemInput.setTimespan(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertNull(systemResult);
    }

    @Test
    public void getSystemResultTimespanOfThreeWithResultsAndExplicitTargetFallingOnTheLastDay() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 150.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 15750.0, 30000.0, 250000.0, 0.0, 0.0));
        prices.add(new Price(4L, "2020-01-04", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(5L, "2020-01-05", 18191.25, 30000.0, 250000.0, 1653.75, 10.0));
        prices.add(new Price(6L, "2020-01-06", 20010.375, 30000.0, 250000.0, 1819.125, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(9.7);
        systemInput.setMax(10.3);
        systemInput.setTimespan(3);
        systemInput.setTarget(2);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(2, systemResult.getTarget());
        assertEquals(3473, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(3473.0, systemResult.getOverall());
        assertEquals(21.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultSimpleWithExplicitStreak() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        systemInput.setStreak(1);
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
    public void getSystemResultStreakOfThree() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 17364.375, 30000.0, 250000.0, 826.875, 5.0));
        prices.add(new Price(5L, "2020-01-05", 19100.8125, 30000.0, 250000.0, 1736.4375, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(4.6);
        systemInput.setMax(5.1);
        systemInput.setStreak(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(1736.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(1736.0, systemResult.getOverall());
        assertEquals(10.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultStreakOfThreeEndingOnLastDayYieldsNoResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 17364.375, 30000.0, 250000.0, 826.875, 5.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(4.6);
        systemInput.setMax(5.1);
        systemInput.setStreak(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertNull(systemResult);
    }

    @Test
    public void getSystemResultStreakOfThreeNoResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 16537.5, 30000.0, 250000.0, 0.0, 0.0));
        prices.add(new Price(5L, "2020-01-05", 17364.375, 30000.0, 250000.0, 826.875, 5.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(4.6);
        systemInput.setMax(5.1);
        systemInput.setStreak(3);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertNull(systemResult);
    }

    @Test
    public void getSystemResultStreakOfTwoAndTimespanOfTwo() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15750.0, 30000.0, 250000.0, 750.0, 5.0));
        prices.add(new Price(3L, "2020-01-03", 16537.5, 30000.0, 250000.0, 787.5, 5.0));
        prices.add(new Price(4L, "2020-01-04", 17364.375, 30000.0, 250000.0, 826.875, 5.0));
        prices.add(new Price(5L, "2020-01-05", 18232.59375, 30000.0, 250000.0, 868.21875, 5.0));
        prices.add(new Price(6L, "2020-01-06", 19000.0, 30000.0, 250000.0, 767.40625, 4.21));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(9.0);
        systemInput.setMax(21.0);
        systemInput.setStreak(2);
        systemInput.setTimespan(2);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(767.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(767.0, systemResult.getOverall());
        assertEquals(4.21, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultStreakOfTwoAndTimespanOfTwoDifferentValues() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15000.0, 30000.0, 250000.0, 0.0, 0.0));
        prices.add(new Price(3L, "2020-01-03", 16500.0, 30000.0, 250000.0, 1500.0, 10.0));
        prices.add(new Price(4L, "2020-01-04", 18150.0, 30000.0, 250000.0, 1650.0, 10.0));
        prices.add(new Price(5L, "2020-01-05", 18150.0, 30000.0, 250000.0, 0.0, 0.0));
        prices.add(new Price(6L, "2020-01-06", 17700.0, 30000.0, 250000.0, -450.0, -2.48));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(9.0);
        systemInput.setMax(12.0);
        systemInput.setStreak(2);
        systemInput.setTimespan(2);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(0, systemResult.getUpOccurrences());
        assertEquals(1, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(0.0, systemResult.getAverageUp());
        assertEquals(-450.0, systemResult.getAverageDown());
        assertEquals(-450.0, systemResult.getOverall());
        assertEquals(-2.48, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    @Test
    public void getSystemResultWithFromAndResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        systemInput.setFrom(formatter.parse("2020-01-01"));
        systemInput.setTo(formatter.parse("2020-01-03"));
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
    public void getSystemResultWithFromAndToWithNoResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-0.5);
        systemInput.setMax(-0.1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        systemInput.setFrom(formatter.parse("2020-01-01"));
        systemInput.setTo(formatter.parse("2020-01-03"));
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertNull(systemResult);
    }

    @Test
    public void getSystemResultWithFromAndNoToWithResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        systemInput.setFrom(formatter.parse("2020-01-01"));
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
    public void getSystemResultWithToAndNoFromWithResults() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(0.5);
        systemInput.setMax(1.5);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        systemInput.setTo(formatter.parse("2020-01-03"));
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
    public void getSystemResultOneOccurrenceTheFirstDay() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, 0.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(-0.5);
        systemInput.setMax(0.5);
        SystemResult systemResult = systemService.getSystemResult(systemInput);

        assertEquals(1, systemResult.getNrOccurrences());
        assertEquals(1, systemResult.getUpOccurrences());
        assertEquals(0, systemResult.getDownOccurrences());
        assertEquals(1, systemResult.getTarget());
        assertEquals(150.0, systemResult.getAverageUp());
        assertEquals(0.0, systemResult.getAverageDown());
        assertEquals(150.0, systemResult.getOverall());
        assertEquals(1.0, systemResult.getRoi());
        assertNull(systemResult.getOccurrenceList());
    }

    // exceptions are thrown in the tests below

    @Test
    public void getSystemResultNoInput() {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, -1.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        Exception exception = assertThrows(Exception.class, () -> systemService.getSystemResult(systemInput));
        assertEquals(MIN_MAX_NOT_PRESENT, exception.getMessage());
    }

    @Test
    public void getSystemResultFromIsAfterTo() throws Exception {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, -1.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        systemInput.setFrom(formatter.parse("2020-01-04"));
        systemInput.setTo(formatter.parse("2020-01-03"));

        Exception exception = assertThrows(Exception.class, () -> systemService.getSystemResult(systemInput));
        assertEquals(FROM_TO_DATES_WRONG, exception.getMessage());
    }

    @Test
    public void getSystemResultTargetIsNegative() {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, -1.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setTarget(-1);

        Exception exception = assertThrows(Exception.class, () -> systemService.getSystemResult(systemInput));
        assertEquals(NEGATIVE_TARGET, exception.getMessage());
    }

    @Test
    public void getSystemResultStreakIsNegative() {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, -1.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setStreak(-1);

        Exception exception = assertThrows(Exception.class, () -> systemService.getSystemResult(systemInput));
        assertEquals(NEGATIVE_STREAK, exception.getMessage());
    }

    @Test
    public void getSystemResultTimespanIsNegative() {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, -1.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setTimespan(-1);

        Exception exception = assertThrows(Exception.class, () -> systemService.getSystemResult(systemInput));
        assertEquals(NEGATIVE_TIMESPAN, exception.getMessage());
    }

    @Test
    public void getSystemResultTimespanIsTooHigh() {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, -1.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setTimespan(366);

        Exception exception = assertThrows(Exception.class, () -> systemService.getSystemResult(systemInput));
        assertEquals(TIMESPAN_TOO_HIGH, exception.getMessage());
    }

    @Test
    public void getSystemResultMinIsHigherThanMax() {

        List<Price> prices = new ArrayList<>();
        prices.add(new Price(1L, "2020-01-01", 15000.0, 30000.0, 250000.0, 100.0, -1.0));
        prices.add(new Price(2L, "2020-01-02", 15150.0, 30000.0, 250000.0, 150.0, 1.00));
        prices.add(new Price(3L, "2020-01-03", 16665.0, 30000.0, 250000.0, 1515.0, 10.0));
        when(priceService.getFullList()).thenReturn(prices);

        SystemInput systemInput = new SystemInput();
        systemInput.setMin(1.5);
        systemInput.setMax(1.2);

        Exception exception = assertThrows(Exception.class, () -> systemService.getSystemResult(systemInput));
        assertEquals(MIN_HIGHER_THAN_MAX, exception.getMessage());
    }

}
