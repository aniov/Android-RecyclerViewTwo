package com.aniov.android.myapplication.model;

/**
 * Created by Marius on 3/31/2017.
 */

public class RecyclerViewItem {

    private String title;
    private String Subtitle;

    public RecyclerViewItem(String title, String subtitle) {
        this.title = title;
        Subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return Subtitle;
    }
}
