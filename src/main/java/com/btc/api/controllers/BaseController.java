package com.btc.api.controllers;

import com.btc.api.model.ResponseModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.btc.api.messages.Responses.SOMETHING_WENT_WRONG;

public class BaseController {

    protected static @NotNull <T> ResponseModel<List<T>> getListResponseModel(List<T> list) {
        ResponseModel<List<T>> response = new ResponseModel<>();
        response.setData(list);
        response.setStatus(list != null && !list.isEmpty());
        response.setCount(list != null ? list.size() : 0);
        response.setMessage(list == null || list.isEmpty() ? "No results" : "");
        return response;
    }

    protected static @NotNull <T> ResponseModel<T> getResponseModel(T entity) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setData(entity);
        response.setStatus(entity != null);
        response.setCount(entity != null ? 1 : 0);
        response.setMessage(entity == null ? "No result found" : "");
        return response;
    }


    protected static @NotNull <T> ResponseModel<T> getResponseModel(String s) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setStatus(s != null);
        response.setCount(s != null ? 1 : 0);
        response.setMessage(s == null ? SOMETHING_WENT_WRONG : s);
        return response;
    }

    protected static @NotNull <T> ResponseModel<T> getResponseModelError(String errorMessage) {
        ResponseModel<T> response = new ResponseModel<>();
        response.setStatus(false);
        response.setCount(0);
        response.setData(null);
        response.setError(errorMessage);
        return response;
    }
}
