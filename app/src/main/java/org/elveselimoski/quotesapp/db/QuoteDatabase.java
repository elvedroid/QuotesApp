package org.elveselimoski.quotesapp.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.elveselimoski.quotesapp.model.Quote;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
@Database(entities = {Quote.class}, version = 1)
public abstract class QuoteDatabase extends RoomDatabase {

    public abstract QuoteDao quoteDao();

    private static QuoteDatabase INSTANCE;

    public static QuoteDatabase getQuoteDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (QuoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            QuoteDatabase.class,
                            "quotes_db")
                            .addCallback(new Callback() {
                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
