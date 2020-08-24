package org.elveselimoski.quotesapp.main.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.elveselimoski.quotesapp.R;
import org.elveselimoski.quotesapp.db.QuoteDao;
import org.elveselimoski.quotesapp.db.QuoteDatabase;
import org.elveselimoski.quotesapp.main.adapter.QuotesAdapter;
import org.elveselimoski.quotesapp.main.view_model.MainViewModel;
import org.elveselimoski.quotesapp.network.QuotesApi;
import org.elveselimoski.quotesapp.network.RetrofitClient;

public class MainActivity extends AppCompatActivity {

  private RecyclerView rvQuotes;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    QuoteDao quoteDao = QuoteDatabase.getQuoteDatabase(this).quoteDao();
    QuotesApi quotesApi = RetrofitClient.getRetrofit().create(QuotesApi.class);

    rvQuotes = findViewById(R.id.rvQuotes);
    rvQuotes.setLayoutManager(new LinearLayoutManager(this));

    MainViewModel viewModel =
        new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel.class);
    viewModel.setQuoteDao(quoteDao);
    viewModel.setQuotesApi(quotesApi);
    viewModel.quotesLiveData.observe(this, quotes -> {
      QuotesAdapter adapter = new QuotesAdapter(quotes);
      rvQuotes.setAdapter(adapter);
    });

    viewModel.getAllQuotes();
  }
}
