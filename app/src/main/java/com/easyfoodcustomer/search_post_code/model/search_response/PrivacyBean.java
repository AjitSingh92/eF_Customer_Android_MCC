package com.easyfoodcustomer.search_post_code.model.search_response;

public class PrivacyBean {




    private boolean success;
    private DataBean data;
    private ErrorsBean errors;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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


        private String restaurant_opportunity;
        private String allergy_dietry_needs;
        private String cookies;
        private String term_and_conditions;
        private String about_us;
        private String privacy_policy;

        public String getRestaurant_opportunity() {
            return restaurant_opportunity;
        }

        public void setRestaurant_opportunity(String restaurant_opportunity) {
            this.restaurant_opportunity = restaurant_opportunity;
        }

        public String getAllergy_dietry_needs() {
            return allergy_dietry_needs;
        }

        public void setAllergy_dietry_needs(String allergy_dietry_needs) {
            this.allergy_dietry_needs = allergy_dietry_needs;
        }

        public String getCookies() {
            return cookies;
        }

        public void setCookies(String cookies) {
            this.cookies = cookies;
        }

        public String getTerm_and_conditions() {
            return term_and_conditions;
        }

        public void setTerm_and_conditions(String term_and_conditions) {
            this.term_and_conditions = term_and_conditions;
        }

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }

        public String getPrivacy_policy() {
            return privacy_policy;
        }

        public void setPrivacy_policy(String privacy_policy) {
            this.privacy_policy = privacy_policy;
        }
    }

    public static class ErrorsBean {
    }
}
