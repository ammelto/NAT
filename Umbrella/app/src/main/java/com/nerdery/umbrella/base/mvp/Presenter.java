package com.nerdery.umbrella.base.mvp;

/**
 * Created by Alexander Melton on 2/12/2017.
 */

// As it is, I think you are only using this presenter to implement in BasePresenter. This
// is fine, but if you aren't using this as a form of casting, then I would say stick with
// just using BasePresenter.
public interface Presenter<V extends BaseView> {

    void attachView(V view);

    void detachView();
}
