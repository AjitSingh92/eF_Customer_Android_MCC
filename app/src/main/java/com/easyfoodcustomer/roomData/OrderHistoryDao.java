package com.easyfoodcustomer.roomData;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class OrderHistoryDao {

    @Query("SELECT * FROM OrderHistory WHERE mealID = :id")
    public abstract LiveData<List<OrderSaveModel>> loadAllMessage(int id);

    @Query("SELECT * FROM OrderHistory WHERE mealID = :id AND isFinal=1")
    public abstract List<OrderSaveModel> loadAllOrderForID(String id);

    @Query("SELECT * FROM OrderHistory")
    public abstract LiveData<List<OrderSaveModel>> loadAllHistory();

  @Query("SELECT * FROM OrderHistory")
    public abstract List<OrderSaveModel> loadAllHistoryOfOrder();

   /* @Query("SELECT * FROM OrderHistory WHERE sender_id = :id AND channelId = :channel_id AND msg_status IN ('0' ,'1') ")
    public abstract List<OrderSaveModel> loadAllMessageByFrom(String id, String channel_id);*/

    @Insert
    @Transaction
    public abstract void insertMessaage(OrderSaveModel chatMessage);

    @Update
    public abstract void updateOrder(OrderSaveModel chatMessage);

    @Delete
    public abstract void delete(OrderSaveModel chatMessage);

    @Query("DELETE FROM OrderHistory")
    public abstract void deleteAll();

    @Query("DELETE FROM OrderHistory WHERE mealID = :mealID")
    public abstract void deleteAllMessage(String mealID);

    @Query("SELECT * FROM OrderHistory WHERE mealID = :id")
    public abstract OrderSaveModel loadMessageByChenalID(int id);


    /*@Query("SELECT * FROM OrderHistory WHERE channelId = :id AND msg_status != :status ORDER BY date DESC , msg_id DESC LIMIT 1")
    public abstract OrderSaveModel getLastMessageByID(int id, String status);*/

   /* @Query("SELECT * FROM OrderHistory ORDER BY channelId = :id ASC LIMIT 1")
    public abstract OrderSaveModel getLastMessage(int id);*/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract Long insert(OrderSaveModel chatMessage);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract List<Long> insert(List<OrderSaveModel> chatMessageList);

    @Update
    public abstract void update(OrderSaveModel chatMessage);

    @Update
    public abstract void update(List<OrderSaveModel> personList);

    @Transaction
    public void insertOrUpdate(OrderSaveModel chatMessage) {
        Long id = insert(chatMessage);
        if (id == -1) {
            update(chatMessage);
        }
    }

    @Transaction
    public void insertOrUpdate(List<OrderSaveModel> chatMessageList) {
        List<Long> insertResult = insert(chatMessageList);
        List<OrderSaveModel> updateList = new ArrayList<>();

        for (int i = 0; i < insertResult.size(); i++) {
            if (insertResult.get(i) == -1) {
                updateList.add(chatMessageList.get(i));
            }
        }
        if (!updateList.isEmpty()) update(updateList);
    }
}
