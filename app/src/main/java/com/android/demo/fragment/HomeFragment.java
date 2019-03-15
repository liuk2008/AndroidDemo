package com.android.demo.fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.common.base.fragment.BaseFragment;
import com.android.common.refreshview.MyCommonAdapter;
import com.android.common.refreshview.MyRefreshView;
import com.android.common.refreshview.MyViewHolder;
import com.android.common.utils.StatusBarUtils;
import com.android.common.utils.ToolbarUtil;
import com.android.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、设置标题栏根据页面滑动渐变
 * 2、测试下拉刷新、上拉加载效果
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View viewTitle;
    private int scrollMaxHeight;
    private MyRefreshView refreshView;
    private MyCommonAdapter<String> adapter;
    private int index = 1;

    {
        super.TAG = TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setRootView(inflater, R.layout.fragment_home);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitlebar(baseRootView, "首页", View.GONE);
        refreshView = baseRootView.findViewById(R.id.refreshView);
        viewTitle = baseRootView.findViewById(R.id.view_title);

        viewTitle.setAlpha(0);
        // 设置系统状态栏透明，通过设置标题栏Padding高度=状态栏高度，保持颜色一致
        viewTitle.setPadding(0, StatusBarUtils.getStatusBarHeight(getActivity()), 0, 0);
        scrollMaxHeight = (getResources().getDisplayMetrics().heightPixels) / 3;
        refreshView.setOnScrollChangeListener(new MyRefreshView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(RecyclerView recyclerView, int scrollX, int scrollY) {
                float alpha = 0;
                if (scrollY <= 0) {
                    viewTitle.setAlpha(0);
                } else if (scrollY < scrollMaxHeight) {
                    alpha = 1 - (scrollMaxHeight - scrollY) * 1f / scrollMaxHeight;
                    viewTitle.setAlpha(alpha);
                } else {
                    viewTitle.setAlpha(1);
                }
            }
        });

        adapter = new MyCommonAdapter<String>(getContext(), R.layout.item_view) {
            @Override
            public void convert(MyViewHolder holder, String data, int position) {
                holder.setText(R.id.item_title, data);
            }
        };

        refreshView.refreshStart();
        refreshView.setAdapter(adapter);
        setData("测试");

        refreshView.setOnRefreshListener(new MyRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index = 1;
                setData("下拉刷新");
            }
        });

        refreshView.setOnLoadMoreListener(new MyRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                index++;
                if (index <= 2)
                    setData("上拉加载");

            }
        });

        adapter.setOnItemClickListener(new MyCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });

    }

    private void setData(final String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 60; i++) {
                    list.add(name + i);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ("下拉刷新".equals(name)) {
                            adapter.cleanData();
                        }
                        adapter.appendData(list);
                        refreshView.setLoadMore(index < 2);
                        refreshView.refreshComplete();
                    }
                });
            }
        }).start();
    }

}
