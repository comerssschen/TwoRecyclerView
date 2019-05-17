package com.weipan.kotilin.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author comersss
 * @date 2018/10/24
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.left = mSpace;
//        outRect.right = mSpace;
        outRect.bottom = mSpace;
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.top = mSpace;
//        }

    }

    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }
}
