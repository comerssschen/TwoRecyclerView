package com.weipan.kotilin;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：create by comersss on 2019/5/15 11:52
 * 邮箱：904359289@qq.com
 */
public class MainActivity extends AppCompatActivity {
    LinearLayoutManager oneLayoutM;
    LinearLayoutManager twoLayoutM;
    OneAdapter oneAdapter;
    TwoAdapter twoAdapter;
    DividerItemDecoration oneitemD;
    DividerItemDecoration twoitemD;
    List<OneBean> oneData = new ArrayList<>();
    List<TwoBean> twoData = new ArrayList<>();
    Handler handler;
    Boolean rightClick = false;
    @BindView(R.id.recyc_one)
    RecyclerView recycOne;
    @BindView(R.id.recyc_two)
    RecyclerView recycTwo;
    private int divider = ConvertUtils.px2dp(20);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        handler = new Handler();
        //获取asset目录下的资源文件
        String assetsData = getAssetsData("sort.json");
        Gson gson = new Gson();
        DataBean dataBean = gson.fromJson(assetsData, DataBean.class);
        //初始化左侧列表数据
        List<DataBean.CategoryOneArrayBean> categoryOneArray = dataBean.getCategoryOneArray();
        for (DataBean.CategoryOneArrayBean categoryOneArrayBean : categoryOneArray) {
            oneData.add(new OneBean(Integer.parseInt(categoryOneArrayBean.getCacode()), categoryOneArrayBean.getName()));
            twoData.add(new TwoBean(Integer.parseInt(categoryOneArrayBean.getCacode()), categoryOneArrayBean.getName(), true, TwoBean.TITLE));
            for (DataBean.CategoryOneArrayBean.CategoryTwoArrayBean categoryTwoArrayBean : categoryOneArrayBean.getCategoryTwoArray()) {
                twoData.add(new TwoBean(Integer.parseInt(categoryTwoArrayBean.getCacode()), categoryTwoArrayBean.getName(), false, TwoBean.CONTENT));
            }

        }


    }

    //从资源文件中获取分类json
    private String getAssetsData(String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", e.getMessage());
            return result;
        }
    }


    private void initView() {
        oneLayoutM = new LinearLayoutManager(this);
        twoLayoutM = new GridLayoutManager(this, 2);
        oneAdapter = new OneAdapter(R.layout.item_one, oneData);
        twoAdapter = new TwoAdapter(twoData);

        recycOne.setLayoutManager(oneLayoutM);
        recycTwo.setLayoutManager(twoLayoutM);
        oneitemD = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycOne.addItemDecoration(oneitemD);

//        twoitemD = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        recycTwo.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
                final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                final int spanCount = layoutManager.getSpanCount();
                int layoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                if (lp.getSpanSize() != spanCount) {
                    //左边间距
                    if (layoutPosition % 2 == 1) {
                        outRect.left = divider / 2;
                        outRect.right = divider;
                    } else {
                        outRect.left = divider;
                        outRect.right = divider / 2;
                    }
                }
                outRect.top = divider;
            }
        });

        recycOne.setAdapter(oneAdapter);
        recycTwo.setAdapter(twoAdapter);
        oneAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                rightClick = true;
                //点击变色
                select(position);
                //点击右侧滚动
                //判断右侧滚动到哪里
                int twoI = 0;
                while (twoI < twoData.size()) {
                    if (twoData.get(twoI).getId() == oneData.get(position).getId()) {
                        break;
                    }
                    twoI++;
                }
                RecycUtil.moveToPositAndTop(twoI, twoLayoutM, recycTwo, handler);
            }
        });

        recycTwo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //切断子母列表循环联调
                if (rightClick == false && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //判断当前子列表显示哪个id的内容
                    int now = 0;
                    int first = twoLayoutM.findFirstVisibleItemPosition();
                    if (twoData.get(first).isTitle()) {
                        now = twoData.get(first).getId();
                    } else {
                        if (twoData.get(first).getId() + 1 > oneData.get(oneData.size() - 1).getId()) {
                            now = twoData.get(first).getId();
                        } else {
                            now = twoData.get(first).getId() + 1;
                        }
                    }
                    //滚动主列表
                    RecycUtil.moveToPositAndCenter(now, oneLayoutM, recycOne, handler);
                    select(now);

                } else if (rightClick == true && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    rightClick = false;
                } else if (rightClick == true && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    rightClick = false;
                }

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


    private void select(int position) {
        int i = oneAdapter.index;
        oneAdapter.index = position;
        if (i >= 0) {
            oneAdapter.notifyItemChanged(i);
        }
        oneAdapter.notifyItemChanged(position);
        Log.i("test", " i:" + i + " position:" + position);
    }

}
