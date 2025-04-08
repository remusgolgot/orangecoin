package api.services;

import com.btc.api.dao.PriceDAO;
import com.btc.api.model.Price;
import com.btc.api.services.PriceService;
import com.btc.model.PriceStats;
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
public class PriceServiceTest {

    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceDAO priceDAO;

    @Test
    public void getPriceStats() {

        List<Price> priceList = new ArrayList<>();
        priceList.add(new Price(1L, "2020-01-01", 17.00,0.0,0.0,0.0,1.0));
        priceList.add(new Price(2L, "2020-01-02", 18.00,0.0,0.0,0.0,1.0));
        priceList.add(new Price(3L, "2020-01-03", 19.00,0.0,0.0,0.0,2.0));
        priceList.add(new Price(4L, "2020-01-04", 16.00,0.0,0.0,0.0,-3.0));
        when(priceDAO.list(Price.class)).thenReturn(priceList);

        PriceStats priceStats = priceService.getPriceStats();

        assertNotNull(priceStats);
        assertEquals(15.79, priceStats.getAthDrawdown());
        assertEquals(17.5, priceStats.getAveragePrice());
        assertEquals(18.0, priceStats.getMedianPrice());
        assertEquals(3, priceStats.getUpDays());
        assertEquals(1, priceStats.getDownDays());
        assertEquals(1, priceStats.getDaysSinceATH());
        assertEquals(19.00, priceStats.getAth());
    }

}
