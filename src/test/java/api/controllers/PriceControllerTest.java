package api.controllers;

import com.btc.api.controllers.PriceController;
import com.btc.api.model.Price;
import com.btc.api.model.ResponseModel;
import com.btc.api.services.PriceService;
import com.btc.model.PriceStats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceControllerTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @Test
    public void priceAt() {

        Price price = new Price(1L, "2020-01-01", 5501.13, 0.0, 0.0, -5.4, 0.0);
        when(priceService.getPriceAt("2020-01-01")).thenReturn(price);
        ResponseModel<Price> result = priceController.getPriceAt("2020-01-01");

        assertEquals(1, result.getCount());
        assertEquals(5501.13, result.getData().getPrice());
        assertEquals(-5.4, result.getData().getVariation());
        assertEquals(true, result.getStatus());
    }

    @Test
    public void getStats() {

        PriceStats priceStats = new PriceStats();
        priceStats.setAveragePrice(1000.0);
        when(priceService.getPriceStats()).thenReturn(priceStats);
        ResponseModel<PriceStats> result = priceController.getStats();

        assertEquals(1, result.getCount());
        assertEquals(1000.0, result.getData().getAveragePrice());
        assertEquals(true, result.getStatus());
    }

    @Test
    public void getFrom() {

        PriceStats priceStats = new PriceStats();
        priceStats.setAveragePrice(1000.0);
        when(priceService.getPriceStatsFrom("2020-01-01")).thenReturn(priceStats);
        ResponseModel<PriceStats> result = priceController.getStatsFrom("2020-01-01");

        assertEquals(1, result.getCount());
        assertEquals(1000.0, result.getData().getAveragePrice());
        assertEquals(true, result.getStatus());
    }

    @Test
    public void getFromTo() {
        PriceStats priceStats = new PriceStats();
        priceStats.setAveragePrice(1000.0);
        when(priceService.getPriceStatsFromTo("2020-01-01", "2020-03-03")).thenReturn(priceStats);
        ResponseModel<PriceStats> result = priceController.getStatsFromTo("2020-01-01", "2020-03-03");

        assertEquals(1, result.getCount());
        assertEquals(1000.0, result.getData().getAveragePrice());
        assertEquals(true, result.getStatus());
    }


}
