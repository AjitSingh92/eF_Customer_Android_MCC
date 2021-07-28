package com.easyfoodcustomer.login.model.response;

public class CheckAccountBean {

    /**
     * success : true
     * message : Found.
     * data : {"isRegisterd":true,"phone":null}
     * error : {}
     */

    private boolean success;
    private String message;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * isRegisterd : true
         * phone : null
         */

        private boolean isRegisterd;
        private String phone;

        public boolean isIsRegisterd() {
            return isRegisterd;
        }

        public void setIsRegisterd(boolean isRegisterd) {
            this.isRegisterd = isRegisterd;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public static class ErrorBean {
    }
}
