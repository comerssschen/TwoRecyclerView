package com.weipan.kotilin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.felhr.usbserial.UsbSerialInterface;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.superscan.lib.device.usb.AsyncUsbScanner;
import com.superscan.lib.device.usb.UsbToolKit;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;
import com.tx.printlib.Const;
import com.tx.printlib.UsbPrinter;
import com.weipan.kotilin.adapter.BsAdapter;
import com.weipan.kotilin.adapter.OneAdapter;
import com.weipan.kotilin.adapter.TwoAdapter;
import com.weipan.kotilin.bean.CarBean;
import com.weipan.kotilin.bean.DataBean;
import com.weipan.kotilin.bean.OneBean;
import com.weipan.kotilin.bean.TwoBean;
import com.weipan.kotilin.bean.json.ArgScanQRCode;
import com.weipan.kotilin.bean.json.ResultFacePay;
import com.weipan.kotilin.bean.json.ResultScanQRCode;
import com.weipan.kotilin.helper.CountDownHelper;
import com.weipan.kotilin.ui.BaseActivity;
import com.weipan.kotilin.ui.SucessActivity;
import com.weipan.kotilin.view.CloseConfirmDialog;
import com.weipan.kotilin.view.LoadingDialog;
import com.weipan.kotilin.view.PayPopupWindow;
import com.weipan.kotilin.view.ScanQrCodeDialog;
import com.weipan.kotilin.view.SpaceItemDecoration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：create by comersss on 2019/5/15 11:52
 * 邮箱：904359289@qq.com
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.recyc_one)
    RecyclerView recycOne;
    @BindView(R.id.recyc_two)
    RecyclerView recycTwo;
    @BindView(R.id.tv_total_count)
    TextView tvTotalCount;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_gocar)
    TextView tvGocar;
    @BindView(R.id.rl_order)
    LinearLayout rlOrder;
    @BindView(R.id.bs_recyclerview)
    RecyclerView bsRecyclerview;
    @BindView(R.id.bs_tv_totalmoney)
    TextView bsTvTotalmoney;
    @BindView(R.id.bs_tv_totalcount)
    TextView bsTvTotalcount;
    @BindView(R.id.bs_bt_gopay)
    TextView bsBtGopay;
    @BindView(R.id.rl_car)
    LinearLayout rlCar;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_back)
    TextView tvBack;
    private int divider = ConvertUtils.dp2px(10);
    private int[] itemRes = {
            R.drawable.group1_item1, R.drawable.group1_item2, R.drawable.group1_item3, R.drawable.group1_item4, R.drawable.group1_item5, R.drawable.group1_item6, R.drawable.group1_item7, R.drawable.group1_item8, R.drawable.group1_item9, R.drawable.group1_item10, R.drawable.group1_item11, R.drawable.group1_item12, R.drawable.group1_item13, R.drawable.group1_item14, R.drawable.group1_item15, R.drawable.group1_item16, R.drawable.group1_item17, R.drawable.group1_item18
            , R.drawable.group2_item1, R.drawable.group2_item2, R.drawable.group2_item3, R.drawable.group2_item4, R.drawable.group2_item5, R.drawable.group2_item6, R.drawable.group2_item7
            , R.drawable.group3_item1, R.drawable.group3_item2, R.drawable.group3_item3, R.drawable.group3_item4, R.drawable.group3_item5
            , R.drawable.group4_item1, R.drawable.group4_item2, R.drawable.group4_item3, R.drawable.group4_item4, R.drawable.group4_item5, R.drawable.group4_item6, R.drawable.group4_item7
            , R.drawable.group5_item1, R.drawable.group5_item2, R.drawable.group5_item3, R.drawable.group5_item4, R.drawable.group5_item5
            , R.drawable.group6_item1, R.drawable.group6_item2, R.drawable.group6_item3, R.drawable.group6_item4, R.drawable.group6_item5, R.drawable.group6_item6
    };
    private int[] groupBgCheck = {R.drawable.group1_check, R.drawable.group2_check, R.drawable.group3_check, R.drawable.group4_check, R.drawable.group5_check, R.drawable.group6_check};
    private int[] groupBgUnCheck = {R.drawable.group1_uncheck, R.drawable.group2_uncheck, R.drawable.group3_uncheck, R.drawable.group4_uncheck, R.drawable.group5_uncheck, R.drawable.group6_uncheck};
    private int totalCount;//总个数
    private String totalMoney;//总金额
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    LinearLayoutManager oneLayoutM;
    LinearLayoutManager twoLayoutM;
    OneAdapter oneAdapter;
    TwoAdapter twoAdapter;
    List<OneBean> oneData = new ArrayList<>();
    List<TwoBean> twoData = new ArrayList<>();
    ArrayList<CarBean> carList = new ArrayList<>();
    CarBean carBean;
    Handler handler;
    Boolean rightClick = false;
    private BsAdapter bsAdapter;
    private CountDownHelper helper;
    private CloseConfirmDialog closeConfirmDialog;
    private String realPayMoney;
    private PayPopupWindow mPhotoPopupWindow;
    private ScanQrCodeDialog scanQrCodeDialog;
    private Gson gson = new Gson();
    private HashMap localHashMap;
    private UsbPrinter mUsbPrinter;
    private AsyncUsbScanner asyncUsbScanner;
    private String outTratNum;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
        initScan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        helper = new CountDownHelper(tvCancle, 90, 1, "取消点餐");
        helper.setOnFinishListener(new CountDownHelper.OnFinishListener() {
            @Override
            public void fin() {
                if (!(ActivityUtils.getTopActivity() instanceof MainActivity)) {
                    return;
                }
                if (ObjectUtils.isEmpty(closeConfirmDialog)) {
                    closeConfirmDialog = new CloseConfirmDialog(MainActivity.this);
                    closeConfirmDialog.setOnCloseOrderLitener(new CloseConfirmDialog.OnCloseOrderLitener() {
                        @Override
                        public void close() {
                            finish();
                        }
                    });
                }
                if (!closeConfirmDialog.isShowing()) {
                    closeConfirmDialog.show();
                }

            }
        });
        helper.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        helper.stop();
        helper = null;
    }

    private void initData() {
        handler = new Handler();
        //获取asset目录下的资源文件
        String assetsData = getAssetsData("sort.json");
        Gson gson = new Gson();
        int goodcar = R.drawable.goodcar;
        DataBean dataBean = gson.fromJson(assetsData, DataBean.class);
        //初始化左侧列表数据
        List<DataBean.CategoryOneArrayBean> categoryOneArray = dataBean.getCategoryOneArray();

        int j = 0;
        for (int i = 0; i < categoryOneArray.size(); i++) {
            oneData.add(new OneBean(Integer.parseInt(categoryOneArray.get(i).getCacode()), categoryOneArray.get(i).getName(), groupBgCheck[i], groupBgUnCheck[i]));
//            twoData.add(new TwoBean(Integer.parseInt(categoryOneArray.get(i).getCacode()), categoryOneArray.get(i).getName(), 0, "", true, TwoBean.TITLE));
            List<DataBean.CategoryOneArrayBean.CategoryTwoArrayBean> categoryTwoArray = categoryOneArray.get(i).getCategoryTwoArray();
            for (int i1 = 0; i1 < categoryTwoArray.size(); i1++) {
                DataBean.CategoryOneArrayBean.CategoryTwoArrayBean categoryTwoArrayBean = categoryTwoArray.get(i1);

                twoData.add(new TwoBean(Integer.parseInt(categoryTwoArrayBean.getCacode()), categoryTwoArrayBean.getGoodsId(), categoryTwoArrayBean.getName(), itemRes[j], categoryTwoArrayBean.getPrice(), false, TwoBean.CONTENT));
                if (categoryTwoArray.size() % 2 != 0 && i1 == categoryTwoArray.size() - 1) {
                    twoData.add(new TwoBean(Integer.parseInt(categoryTwoArrayBean.getCacode()), categoryTwoArrayBean.getGoodsId(), categoryTwoArrayBean.getName(), itemRes[j], categoryTwoArrayBean.getPrice(), false, TwoBean.EMPTY));
                }
                j++;
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
        loadingDialog = new LoadingDialog(MainActivity.this, "支付中...");
        oneLayoutM = new LinearLayoutManager(this);
        twoLayoutM = new GridLayoutManager(this, 2);
        oneAdapter = new OneAdapter(R.layout.item_one, oneData);
        twoAdapter = new TwoAdapter(twoData);

//        twoAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
//                return twoData.get(position).isTitle() ? 2 : 1;
//            }
//        });
        recycOne.setLayoutManager(oneLayoutM);
        recycTwo.setLayoutManager(twoLayoutM);
        recycOne.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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
        select(0);
        oneAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SoundPlayUtils.play(1);
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
//                            now = twoData.get(first).getId() + 1;
                            now = twoData.get(first).getId();
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

        twoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SoundPlayUtils.play(1);
                addCar(twoData.get(position));
            }
        });
        twoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_add) {
                    SoundPlayUtils.play(1);
                    addCar(twoData.get(position));
                }
            }
        });


        bsAdapter = new BsAdapter(R.layout.item_bs_car);
        bsRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
        bsRecyclerview.setAdapter(bsAdapter);
        bsRecyclerview.addItemDecoration(new SpaceItemDecoration(ConvertUtils.dp2px(10)));
        bsAdapter.setmListener(new BsAdapter.mListener() {
            @Override
            public void updateGoodsNumber(int number, CarBean item, final int position) {
                if (number == 0) {
                    bsAdapter.remove(position);
                    carList.remove(position);
                } else {
                    carList.get(position).setConut(number);
                    carList.get(position).setTotalPrice(decimalFormat.format(number * Float.parseFloat(carList.get(position).getPrice())));

                    Handler handler = new Handler();
                    Runnable r = new Runnable() {
                        public void run() {
                            bsAdapter.notifyItemChanged(position);
                        }
                    };
                    handler.post(r);


//                    bsAdapter.notifyDataSetChanged();
                }
//                bsAdapter.notifyDataSetChanged();
                updateCar();
            }
        });
    }

    private void updateCar() {
        float price = 0.00f;
        int count = 0;
        if (ObjectUtils.isEmpty(carList) && carList.size() == 0) {
            rlOrder.setVisibility(View.VISIBLE);
            rlCar.setVisibility(View.GONE);
            updateView();
        } else {
            for (CarBean bean1 : carList) {
                price = price + Float.parseFloat(bean1.getTotalPrice());
                count = count + bean1.getConut();
            }
        }
        totalCount = count;
        totalMoney = decimalFormat.format(price);
        bsTvTotalmoney.setText(totalMoney);
        bsTvTotalcount.setText("(共" + totalCount + "件商品)");
    }

    private void addCar(TwoBean twoBean) {
        carBean = new CarBean();
        carBean.setGoodsId(twoBean.getGoodsId());
        carBean.setBgImg(twoBean.getBgImg());
        carBean.setConut(1);
        carBean.setName(twoBean.getName());
        carBean.setPrice(twoBean.getPrice());
        carBean.setTotalPrice(twoBean.getPrice());
        boolean isExist = false;
        int position = carList.size();
        for (int i = 0; i < carList.size(); i++) {
            if (ObjectUtils.equals(carList.get(i).getGoodsId(), twoBean.getGoodsId())) {
                isExist = true;
                position = i;
                carList.get(i).setConut(carList.get(i).getConut() + 1);
                carList.get(i).setTotalPrice(decimalFormat.format((Float.parseFloat(carList.get(i).getTotalPrice())
                        + Float.parseFloat(twoBean.getPrice()))));
            }
        }
        if (isExist) {
            bsAdapter.notifyItemChanged(position);
        } else {
            carList.add(carBean);
            bsAdapter.addData(position, carBean);
        }
        updateView();


    }

    private void updateView() {
        float price = 0.00f;
        int count = 0;
        if (ObjectUtils.isEmpty(carList) && carList.size() == 0) {
//            btGoPay.setEnabled(false);
            tvTotalCount.setVisibility(View.INVISIBLE);
        } else {
            tvTotalCount.setVisibility(View.VISIBLE);
            for (CarBean bean1 : carList) {
                price = price + Float.parseFloat(bean1.getTotalPrice());
                count = count + bean1.getConut();
            }
//            btGoPay.setEnabled(true);
        }
        totalCount = count;
        totalMoney = decimalFormat.format(price);
        tvTotalMoney.setText(totalMoney);
        tvTotalCount.setText(totalCount + "");
    }

    private void initScan() {
        mUsbPrinter = new UsbPrinter(MainActivity.this);
        UsbToolKit usbToolKit = new UsbToolKit(MainActivity.this);
        List<UsbDevice> usbDevices = usbToolKit.listDevices();
        assert usbDevices.size() > 0;
        asyncUsbScanner = new AsyncUsbScanner(usbDevices.get(0), usbToolKit.getUsbManager(), new UsbSerialInterface.UsbReadCallback() {
            @Override
            public void onReceivedData(final byte[] bytes) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!ObjectUtils.isEmpty(scanQrCodeDialog) && scanQrCodeDialog.isShowing()) {
                            scanQRCode(new String(bytes).trim());
                        }
                    }
                });
            }
        });
        asyncUsbScanner.open();
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

    private UsbDevice getCorrectDevice() {
        final UsbManager usbMgr = (UsbManager) getSystemService(Context.USB_SERVICE);
        final Map<String, UsbDevice> devMap = usbMgr.getDeviceList();
        for (String name : devMap.keySet()) {
            Log.v("test", "check device: " + name);
            if (UsbPrinter.checkPrinter(devMap.get(name)))
                return devMap.get(name);
        }
        return null;
    }


    public void DouHaoPrint(ArrayList<CarBean> menus, String payType) {
        final UsbDevice dev = getCorrectDevice();
        if (dev != null && mUsbPrinter.open(dev)) {
            mUsbPrinter.init();
            mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_CENTER, 0);
            mUsbPrinter.outputStringLn("杭州微盘每日付收款明细");
            mUsbPrinter.outputStringLn("\n********************************");
            mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_LEFT, 0);
            mUsbPrinter.outputStringLn("店 员 号：032  POS号：32");
            mUsbPrinter.outputStringLn("下单时间：" + TimeUtils.getNowString());
            mUsbPrinter.outputStringLn("支付方式：" + payType);
            mUsbPrinter.outputStringLn("********************************");
            mUsbPrinter.outputStringLn("取餐号码：");
            mUsbPrinter.doFunction(Const.TX_FONT_SIZE, Const.TX_SIZE_3X, Const.TX_SIZE_3X);
            mUsbPrinter.outputStringLn("    " + Constant.OrderNum);
            mUsbPrinter.resetFont();
            mUsbPrinter.outputStringLn("\n********************************");
            printString("商品名称/数量/单位", "合计");
            mUsbPrinter.outputStringLn("--------------------------------");
            float price = 0.00f;
            for (CarBean menu : menus) {
                printString(menu.getName() + " * " + menu.getConut(), menu.getPrice());
                price = price + Float.parseFloat(menu.getTotalPrice());
            }

            mUsbPrinter.outputStringLn("--------------------------------");
            printString("自助收银", "共" + totalCount + "件");
            printString("商品合计", "￥" + price);
            printString("优惠金额", "-￥0.00");
            printString("优惠券金额", "-￥0.00");
            mUsbPrinter.outputStringLn(" ");
            printString("应付金额", "￥" + price);
            printString("实际支付金额", "￥" + realPayMoney);
            mUsbPrinter.outputStringLn("\n依法预付费卡、第三方卡、支付优惠的支付方式其消费金额不再重复开立发票");
            mUsbPrinter.outputStringLn("\n--------------------------------");
            mUsbPrinter.doFunction(Const.TX_FEED, 10, 0);

            mUsbPrinter.doFunction(Const.TX_UNIT_TYPE, Const.TX_UNIT_PIXEL, 0);
            mUsbPrinter.doFunction(Const.TX_FEED, 140, 0);
            mUsbPrinter.doFunction(Const.TX_CUT, Const.TX_CUT_FULL, 0);
            mUsbPrinter.close();
        }

    }

    private void printString(String left, String right) {
        int lLength = 0;
        int rLength = 0;
        try {
            lLength = left.getBytes("GBK").length;
            rLength = right.getBytes("GBK").length;
            mUsbPrinter.doFunction(Const.TX_ALIGN, Const.TX_ALIGN_LEFT, 0);
            String nullString = "                                ";
            if (left.length() > 32) {
                mUsbPrinter.outputStringLn(left + nullString.substring(0, 64 - lLength - rLength) + right);
            } else {
                mUsbPrinter.outputStringLn(left + nullString.substring(0, 32 - lLength - rLength) + right);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showPayPopWindow() {
        mPhotoPopupWindow = new PayPopupWindow(MainActivity.this, "共" + totalCount + "件商品", "￥" + realPayMoney);
        mPhotoPopupWindow.setPopListener(new PayPopupWindow.PopLitener() {
            @Override
            public void onClosed() {
                mPhotoPopupWindow.dismiss();
            }

            @Override
            public void onPart1() {
                wxPay();
                mPhotoPopupWindow.dismiss();
            }

            @Override
            public void onPart2() {
                if (ObjectUtils.isEmpty(scanQrCodeDialog)) {
                    scanQrCodeDialog = new ScanQrCodeDialog(MainActivity.this);
                    scanQrCodeDialog.setOnScanResultLitener(new ScanQrCodeDialog.OnScanResultLitener() {
                        @Override
                        public void confirm(String result) {
                            scanQRCode(result);
                        }
                    });
                }
                scanQrCodeDialog.show();
            }

        });
        mPhotoPopupWindow.showAtLocation(getWindow().getDecorView(),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private void wxPay() {
        localHashMap = new HashMap();
        localHashMap.put("face_authtype", "FACEPAY");
        localHashMap.put("appid", Constant.appid);
        localHashMap.put("mch_id", Constant.mch_id);
        localHashMap.put("store_id", Constant.store_id);
        outTratNum = System.currentTimeMillis() + "";
        localHashMap.put("out_trade_no", outTratNum);
        localHashMap.put("total_fee", "1");
        localHashMap.put("ask_face_permit", "0");
        localHashMap.put("sub_appid", Constant.sub_appid);
        localHashMap.put("sub_mch_id", Constant.sub_mch_id);
        if (TextUtils.isEmpty(Constant.authInfo)) {
            Toast.makeText(MainActivity.this, "初始化失败，请退出重进", Toast.LENGTH_SHORT).show();
            return;
        }
        localHashMap.put("authinfo", Constant.authInfo);
        localHashMap.put("payresult", "SUCCESS");

        Log.i("test", "localHashMap =  " + localHashMap.toString());
        WxPayFace.getInstance().getWxpayfaceCode(localHashMap, new IWxPayfaceCallback() {
            public void response(final Map paramMap) throws RemoteException {
                Log.i("test", "response | getWxpayfaceCode " + paramMap);
                String code = paramMap.get("return_code").toString();
                if (TextUtils.equals(code, "SUCCESS")) {
                    String url = Constant.localhostUrl + "/api/Pay/FacePay?appid=" + Constant.appid + "&mch_id=" + Constant.mch_id + "&sub_appid=" + Constant.sub_appid + "&sub_mch_id=" + Constant.sub_mch_id + "&out_trade_no=" + outTratNum + "&total_fee=" + realPayMoney + "&openid=" + paramMap.get("openid").toString() + "&face_code=" + paramMap.get("face_code").toString();
                    OkGo.<String>get(url)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.i("test", "response.body() = " + response.body());
                                    try {
                                        ResultFacePay resultFacePay = gson.fromJson(response.body(), ResultFacePay.class);
                                        if (ObjectUtils.equals(resultFacePay.getCode(), "200")) {
                                            WxPayFace.getInstance().updateWxpayfacePayResult(localHashMap, new IWxPayfaceCallback() {
                                                public void response(Map paramMap) throws RemoteException {
                                                    doSuceess("微信扫脸支付");
                                                }
                                            });
                                        } else {
                                            SoundPlayUtils.play(4);
                                            ToastUtils.showShort(resultFacePay.getMsg());
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        SoundPlayUtils.play(4);
                                        ToastUtils.showShort("网络异常");
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    SoundPlayUtils.play(4);
                                    ToastUtils.showShort("网络异常：" + response.body());
                                }
                            });

                } else if (TextUtils.equals(code, "USER_CANCEL")) {
                    SoundPlayUtils.play(8);
                    ToastUtils.showShort("用户取消");
                } else if (TextUtils.equals(code, "SCAN_PAYMENT")) {
                    SoundPlayUtils.play(4);
                    ToastUtils.showShort("扫码支付");
                } else {
                    SoundPlayUtils.play(4);
                    ToastUtils.showShort(paramMap.get("return_msg").toString());
                }

            }
        });

    }

    private void scanQRCode(String result) {
        loadingDialog.show();
        ArgScanQRCode arg = new ArgScanQRCode();
        arg.setAuth_code(result);
        arg.setCash_id("100112053");
        arg.setClient(1);
        arg.setRemark("刷脸支付");
        arg.setTotal_fee(realPayMoney);
        OkGoUtils.getInstance().postNoGateWay(MainActivity.this, gson.toJson(arg), "/api/pay/barcodepay", new OnResponseListener() {
            @Override
            public void onResponse(String serverRetData) {
                loadingDialog.dismiss();
                try {
                    ResultScanQRCode result = gson.fromJson(serverRetData, ResultScanQRCode.class);
                    doSuceess(ObjectUtils.equals(result.getPay_type(), "1") ? "微信扫码支付" : ObjectUtils.equals(result.getPay_type(), "2") ? "支付宝扫码支付" : "扫码支付");
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    SoundPlayUtils.play(4);
                    ToastUtils.showShort("解析Json字符串失败");
                }
            }

            @Override
            public void onFail(String errMsg) {
                loadingDialog.dismiss();
                SoundPlayUtils.play(4);
                ToastUtils.showShort(errMsg);
            }
        });

    }

    public void doSuceess(String payType) {
        SoundPlayUtils.play(5);
        Constant.OrderNum++;
        DouHaoPrint(carList, payType);
        Intent intent = new Intent(MainActivity.this, SucessActivity.class);
        intent.putExtra("menus", (Serializable) carList);
        intent.putExtra("count", totalCount);
        intent.putExtra("OrderNum", Constant.OrderNum);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.tv_total_count, R.id.bs_bt_gopay, R.id.tv_gocar, R.id.tv_cancle, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                SoundPlayUtils.play(1);
                tvBack.setVisibility(View.GONE);
                tvCancle.setVisibility(View.VISIBLE);
                rlOrder.setVisibility(View.VISIBLE);
                rlCar.setVisibility(View.GONE);
                break;
            case R.id.tv_cancle:
                SoundPlayUtils.play(1);
                if (ObjectUtils.isEmpty(closeConfirmDialog)) {
                    closeConfirmDialog = new CloseConfirmDialog(MainActivity.this);
                    closeConfirmDialog.setOnCloseOrderLitener(new CloseConfirmDialog.OnCloseOrderLitener() {
                        @Override
                        public void close() {
                            finish();
                        }
                    });
                }
                closeConfirmDialog.show();
                break;
            case R.id.bs_bt_gopay:
                SoundPlayUtils.play(7);
                if (SPUtils.getInstance().getBoolean("realMoney", false)) {
                    realPayMoney = totalMoney;
                } else {
                    realPayMoney = "0.01";
                }
                showPayPopWindow();
                break;
            case R.id.tv_total_count:
                break;
            case R.id.tv_gocar:
                if (carList.size() > 0) {
                    SoundPlayUtils.play(1);
                    rlOrder.setVisibility(View.GONE);
                    rlCar.setVisibility(View.VISIBLE);
                    tvBack.setVisibility(View.VISIBLE);
                    tvCancle.setVisibility(View.GONE);
                    updateCar();
                } else {
                    SoundPlayUtils.play(6);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carList = null;
        decimalFormat = new DecimalFormat("0.00");
        bsAdapter = null;
        twoAdapter = null;
        oneAdapter = null;
        totalCount = 0;
        totalMoney = null;
        realPayMoney = null;
        mPhotoPopupWindow = null;
        scanQrCodeDialog = null;
        closeConfirmDialog = null;
        asyncUsbScanner.close();
        mUsbPrinter.close();
        mUsbPrinter = null;

    }


    /*
    private View createBottomSheetView() {
        View bottomSheet = LayoutInflater.from(this).inflate(R.layout.sheet_layout, new BottomSheetLayout(this), false);
        RecyclerView bsRecyclerview = bottomSheet.findViewById(R.id.bs_recyclerview);
        TextView bsTvTotalmoney = bottomSheet.findViewById(R.id.bs_tv_totalmoney);
        TextView bsTvTotalcount = bottomSheet.findViewById(R.id.bs_tv_totalcount);
        Button bsBtGopay = bottomSheet.findViewById(R.id.bs_bt_gopay);
        BsAdapter bsAdapter = new BsAdapter(R.layout.item_bs_car);
        bsAdapter.setNewData(carList);
        bsRecyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        bsRecyclerview.setAdapter(bsAdapter);
        return bottomSheet;
    }
     */

}
