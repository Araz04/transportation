package com.example.transporteationtask.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;

import com.example.transporteationtask.R;
import com.example.transporteationtask.adapters.TransportationsAdapter;
import com.example.transporteationtask.controller.TimerManager;
import com.example.transporteationtask.local.Constants;
import com.example.transporteationtask.models.Transport;

public class MainActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TransportationsAdapter mTransportationsAdapter;
    private MainViewModel mMainViewModel;
    private TimerManager mTransportTimerManager;

    SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeRefreshLayout.setRefreshing(false);
            //TODO refresh transport list
        }
    };

    TransportationsAdapter.OnItemClickListener mOnItemClickListener = new TransportationsAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(int position, View view, Transport transport, RecyclerView.ViewHolder holder) {
            if (transport.getState().equalsIgnoreCase(Constants.STATUS_ACTIVE)){

                updateTransportCreatedTime(transport);

                registerCountdownForTransport(transport, position);

                mTransportationsAdapter.onBuildingStarted(position);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initView();
        initTransportTimer();
    }

    private void initView(){
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        RecyclerView rvTransportations = findViewById(R.id.rv_transportation);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mTransportationsAdapter = new TransportationsAdapter(this);
        rvTransportations.setAdapter(mTransportationsAdapter);
        rvTransportations.setLayoutManager(new LinearLayoutManager(this));
        rvTransportations.hasFixedSize();
        mTransportationsAdapter.setOnClickListener(mOnItemClickListener);

        initData();
    }

    private void initTransportTimer() {
        mTransportTimerManager = new TimerManager();
        mTransportTimerManager.setTimerListener(new TimerManager.TimerListener() {
            @Override
            public void onTick(long milliseconds) {
                mTransportationsAdapter.onTick();
            }

            @Override
            public void onFinish(TimerManager.CountdownObserver observer) {
                mTransportationsAdapter.onBuildingCompleted(observer.getIdentifier());
                Transport transport = mTransportationsAdapter.getItem(observer.getIdentifier());
                transport.setPoints(transport.getPoints() + transport.getBuildPoints());
                updateTransportStateToActive(transport);
            }
        });
    }

    private void initData(){
        mMainViewModel.getAllTransports().observe(this, transports -> {
            mTransportationsAdapter.addAllItems(transports);
            for (int i = 0; i < transports.size(); i++) {
                Transport transport = transports.get(i);
                if (transport.getState().equalsIgnoreCase(Constants.STATUS_BUILDING)) {
                    registerCountdownForTransport(transport, i);
                }
            }
            mMainViewModel.getAllTransports().removeObservers(this);
        });
    }

    public void registerCountdownForTransport(Transport transport, int position) {
        TimerManager.CountdownObserver countdownObserver = new TimerManager.CountdownObserver() {
            private long millisToFinish = (transport.getBuildingTime() -
                    (System.currentTimeMillis() - transport.getCreatedAt()));
            @Override
            public int getIdentifier() {
                return position;
            }

            @Override
            public long getMillisToFinish() {
                return millisToFinish;
            }
        };

        mTransportTimerManager.startNew(countdownObserver);
    }

    public void updateTransportStateToActive(Transport transport) {
        mMainViewModel.updateTransportById(transport.getId(), 0, Constants.STATUS_ACTIVE,
                transport.getPoints() + transport.getBuildPoints());
    }

    public void updateTransportCreatedTime(Transport transport){
        transport.setCreatedAt(System.currentTimeMillis());
        mMainViewModel.updateTransportById(transport.getId(), transport.getCreatedAt(), Constants.STATUS_BUILDING);

    }
}