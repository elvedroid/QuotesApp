package org.elveselimoski.quotesapp.main.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.elveselimoski.quotesapp.R;
import org.elveselimoski.quotesapp.db.QuoteDao;
import org.elveselimoski.quotesapp.db.QuoteDatabase;
import org.elveselimoski.quotesapp.main.adapter.QuotesAdapter;
import org.elveselimoski.quotesapp.main.presenter.MainPresenter;
import org.elveselimoski.quotesapp.main.presenter.MainPresenterImpl;
import org.elveselimoski.quotesapp.model.Quote;
import org.elveselimoski.quotesapp.network.QuotesApi;
import org.elveselimoski.quotesapp.network.RetrofitClient;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private RecyclerView rvQuotes;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuoteDao quoteDao = QuoteDatabase.getQuoteDatabase(this).quoteDao();
        QuotesApi quotesApi = RetrofitClient.getRetrofit().create(QuotesApi.class);
        presenter = new MainPresenterImpl(quoteDao, quotesApi);
        presenter.bindView(this);

        rvQuotes = findViewById(R.id.rvQuotes);
        rvQuotes.setLayoutManager(new LinearLayoutManager(this));

        presenter.getAllQuotes();
    }

    @Override
    protected void onDestroy() {
        presenter.dropView();
        super.onDestroy();
    }

    @Override
    public void displayQuotes(List<Quote> quotes) {
        QuotesAdapter adapter = new QuotesAdapter(quotes);
        rvQuotes.setAdapter(adapter);
    }
}
