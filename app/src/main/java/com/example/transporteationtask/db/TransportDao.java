package com.example.transporteationtask.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.transporteationtask.models.Transport;

import java.util.List;

@Dao
public interface TransportDao {
    @Query("SELECT * FROM transport")
    LiveData<List<Transport>> getAllTransports();

    @Query("SELECT * FROM transport WHERE id =:id")
    LiveData<Transport> getLiveTransportById(int id);

    @Insert()
    void insertAll(List<Transport> transport);

    @Query("UPDATE transport SET created_at = :createdAt, state = :state,  points= :points WHERE id =:id")
    void updateTransportById(int id, long createdAt, String state, int points);

    @Query("UPDATE transport SET created_at = :createdAt, state = :state WHERE id =:id")
    void updateTransportById(int id, long createdAt, String state);


    @Query("DELETE FROM transport")
    void deleteAll();

    @Insert
    void insertTransport(Transport transport);

    @Update()
    void updateTransport(Transport transport);

    @Delete
    void deleteTransport(Transport transport);
}
