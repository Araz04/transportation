package com.example.transporteationtask.activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.transporteationtask.models.Transport;
import com.example.transporteationtask.repository.TransportRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private TransportRepository mRepository;

    private final LiveData<List<Transport>> mAllTransports;
    public MainViewModel(Application application){
        super(application);
        mRepository = new TransportRepository(application);
        mAllTransports = mRepository.getAllTransports();
    }

    LiveData<List<Transport>> getAllTransports() { return mAllTransports; }

    public void insert(Transport transport) { mRepository.insert(transport); }

    public void delete(Transport transport) { mRepository.deleteTransport(transport); }

    public void insertAll(List<Transport> transports) { mRepository.insertAll(transports); }

    public void deleteAll() { mRepository.deleteAll(); }

    public void update(Transport transport) { mRepository.update(transport); }

    public void updateTransportById(int id, long createdAt, String state, int points) { mRepository.updateTransportById(id, createdAt, state, points); }

    public void updateTransportById(int id, long createdAt, String state) { mRepository.updateTransportById(id, createdAt, state); }
}
