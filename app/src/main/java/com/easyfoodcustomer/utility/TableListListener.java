package com.easyfoodcustomer.utility;

public interface TableListListener {
    void onTopItemClick(int position);

    void onBottomItemClick(int parentPos
            , int position);
}
