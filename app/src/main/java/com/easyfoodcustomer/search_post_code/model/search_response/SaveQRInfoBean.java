package com.easyfoodcustomer.search_post_code.model.search_response;

public class SaveQRInfoBean {

    /**
     * success : true
     * message : Data saved successfully
     * data : {}
     * errors : {}
     */

    private boolean success;
    private String message;
    private DataBean data;
    private ErrorsBean errors;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ErrorsBean getErrors() {
        return errors;
    }

    public void setErrors(ErrorsBean errors) {
        this.errors = errors;
    }

    public static class DataBean {
    }

    public static class ErrorsBean {
    }
}
