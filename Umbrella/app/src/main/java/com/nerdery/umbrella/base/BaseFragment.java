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

    protected BaseActivity getBaseActivity(){
        return (BaseActivity) getActivity();
    }
}
