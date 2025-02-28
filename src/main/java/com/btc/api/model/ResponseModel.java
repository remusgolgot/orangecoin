package com.btc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseModel<T> {

    private Boolean status;
    private String message;
    private String error;
    private int count;
    private T data;

    public ResponseModel() {
        this.status = true;
        this.message = "";
        this.error = "";
        this.data = null;
        this.count = 0;
    }

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}