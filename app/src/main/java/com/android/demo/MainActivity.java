package com.android.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.demo.fragment.HomeFragment;
import com.android.demo.fragment.MineFragment;
import com.android.demo.fragment.WorkFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Class<?>[] fragmentArray = {HomeFragment.class, WorkFragment.class, MineFragment.class};
    private String tabs[] = {"首页", "工作", "我的"};
    private int tabViewArray[] = {R.drawable.tab_home, R.drawable.tab_message, R.drawable.tab_mycenter};
    private ArrayList<TextView> tabNames = new ArrayList<>();
    private static final String TAG = MainActivity.class.getSimpleName();

    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        layoutInflater = LayoutInflater.from(this);
        mTabHost = findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < fragmentArray.length; i++) {
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tabs[i]).setIndicator(getTabItemView(i));
            // 将按钮添加到选项卡中
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
        }
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabs.length; i++) {
                    if (tabId != null && tabId.equals(tabs[i])) {
                        tabNames.get(i).setTextColor(getResources().getColor(R.color.tab_text_color_selected));
                    } else {
                        tabNames.get(i).setTextColor(getResources().getColor(R.color.tab_text_color_normal));
                    }
                }
            }
        });
    }

    // 创建Tab
    private View getTabItemView(int index) {
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = view.findViewById(R.id.imageview);
        imageView.setImageResource(tabViewArray[index]);
        TextView textView = view.findViewById(R.id.item_title);
        tabNames.add(textView);
        textView.setText(tabs[index]);
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
