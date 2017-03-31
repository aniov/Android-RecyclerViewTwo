package com.aniov.android.myapplication.listener;

import com.aniov.android.myapplication.model.RecyclerViewItem;

/**
 * Created by Marius on 3/31/2017.
 */

public interface OnRecyclerViewItemClickListener  {

    void onRightItemClickListener(int position);

    void onLeftItemClickListener(RecyclerViewItem item, float x, float y);
}
