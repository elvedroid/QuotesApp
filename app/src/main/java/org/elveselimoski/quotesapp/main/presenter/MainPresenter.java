package org.elveselimoski.quotesapp.main.presenter;

import org.elveselimoski.quotesapp.base.BasePresenter;
import org.elveselimoski.quotesapp.main.view.MainView;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
public interface MainPresenter extends BasePresenter<MainView> {
    void getAllQuotes();
}
