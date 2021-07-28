package com.easyfoodcustomer.search_post_code.model.search_response;

public class GuestTokenBean {

    /**
     * success : true
     * key : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvYWRtaW4uZWFzeWZvb2QuY28udWsiLCJhdWQiOiJodHRwczpcL1wvYWRtaW4uZWFzeWZvb2QuY28udWsifQ.qNsj6z_18pLPr_EMClYrcUMGU_AM6Mz3SdbNvmh-53A
     */

    private boolean success;
    private String key;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
