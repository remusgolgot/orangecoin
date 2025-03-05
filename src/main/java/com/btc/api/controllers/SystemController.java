package com.btc.api.controllers;

import com.btc.api.model.ResponseModel;
import com.btc.api.services.SystemService;
import com.btc.model.SystemInput;
import com.btc.model.SystemResult;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.btc.api.messages.Responses.SYSTEM_SERVICE_NO_RESULT;

@RestController
@RequestMapping("/api/systems")
public class SystemController {

    @Autowired
    SystemService systemService;

    /**
     * Calculates the results of the given system (as systemInput)
     * If no input is given, it returns the result of what happened after an up day
     *
     * target is optional (default is 1 day after)
     * timespan is optional (default is 1 day timespan)
     * streak is optional (default is no streak (0 acts as no streak))
     * min and max (at least one of them must be provided)
     * from is optional (default is from the beginning of the data set)
     * to is optional (default is up until the end of the data set)
     *
     * @return system results
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseModel<SystemResult> getSystemResult(@Valid @RequestBody SystemInput systemInput) {

        try {
            SystemResult systemResult = systemService.getSystemResult(systemInput);
            return getResponseModel(systemResult);
        } catch (Exception e) {
            ResponseModel<SystemResult> responseModel = getResponseModel(null);
            responseModel.setError(e.getMessage());
            return responseModel;
        }
    }

    private static @NotNull <T> ResponseModel<T> getResponseModel(T entity) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setData(entity);
        response.setStatus(entity != null);
        response.setCount(entity != null ? 1 : 0);
        response.setMessage(entity == null ? SYSTEM_SERVICE_NO_RESULT : "");
        return response;
    }
}
