package com.aniov.android.myapplication.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.aniov.android.myapplication.R;
import com.aniov.android.myapplication.adapter.RecyclerViewAdapter;
import com.aniov.android.myapplication.listener.OnRecyclerViewItemClickListener;
import com.aniov.android.myapplication.model.RecyclerViewItem;
import com.aniov.android.myapplication.utils.AnimationUtils;
import com.aniov.android.myapplication.utils.JSONParser;
import com.aniov.android.myapplication.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener {


    private List<RecyclerViewItem> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView quoteTextView;
    private TextView authorNameTextView;
    private boolean detailsHidden = true;
    private AnimatorSet animatorSet;
    private FloatingActionButton floatingActionButton;
    private View circleView;
    private View quotesDetails;
    private float lastPositionX;
    private float lastPositionY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*JSONParser jsonParser = new JSONParser(this);

        final List<RecyclerViewItem> tempList = jsonParser.getJsonObjects();
        for (int i = 0; i < 20; i++) {
            items.add(tempList.get(i));
        }*/

       /* recyclerViewAdapter = new RecyclerViewAdapter(items, this);

        // initialising the recyclerView with its adapter and layoutManager
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // floating button that adds random items from the JSON file
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                int number = rand.nextInt(50) + 1;
                int position = recyclerViewAdapter.getItemCount() == 0 ? 0 : 1;
                recyclerViewAdapter.addItemToList(new RecyclerViewItem(tempList.get(number).getTitle(), tempList.get(number).getSubtitle()), position);
                recyclerView.smoothScrollToPosition(0);
            }
        });

        quoteTextView = (TextView) findViewById(R.id.text_quote);
        authorNameTextView = (TextView) findViewById(R.id.text_quote_author);
        circleView = findViewById(R.id.view_circle);
        quotesDetails = findViewById(R.id.layout_quotes_details);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Drawable drawable = menu.getItem(0).getIcon();
        drawable.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_IN));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.refresh_icon) {
            recyclerViewAdapter.notifyItemRangeChanged(0, items.size());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRightItemClickListener(int position) {
        recyclerViewAdapter.removeItemFromList(position);

    }

    @Override
    public void onLeftItemClickListener(RecyclerViewItem item, float x, float y) {
        if (detailsHidden) {
            detailsHidden = false;
            translateDetails(x, y);
            showDetails(item);
        } else {
            detailsHidden = true;
            hideDetails();
        }
    }

    private void hideDetails() {
        float toValue = 0f;

        Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //no implementation needed
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                circleView.setVisibility(View.GONE);
                quotesDetails.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //no implementation needed
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //no implementation needed
            }
        };
        AnimationUtils.stopAnimatorSet(animatorSet);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(quotesDetails, View.TRANSLATION_X, lastPositionX - quotesDetails.getWidth()),
                ObjectAnimator.ofFloat(quotesDetails, View.TRANSLATION_Y, lastPositionY - quotesDetails.getHeight() * 3),
                ObjectAnimator.ofFloat(circleView, View.SCALE_X, toValue),
                ObjectAnimator.ofFloat(circleView, View.SCALE_Y, toValue),
                ObjectAnimator.ofFloat(quotesDetails, View.SCALE_X, toValue),
                ObjectAnimator.ofFloat(quotesDetails, View.SCALE_Y, toValue),
                ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_X, 1),
                ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_Y, 1));
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(listener);
        animatorSet.setDuration(AnimationUtils.ANIMATION_DURATION_400);
        animatorSet.start();
    }

    private void showDetails(final RecyclerViewItem item) {
        float fromValue = 0f;
        float toValue = 6f;

        Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                circleView.setVisibility(View.VISIBLE);
                quotesDetails.setVisibility(View.VISIBLE);
                quoteTextView.setText(item.getTitle());
                authorNameTextView.setText(item.getSubtitle());
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //no implementation needed
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                //no implementation needed
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                //no implementation needed
            }
        };
        AnimationUtils.stopAnimatorSet(animatorSet);
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(quotesDetails, View.TRANSLATION_X, UiUtils.getScreenWidth(this) / 8),
                ObjectAnimator.ofFloat(quotesDetails, View.TRANSLATION_Y, UiUtils.getScreenHeight(this) / 8),
                // scales the details view
                ObjectAnimator.ofFloat(circleView, View.SCALE_X, toValue),
                ObjectAnimator.ofFloat(circleView, View.SCALE_Y, toValue),
                // scales the quote details
                ObjectAnimator.ofFloat(quotesDetails, View.SCALE_X, 1),
                ObjectAnimator.ofFloat(quotesDetails, View.SCALE_Y, 1),
                // scales the fab
                ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_X, fromValue),
                ObjectAnimator.ofFloat(floatingActionButton, View.SCALE_Y, fromValue));
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(listener);
        animatorSet.setDuration(AnimationUtils.ANIMATION_DURATION_400);
        animatorSet.start();
    }

    private void translateDetails(float x, float y) {
        lastPositionX = x;
        lastPositionY = y;
        AnimatorSet translateAnimatorSet = new AnimatorSet();
        translateAnimatorSet.playTogether(
                ObjectAnimator.ofFloat(circleView, View.TRANSLATION_X, x - circleView.getWidth()),
                ObjectAnimator.ofFloat(circleView, View.TRANSLATION_Y, y - circleView.getHeight() * 2),
                ObjectAnimator.ofFloat(quotesDetails, View.TRANSLATION_X, x - quotesDetails.getWidth()),
                ObjectAnimator.ofFloat(quotesDetails, View.TRANSLATION_Y, y - quotesDetails.getHeight() * 3)
        );
        translateAnimatorSet.setDuration(0).start();
    }
}
