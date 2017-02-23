package com.nerdery.umbrella.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nerdery.umbrella.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Alexander Melton on 2/18/2017.
 */

public class BaseActivity extends AppCompatActivity {

    // This field is never used, Also, if you were using this, you should make it private, and only
    // accessible via getter and/or setter methods
    protected CompositeSubscription compositeSubscription;

    @Nullable @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

    }

    // Annotate this with an @LayoutRes annotation. It will throw a warning when you return a non layout resource
    protected int getLayoutResource(){
        return R.layout.activity_base;
    }

    protected void attachFragment(int containerViewId, Fragment fragment){
        attachFragment(containerViewId, fragment, false);
    }

    // I personally don't like doing this in the base activity anymore, but that's 'cause
    // Fragments are shitty.
    protected void attachFragment(int containerViewId, Fragment fragment, boolean addToBackstack){
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, fragment.getClass().getSimpleName());

        if(addToBackstack){
            fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }

        fragmentTransaction.commit();
    }
}
