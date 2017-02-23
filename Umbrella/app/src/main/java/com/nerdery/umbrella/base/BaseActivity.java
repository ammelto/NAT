package com.nerdery.umbrella.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nerdery.umbrella.R;
import com.nerdery.umbrella.Umbrella;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class BaseActivity extends AppCompatActivity {

    @Nullable @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }

    }

    @LayoutRes
    protected int getLayoutResource(){
        return R.layout.activity_base;
    }

    protected void attachFragment(int containerViewId, Fragment fragment){
        attachFragment(containerViewId, fragment, false);
    }

    protected void attachFragment(int containerViewId, Fragment fragment, boolean addToBackstack){
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());

        if(addToBackstack){
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        fragmentTransaction.commit();
    }
}
