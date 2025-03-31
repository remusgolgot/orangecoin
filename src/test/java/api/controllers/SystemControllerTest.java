package api.controllers;

import com.btc.api.controllers.SystemController;
import com.btc.api.model.ResponseModel;
import com.btc.api.services.SystemService;
import com.btc.model.SystemInput;
import com.btc.model.SystemResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.btc.api.messages.Responses.MIN_HIGHER_THAN_MAX;
import static com.btc.api.messages.Responses.SYSTEM_SERVICE_NO_RESULT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SystemControllerTest {

    @Mock
    private SystemService systemService;

    @InjectMocks
    private SystemController systemController;

    @Test
    public void systemServiceReturnsNoResult() throws Exception {
        SystemInput systemInput = new SystemInput();
        when(systemService.getSystemResult(systemInput)).thenReturn(null);
        ResponseModel<SystemResult> result = systemController.getSystemResult(systemInput);

        assertFalse(result.getStatus());
        assertEquals(0, result.getCount());
        assertNull(result.getData());
        assertEquals(SYSTEM_SERVICE_NO_RESULT, result.getMessage());
    }

    @Test
    public void systemServiceReturnsActualResult() throws Exception {
        SystemInput systemInput = new SystemInput();
        SystemResult systemResult = new SystemResult();
        systemResult.setOverall(10.1);
        when(systemService.getSystemResult(systemInput)).thenReturn(systemResult);
        ResponseModel<SystemResult> result = systemController.getSystemResult(systemInput);

        assertTrue(result.getStatus());
        assertEquals(1, result.getCount());
        assertNotNull(result.getData());
        assertEquals("", result.getMessage());
    }

    @Test
    public void systemServiceThrowsException() throws Exception {
        SystemInput systemInput = new SystemInput();
        when(systemService.getSystemResult(systemInput)).thenThrow(new Exception(MIN_HIGHER_THAN_MAX));
        ResponseModel<SystemResult> result = systemController.getSystemResult(systemInput);

        assertFalse(result.getStatus());
        assertEquals(0, result.getCount());
        assertNull(result.getData());
        assertEquals(MIN_HIGHER_THAN_MAX, result.getError());
        assertEquals(SYSTEM_SERVICE_NO_RESULT, result.getMessage());
    }
}
