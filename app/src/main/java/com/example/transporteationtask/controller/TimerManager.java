package com.example.transporteationtask.controller;

import android.os.Handler;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TimerManager {
    private static final String LOG_TAG = TimerManager.class.getSimpleName();

    private final static int DEFAULT_INTERVAL = 1000;

    private TimerListener mTimerListener;
    private boolean isRunning = false;
    private final Handler mHandler;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                onTick();
                mHandler.postDelayed(this, DEFAULT_INTERVAL);
            }
        }
    };

    private final Map<CountdownObserver, Long> mObservers = new HashMap<>();

    public TimerManager() {
        mHandler = new Handler();
    }

    public void startNew(CountdownObserver countdownObserver) {
        Log.d(LOG_TAG, "new observer added: " + countdownObserver.getIdentifier());
        mObservers.put(countdownObserver, System.currentTimeMillis());
        maybeStartCountDown();
    }

    public void stop(CountdownObserver countdownObserver) {
        mObservers.remove(countdownObserver);
        maybeStopCountDown();
    }

    private void maybeStartCountDown() {
        if (!isRunning) {
            Log.d(LOG_TAG, "Timer started");
            isRunning = true;
            mHandler.postDelayed(mRunnable, DEFAULT_INTERVAL);
        }
    }

    private void maybeStopCountDown() {
        if (mObservers.isEmpty()) {
            Log.d(LOG_TAG, "Timer stopped");
            mHandler.removeCallbacks(mRunnable);
            isRunning = false;
        }
    }

    private void onTick() {
        mTimerListener.onTick(System.currentTimeMillis());
        notifyCompleted(System.currentTimeMillis());
    }

    public void notifyCompleted(long milliseconds) {
        Set<CountdownObserver> keys = new HashSet<>(mObservers.keySet());
        for (CountdownObserver observer : keys) {
            if (milliseconds - mObservers.get(observer) > observer.getMillisToFinish()) {
                Log.d(LOG_TAG, observer.getIdentifier() + " is completed");
                mTimerListener.onFinish(observer);
                stop(observer);
            }
        }
    }

    public void setTimerListener(TimerListener timerListener) {
        mTimerListener = timerListener;
    }

    public interface TimerListener {
        void onTick(long milliseconds);
        void onFinish(CountdownObserver observer);
    }

    public interface CountdownObserver {
        int getIdentifier();
        long getMillisToFinish();
    }
}
