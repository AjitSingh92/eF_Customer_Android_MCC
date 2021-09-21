package com.easyfoodcustomer.adapters.previous_order;

public class DeleteResponse {

    /**
     * success : true
     * message : Order deleted successfully
     * error : {}
     */

    private boolean success;
    private String message;
    private ErrorBean error;

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

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
    }
}
