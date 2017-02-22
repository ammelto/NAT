package com.nerdery.umbrella.views.home;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Alexander Melton on 2/21/2017.
 */

public class DecorationDailyForecast extends RecyclerView.ItemDecoration {
    private final int verticalSpaceHeight;

    DecorationDailyForecast(int verticalSpaceHeight){
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = verticalSpaceHeight;
    }
}
