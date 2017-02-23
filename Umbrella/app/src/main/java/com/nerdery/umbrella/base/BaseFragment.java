package com.nerdery.umbrella.base;

import android.support.v4.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by Alexander Melton on 2/13/2017.
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder = Unbinder.EMPTY;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // Y tho (also unused) you can butterknife.bind in onCreateView with an abstract getView method
    protected void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    // unused, but keep it
    protected BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity();
    }
}
