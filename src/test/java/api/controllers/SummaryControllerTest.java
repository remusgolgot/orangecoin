package api.controllers;

import com.btc.api.controllers.SummaryController;

import com.btc.api.model.ResponseModel;
import com.btc.api.model.Summary;
import com.btc.api.services.SummaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SummaryControllerTest {

    @Mock
    private SummaryService summaryService;

    @InjectMocks
    private SummaryController summaryController;

    @Test
    public void systemServiceReturnsActualResult() throws Exception {
        Summary summary = new Summary();
        summary.setNrUtxos(12535L);
        when(summaryService.getSummary()).thenReturn(summary);
        ResponseModel<Summary> result = summaryController.getSummary();

        assertTrue(result.getStatus());
        assertEquals(1, result.getCount());
        assertNotNull(result.getData());
        assertEquals("", result.getMessage());
        assertEquals(12535L, result.getData().getNrUtxos());
    }

}
