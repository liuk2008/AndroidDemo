package com.android.demo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.base.fragment.BaseFragment;
import com.android.common.refreshview.MyCommonAdapter;
import com.android.common.refreshview.MyRefreshView;
import com.android.common.refreshview.MyViewHolder;
import com.android.common.utils.common.LogUtils;
import com.android.common.utils.common.ToastUtils;
import com.android.common.utils.view.StatusBarUtils;
import com.android.common.utils.view.ToolbarUtil;
import com.android.demo.R;
import com.android.demo.mvp.entity.FinanceListInfo;
import com.android.demo.mvp.model.retrofit.RetrofitDemo;
import com.android.network.callback.Callback;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 1、设置标题栏根据页面滑动渐变
 * 2、测试下拉刷新、上拉加载、点击屏幕刷新等功能
 * 3、测试Loading页面，加载失败页面，无数据页面
 * 4、可自定义对应状态的view
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private View viewTitle;
    private int scrollMaxHeight;
    private MyRefreshView refreshView;
    private MyCommonAdapter<FinanceListInfo.ListBean> adapter;
    private int index = 1, pageSize;
    private RetrofitDemo retrofitDemo;

    {
        super.TAG = TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return setRootView(inflater, R.layout.fragment_home);
    }

    @Override
    public void initRootViews(View baseRootView) {
        super.initRootViews(baseRootView);
        ToolbarUtil.configTitle(baseRootView, "首页", View.GONE);
        viewTitle = baseRootView.findViewById(R.id.view_title);
        refreshView = baseRootView.findViewById(R.id.refreshView);
        // 设置标题栏
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

        // 设置refreshView
        adapter = new MyCommonAdapter<FinanceListInfo.ListBean>(R.layout.item_view) {
            @Override
            public void convert(MyViewHolder holder, FinanceListInfo.ListBean data, int position) {
                holder.setText(R.id.item_title, data.loanTitle);
            }
        };
        refreshView.setAdapter(adapter);
        setRefreshView();
        request();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        retrofitDemo.cancelAll();
    }

    private void setRefreshView() {
        // 下拉刷新
        refreshView.setOnRefreshListener(new MyRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index = 1;
                request();
            }
        });
        // 上拉加载
        refreshView.setOnLoadMoreListener(new MyRefreshView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                index++;
                if (index <= pageSize)
                    request();
            }
        });
        // 重新加载数据
        refreshView.setOnReLoadListener(new MyRefreshView.OnReLoadListener() {
            @Override
            public void onReload() {
                request();
            }
        });
        // item点击事件
        adapter.setOnItemClickListener(new MyCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(getContext(), "position:" + position);
            }
        });
    }

    private void request() {
        retrofitDemo = new RetrofitDemo();
        retrofitDemo.financeList(index, new Callback<FinanceListInfo>() {
            @Override
            public void onSuccess(FinanceListInfo financeListInfo) {
                refreshView.refreshComplete();
                if (financeListInfo == null || financeListInfo.list == null || financeListInfo.list.size() == 0) {
                    refreshView.showEmptyView();
                    return;
                }
                int size = financeListInfo.pageInfo.total / 40;
                pageSize = financeListInfo.pageInfo.total % 40 == 0 ? size : size + 1;
                if (index == 1)
                    adapter.cleanData();
                adapter.appendData(financeListInfo.list);
                refreshView.setLoadMore(index < size);
            }

            @Override
            public void onFail(int resultCode, String msg, String data) {
                LogUtils.logd(TAG, "resultCode:" + resultCode + ", msg:" + msg + ", data:" + data);
                refreshView.refreshComplete();
                if (index > 1)
                    index--;
                else
                    adapter.cleanData();
                refreshView.showErrorView();
            }
        });
    }


}
