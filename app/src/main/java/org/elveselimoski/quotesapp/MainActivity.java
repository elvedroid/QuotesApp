package org.elveselimoski.quotesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import org.elveselimoski.quotesapp.db.QuoteDao;
import org.elveselimoski.quotesapp.db.QuoteDatabase;
import org.elveselimoski.quotesapp.model.Quote;
import org.elveselimoski.quotesapp.network.QuotesApi;
import org.elveselimoski.quotesapp.network.RetrofitClient;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private QuoteDao quoteDao;
    private RecyclerView rvQuotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quoteDao = QuoteDatabase.getQuoteDatabase(this).quoteDao();

        rvQuotes = findViewById(R.id.rvQuotes);
        rvQuotes.setLayoutManager(new LinearLayoutManager(this));

        QuotesApi quotesApi = RetrofitClient.getRetrofit().create(QuotesApi.class);
        Call<List<Quote>> quotesCall = quotesApi.getQuotes();
        quotesCall.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful()) {

                    new Thread(() -> quoteDao.insertQuotes(response.body())).start();

                    QuotesAdapter adapter = new QuotesAdapter(response.body());
                    rvQuotes.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                DatabaseQuotesAsyncTask asyncTask = new DatabaseQuotesAsyncTask(quoteDao, rvQuotes);
                asyncTask.execute();
            }
        });
    }

    static class DatabaseQuotesAsyncTask extends AsyncTask<Void, Void, List<Quote>> {

        private QuoteDao quoteDao;
        private WeakReference<RecyclerView> rvQuotes;

        public DatabaseQuotesAsyncTask(QuoteDao quoteDao, RecyclerView rvQuotes) {
            this.quoteDao = quoteDao;
            this.rvQuotes = new WeakReference<>(rvQuotes);
        }

        @Override
        protected List<Quote> doInBackground(Void... voids) {
            return quoteDao.getAllQuotes();
        }

        @Override
        protected void onPostExecute(List<Quote> quotes) {
            QuotesAdapter adapter = new QuotesAdapter(quotes);
            rvQuotes.get().setAdapter(adapter);
        }
    }
}
