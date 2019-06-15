
package com.lexxdigital.easyfooduserapps.model.my_account_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

}
