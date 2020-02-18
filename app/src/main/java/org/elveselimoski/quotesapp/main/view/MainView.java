package org.elveselimoski.quotesapp.main.view;

import org.elveselimoski.quotesapp.base.BaseView;
import org.elveselimoski.quotesapp.main.presenter.MainPresenter;
import org.elveselimoski.quotesapp.model.Quote;

import java.util.List;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
public interface MainView extends BaseView<MainPresenter> {
    void displayQuotes(List<Quote> quotes);
}
