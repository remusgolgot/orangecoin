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

@RestController
@RequestMapping("/api/systems")
public class SystemController {

    @Autowired
    SystemService systemService;

    /**
     * Calculates the results of the given system (as systemInput)
     * If no input is given, it returns the result of what happened after an up day
     *
     * time span is optional (default is 1 day after, can be a value between 1 and 365)
     * upOrDown is optional (default is up (1), can also be down (-1))
     * streak is optional (default is no streak (0 acts as no streak), can be a value between 0 and 7)
     * amount is optional (default is 0, is a double value acting as a percentage)
     * from is optional (default is from the beginning of the data set)
     * to is optional (default is up until the end of the data set)
     *
     * @return system results
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseModel<SystemResult> getSystemResult(@Valid @RequestBody SystemInput systemInput) {
        SystemResult systemResult = systemService.getSystemResult(systemInput);
        return getResponseModel(systemResult);
    }

    private static @NotNull <T> ResponseModel<T> getResponseModel(T entity) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setData(entity);
        response.setStatus(entity != null);
        response.setCount(entity != null ? 1 : 0);
        response.setMessage(entity == null ? "No result found" : "");
        return response;
    }
}
