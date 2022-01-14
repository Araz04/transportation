package com.example.transporteationtask.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.transporteationtask.models.Transport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transport.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DB_NAME = "transport_db";
    private static AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public abstract TransportDao transportDao();

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            prepopulateDb(getInstance(context));

                        }

                        public void onOpen(SupportSQLiteDatabase db) {
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .addCallback(rdc)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static void prepopulateDb(AppDatabase db) {
        List<Transport> transportsList = new ArrayList<>();
        transportsList.add(new Transport(1, "Taxi", "5", "Ground Lines", "", 0, "Active", 120000L, 100));
        transportsList.add(new Transport(2, "Bus", "10", "Ground Lines", "", 0, "Active", 120000L, 100));
        transportsList.add(new Transport(3, "Train", "20", "Ground Lines", "", 0, "Active", 120000L, 100));
        transportsList.add(new Transport(4, "Limousine", "40", "Ground Lines", "", 0, "Active", 120000L, 100));
        transportsList.add(new Transport(5, "Passengers plane", "50", "Air Lines", "", 0, "Active", 120000L, 100));
        transportsList.add(new Transport(6, "Cargo plane", "70", "Air Lines", "", 0, "Active", 120000L, 100));
        transportsList.add(new Transport(7, "Passengers ship", "100", "Ship Lines", "", 0, "Active", 120000L, 100));
        transportsList.add(new Transport(8, "Cargo ship", "120", "Ship Lines", "", 0, "Active", 120000L, 100));

        AppDatabase.databaseWriteExecutor.execute(() -> {
            db.transportDao().insertAll(transportsList);
        });
    }
}
