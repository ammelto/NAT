package com.nerdery.umbrella.base.mvp;

/**
 * Created by Alexander Melton on 2/12/2017.
 *
 * This is just to implement in BasePresenter
 * It's a little overkill for a small project like this
 *
 */


public interface Presenter<V extends BaseView> {

    void attachView(V view);

    void detachView();
}
