package com.android.common.widget.webview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.common.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;

// 显示图片，可放大缩小
public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("image");
        PhotoView imageView = new PhotoView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        imageView.setBackgroundColor(Color.BLACK);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.common_image_failed);
        Glide.with(this).load(url).apply(requestOptions).thumbnail(0.1f).into(imageView);
        configStatusBar();
        setContentView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void configStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
                    View statusBarView = getWindow().findViewById(identifier);
                    statusBarView.setBackgroundColor(Color.BLACK);
                    getWindow().getDecorView().removeOnLayoutChangeListener(this);
                }
            });
        }
    }

}
