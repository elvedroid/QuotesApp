package org.elveselimoski.quotesapp.base;

/**
 * Created by Elvedin Selimoski on 2/18/2020.
 */
public interface BasePresenter<V> {
    void bindView(V view);
    void dropView();
}
