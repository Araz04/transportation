package com.example.transporteationtask.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.transporteationtask.db.AppDatabase;
import com.example.transporteationtask.db.TransportDao;
import com.example.transporteationtask.models.Transport;

import java.util.List;

public class TransportRepository {
    private final TransportDao mTransportDao;
    private final LiveData<List<Transport>> mTransports;

    public TransportRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mTransportDao = db.transportDao();
        mTransports = mTransportDao.getAllTransports();
    }

    public LiveData<List<Transport>> getAllTransports() {
        return mTransports;
    }

    public void insert(Transport transport) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransportDao.insertTransport(transport);
        });
    }

    public void insertAll(List<Transport> transports){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransportDao.insertAll(transports);
        });
    }

    public void deleteTransport(Transport transport) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransportDao.deleteTransport(transport);
        });
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(mTransportDao::deleteAll);
    }

    public void update(Transport transport){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransportDao.updateTransport(transport);
        });
    }

    public void updateTransportById(int id, long createdAt, String status, int points){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransportDao.updateTransportById(id, createdAt, status, points);
        });
    }

    public void updateTransportById(int id, long createdAt, String status){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTransportDao.updateTransportById(id, createdAt, status);
        });
    }
}
