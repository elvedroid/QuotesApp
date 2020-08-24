package org.elveselimoski.quotesapp.main.view_model;

import android.os.AsyncTask;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import org.elveselimoski.quotesapp.db.QuoteDao;
import org.elveselimoski.quotesapp.model.Quote;
import org.elveselimoski.quotesapp.network.QuotesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Elvedin Selimoski on 8/24/2020.
 */
public class MainViewModel extends ViewModel {
  private QuoteDao quoteDao;
  private QuotesApi quotesApi;

  public MutableLiveData<List<Quote>> quotesLiveData = new MutableLiveData<>();

  public void setQuoteDao(QuoteDao quoteDao) {
    this.quoteDao = quoteDao;
  }

  public void setQuotesApi(QuotesApi quotesApi) {
    this.quotesApi = quotesApi;
  }

  public void getAllQuotes() {
    Call<List<Quote>> quotesCall = quotesApi.getQuotes();
    quotesCall.enqueue(new Callback<List<Quote>>() {
      @Override
      public void onResponse(Call<List<Quote>> call, Response<List<Quote>> response) {
        if (response.isSuccessful()) {
          new Thread(() -> quoteDao.insertQuotes(response.body())).start();

          quotesLiveData.setValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<List<Quote>> call, Throwable t) {
        DatabaseQuotesAsyncTask asyncTask = new DatabaseQuotesAsyncTask(quoteDao,
            quotes -> quotesLiveData.setValue(quotes));
        asyncTask.execute();
      }
    });
  }

  static class DatabaseQuotesAsyncTask extends AsyncTask<Void, Void, List<Quote>> {

    private QuoteDao quoteDao;
    private final GetQuotes getQuotes;

    public DatabaseQuotesAsyncTask(QuoteDao quoteDao, GetQuotes getQuotes) {
      this.quoteDao = quoteDao;
      this.getQuotes = getQuotes;
    }

    @Override
    protected List<Quote> doInBackground(Void... voids) {
      return quoteDao.getAllQuotes();
    }

    @Override
    protected void onPostExecute(List<Quote> quotes) {
      getQuotes.onGetQuotes(quotes);
    }
  }

  interface GetQuotes {
    void onGetQuotes(List<Quote> quotes);
  }
}
