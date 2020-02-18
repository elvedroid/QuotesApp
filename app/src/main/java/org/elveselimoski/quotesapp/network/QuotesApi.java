package org.elveselimoski.quotesapp.network;

import org.elveselimoski.quotesapp.model.Quote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
public interface QuotesApi {
    @GET("quotes")
    Call<List<Quote>> getQuotes();
}
