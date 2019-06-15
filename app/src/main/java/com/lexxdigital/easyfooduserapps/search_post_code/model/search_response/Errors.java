
package com.lexxdigital.easyfooduserapps.search_post_code.model.search_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Errors {

    @SerializedName("post_code")
    @Expose
    private List<String> postCode = null;

    public List<String> getPostCode() {
        return postCode;
    }

    public void setPostCode(List<String> postCode) {
        this.postCode = postCode;
    }

}
