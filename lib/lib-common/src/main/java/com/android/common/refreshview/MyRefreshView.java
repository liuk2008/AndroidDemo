package com.android.common.refreshview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.android.common.R;

/**
 * 自定义列表View
 * 1、实现下拉刷新、上拉加载功能，默认开启
 * 2、包装Adapter，提供item点击、添加数据功能
 * 3、
 */
public class MyRefreshView extends LinearLayout {

    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private boolean isLoadMore = true, isLoading = false;
    private MyCommonAdapter wrapAdapter;

    public MyRefreshView(Context context) {
        super(context);
        initView(context);
    }

    public MyRefreshView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyRefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        View rooView = View.inflate(context, R.layout.refreshview_layout, null);
        recyclerView = rooView.findViewById(R.id.recyclerView);
        swipeRefreshLayout = rooView.findViewById(R.id.swipeRefreshLayout);
        this.addView(rooView);
        setSwipeRefreshLayout();
        setRecyclerView();
    }

    private void setSwipeRefreshLayout() {
        // 设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        swipeRefreshLayout.setProgressViewOffset(true, 50, 200);
        // 设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        // 设置下拉圆圈上的颜色
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        // onRefresh 的回调只有在手势下拉的情况下才会触发，通过 setRefreshing 只能调用刷新的动画是否显示
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout.isRefreshing() && onRefreshListener != null) {
                    onRefreshListener.onRefresh(); // 下拉刷新功能
                }
            }
        });
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager.findLastVisibleItemPosition() == (wrapAdapter.getItemCount() - 1)) {//最后一条可见
                        if (!isLoading && isLoadMore && onLoadMoreListener != null) {
                            isLoading = true;
                            onLoadMoreListener.onLoadMore(); // 上拉加载功能
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (onScrollChangeListener != null) {
                    int scrollY = recyclerView.computeVerticalScrollOffset();
                    int scrollX = recyclerView.computeHorizontalScrollOffset();
                    onScrollChangeListener.onScrollChange(recyclerView, scrollX, scrollY);
                }
            }
        });
    }

    /**
     * 设置RecyclerView中的Adapter：
     * <p>
     * 1、可传入自定义RecyclerView.Adapter
     * <p>
     * 2、可使用 MyCommonAdapter
     *
     * @param adapter RecyclerView.Adapter
     */
    public void setAdapter(final RecyclerView.Adapter adapter) {
        if (adapter instanceof MyCommonAdapter) {
            wrapAdapter = (MyCommonAdapter) adapter;
        } else {
            // 包装自定义Adapter，共用CommonAdapter实现数据包装
            wrapAdapter = new MyCommonAdapter(adapter) {
                @Override
                public void convert(MyViewHolder holder, Object o, int position) {

                }
            };
        }
        recyclerView.setAdapter(wrapAdapter);
    }

    /**
     * 显示进度条
     */
    public void refreshStart() {
        swipeRefreshLayout.setRefreshing(true);
    }

    /**
     * 隐藏进度条
     */
    public void refreshComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 默认开启下拉刷新功能：
     * <p>
     * 关闭刷新功能需在refreshView.refreshStart()前调用此方法
     *
     * @param isRefresh false：关闭下拉刷新功能，true：打开下拉刷新功能
     */
    public void setRefresh(boolean isRefresh) {
        swipeRefreshLayout.setEnabled(isRefresh);
    }

    /**
     * 默认关闭上拉加载功能：
     * <p>
     * 多用于列表分页加载数据，当最后一页数据加载完毕时关闭此功能
     *
     * @param isLoadMore false：关闭上拉加载功能，true：打开上拉加载功能
     */
    public void setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        isLoading = false;
        wrapAdapter.setLoadMore(isLoadMore);
    }

    private OnRefreshListener onRefreshListener;
    private OnLoadMoreListener onLoadMoreListener;
    private OnScrollChangeListener onScrollChangeListener;

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnScrollChangeListener {
        void onScrollChange(RecyclerView recyclerView, int scrollX, int scrollY);
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }


}
