package com.weipan.kotilin;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

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
        int i = 0;
        int j = 0;
        while (i < 20) {
            oneData.add(new OneBean(i, "标题：" + i));
            j = 0;
//            twoData.add(new TwoBean(i, "标题：" + i, true));
            while (j < 5) {
                twoData.add(new TwoBean(i, "内容:" + j, false));
                j++;
            }
            i++;
        }
    }


    private void initView() {
        oneLayoutM = new LinearLayoutManager(this);
        twoLayoutM = new GridLayoutManager(this, 2);

        recycOne.setLayoutManager(oneLayoutM);
        recycTwo.setLayoutManager(twoLayoutM);
        oneitemD = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        twoitemD = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycOne.addItemDecoration(oneitemD);
        recycTwo.addItemDecoration(twoitemD);
        oneAdapter = new OneAdapter(R.layout.item_one, oneData);
        twoAdapter = new TwoAdapter(R.layout.item_two, twoData);
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
