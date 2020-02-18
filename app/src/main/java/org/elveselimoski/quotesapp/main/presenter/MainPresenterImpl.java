package org.elveselimoski.quotesapp.main.presenter;

import android.os.AsyncTask;

import org.elveselimoski.quotesapp.db.QuoteDao;
import org.elveselimoski.quotesapp.main.view.MainView;
import org.elveselimoski.quotesapp.model.Quote;
import org.elveselimoski.quotesapp.network.QuotesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
public class MainPresenterImpl implements MainPresenter {

    private final QuoteDao quoteDao;
    private final QuotesApi quotesApi;

    private MainView view;

    public MainPresenterImpl(QuoteDao quoteDao, QuotesApi quotesApi) {
        this.quoteDao = quoteDao;
        this.quotesApi = quotesApi;
    }

    @Override
    public void bindView(MainView view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void getAllQuotes() {
        Call<List<Quote>> quotesCall = quotesApi.getQuotes();
        quotesCall.enqueue(new Callback<List<Quote>>() {
            @Override
            public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
                if (response.isSuccessful()) {
                    new Thread(() -> quoteDao.insertQuotes(response.body())).start();

                    if (view != null) {
                        view.displayQuotes(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Quote>> call, Throwable t) {
                DatabaseQuotesAsyncTask asyncTask = new DatabaseQuotesAsyncTask(quoteDao, view);
                asyncTask.execute();
            }
        });

    }

    static class DatabaseQuotesAsyncTask extends AsyncTask<Void, Void, List<Quote>> {

        private QuoteDao quoteDao;
        private MainView mainView;

        public DatabaseQuotesAsyncTask(QuoteDao quoteDao, MainView mainView) {
            this.quoteDao = quoteDao;
            this.mainView = mainView;
        }

        @Override
        protected List<Quote> doInBackground(Void... voids) {
            return quoteDao.getAllQuotes();
        }

        @Override
        protected void onPostExecute(List<Quote> quotes) {
            if (mainView != null) {
                mainView.displayQuotes(quotes);
            }
        }
    }
}
