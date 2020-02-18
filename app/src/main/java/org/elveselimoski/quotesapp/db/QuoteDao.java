package org.elveselimoski.quotesapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.elveselimoski.quotesapp.model.Quote;

import java.util.List;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
@Dao
public interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuotes(List<Quote> quotes);

    @Query("SELECT * FROM Quote")
    List<Quote> getAllQuotes();

    @Query("DELETE FROM Quote")
    void clearQuotes();

}