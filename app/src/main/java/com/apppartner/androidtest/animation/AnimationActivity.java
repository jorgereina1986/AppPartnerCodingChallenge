package com.apppartner.androidtest.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.apppartner.androidtest.MainActivity;
import com.apppartner.androidtest.R;

/**
 * Screen that displays the AppPartner icon.
 * The icon can be moved around on the screen as well as animated.
 * <p>
 * Created on Aug 28, 2016
 *
 * @author Thomas Colligan
 */
public class AnimationActivity extends AppCompatActivity {
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private Button animationBtn;
    private ImageView logoIv;
    private ViewGroup rootLayout;
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    private int _xDelta;
    private int _yDelta;
    //==============================================================================================
    // Static Class Methods
    //==============================================================================================

    public static void start(Context context) {
        Intent starter = new Intent(context, AnimationActivity.class);
        context.startActivity(starter);
    }

    //==============================================================================================
    // Lifecycle Methods
    //==============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        logoIv = (ImageView) findViewById(R.id.logo_iv);
        animationBtn = (Button) findViewById(R.id.anim_btn);
        rootLayout = (ViewGroup) findViewById(R.id.root_layout);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(692,656);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        logoIv.setLayoutParams(layoutParams);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: When the fade button is clicked, you must animate the AppPartner Icon.
        // TODO: It should fade from 100% alpha to 0% alpha, and then from 0% alpha to 100% alpha
        animationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleAnimation();
            }
        });

        // TODO: The user should be able to touch and drag the AppPartner Icon around the screen.

        logoIv.setOnTouchListener(new ChoiceTouchListener());

    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    lParams.removeRule(RelativeLayout.CENTER_IN_PARENT);
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    view.animate().alpha(1f);
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -(250);
                    layoutParams.bottomMargin = -(250);
                    view.setLayoutParams(layoutParams);
                    view.setAlpha(.3f);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }


    private void toggleAnimation() {
        if (logoIv.getAlpha() == 1) {
            logoIv.animate().alpha(0f);
        } else
            logoIv.animate().alpha(1f);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
