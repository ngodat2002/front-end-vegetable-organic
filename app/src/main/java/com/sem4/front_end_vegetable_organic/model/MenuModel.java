package com.sem4.front_end_vegetable_organic.model;

import java.util.List;

public class MenuModel {
    boolean success;
    String message;
    List<Menu> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Menu> getResult() {
        return result;
    }

    public void setResult(List<Menu> result) {
        this.result = result;
    }
}
