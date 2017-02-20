package com.nerdery.umbrella.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    protected int getLayoutResource(){
        return R.layout.activity_base;
    }
}
