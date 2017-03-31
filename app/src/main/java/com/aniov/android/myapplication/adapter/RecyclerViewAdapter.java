package com.aniov.android.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aniov.android.myapplication.R;
import com.aniov.android.myapplication.listener.OnRecyclerViewItemClickListener;
import com.aniov.android.myapplication.model.RecyclerViewItem;

import java.util.List;

/**
 * Created by Marius on 3/31/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ItemViewHolder>{

    private List<RecyclerViewItem> items;

    private OnRecyclerViewItemClickListener clickListener;

    public RecyclerViewAdapter(List<RecyclerViewItem> items, OnRecyclerViewItemClickListener clickListener) {
        this.items = items;
        this.clickListener = clickListener;
    }

    public void addItemToList(RecyclerViewItem item, int position) {
        items.add(position, item);

    }

    public void removeItemFromList(int position) {
        items.remove(position);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.titleTextView.setText(items.get(holder.getAdapterPosition()).getTitle());
        holder.subtitleTextView.setText(items.get(holder.getAdapterPosition()).getSubtitle());

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onRightItemClickListener(holder.getAdapterPosition());
                }
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            float actionDownT = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    actionDownT = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP && clickListener != null && actionDownT < 100) {
                    clickListener.onLeftItemClickListener(items.get(holder.getAdapterPosition()), event.getRawX(), event.getRawY());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTextView;
        private TextView subtitleTextView;
        private ImageView deleteImage;

        public ItemViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.text_title);
            subtitleTextView = (TextView) itemView.findViewById(R.id.text_subtitle);
            deleteImage = (ImageView) itemView.findViewById(R.id.image_delete);
        }
    }


}
