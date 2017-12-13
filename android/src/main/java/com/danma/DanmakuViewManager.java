package com.danma;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.HashMap;
import java.util.Map;

import master.flame.danmaku.danmaku.loader.android.AcFunDanmakuLoader;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by lmy on 2017/12/13.
 */

public class DanmakuViewManager extends SimpleViewManager<DanmakuView> implements LifecycleEventListener {

    private static final String TAG = DanmakuView.class.getSimpleName();
    private static final String COMMAND_RESUME_NAME = "resume";
    private static final int COMMAND_RESUME_ID = 0;

    private static final String COMMAND_PAUSE_NAME = "pause";
    private static final int COMMAND_PAUSE_ID = 1;

    private static final String COMMAND_SHOW_NAME = "show";
    private static final int COMMAND_SHOW_ID = 2;

    private static final String COMMAND_HIDE_NAME = "hide";
    private static final int COMMAND_HIDE_ID = 3;

    private static final String COMMAND_ADDDANMAKU_NAME = "addDanmaku";
    private static final int COMMAND_ADDDANMAKU_ID = 4;

    private static final String UI_NAME = "RCTDanmakuView";

    private DanmakuView mDanmakuView;

    private DanmakuContext mContext;

    private AcFunDanmakuParser mParser;

    @Override
    public String getName() {
        return UI_NAME;
    }

    @Override
    protected DanmakuView createViewInstance(ThemedReactContext reactContext) {
        mDanmakuView = new DanmakuView(reactContext);
        init();
        return mDanmakuView;
    }

    private void init() {
        mContext = DanmakuContext.create();

        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行

        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        mContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 0) //描边的厚度
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f) //弹幕的速度。注意！此值越小，速度越快！值越大，速度越慢。// by phil
                .setScaleTextSize(1.2f) //缩放的值
                //.setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer
//        .setCacheStuffer(new BackgroundCacheStuffer())  // 绘制背景使用BackgroundCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);

        mParser = new AcFunDanmakuParser();
        mDanmakuView.prepare(mParser, mContext);
        //mParser.setTime(100);
        //mDanmakuView.setTime
        //mDanmakuView.showFPS(true);
        mDanmakuView.enableDanmakuDrawingCache(true);
        if (mDanmakuView != null) {
            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                    //Log.d("弹幕文本", "danmakuShown text=" + danmaku.text);
                }

                @Override
                public void prepared() {
                    mDanmakuView.start();
                }
            });
        }
    }
    @ReactProp(name="isOverlap",defaultBoolean = false)
    public void setIsOverlap(DanmakuView mDanmakuView,boolean isOverlap){
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, !isOverlap);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, !isOverlap);
        mContext.setOverlapping(overlappingEnablePair);
    }
    /**
     * 设置弹幕的最大行数
     * @param mDanmakuView
     * @param maxLines
     */
    @ReactProp(name="maxLines",defaultInt = 5)
    public void setMaxLines(DanmakuView mDanmakuView,int maxLines){
        HashMap<Integer, Integer> maxLinesPair = new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, maxLines); // 滚动弹幕最大显示5行
        mContext.setMaximumLines(maxLinesPair);
    }
    /**
     * 设置弹幕速度
     * @param mDanmakuView
     * @param speed
     */
    @ReactProp(name="speed",defaultFloat = 1.2f)
    public void setSpeed(DanmakuView mDanmakuView,float speed){
        mContext.setScrollSpeedFactor(speed);
    }
    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {

        return MapBuilder.of(
                COMMAND_RESUME_NAME, COMMAND_RESUME_ID,
                COMMAND_PAUSE_NAME, COMMAND_PAUSE_ID,
                COMMAND_SHOW_NAME, COMMAND_SHOW_ID,
                COMMAND_HIDE_NAME, COMMAND_HIDE_ID,
                COMMAND_ADDDANMAKU_NAME, COMMAND_ADDDANMAKU_ID);
    }

    @Override
    public void receiveCommand(DanmakuView root, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_RESUME_ID:
                mDanmakuView.resume();
                break;
            case COMMAND_PAUSE_ID:
                mDanmakuView.pause();
                break;
            case COMMAND_SHOW_ID:
                mDanmakuView.show();
                break;
            case COMMAND_HIDE_ID:
                mDanmakuView.hide();
                break;
            case COMMAND_ADDDANMAKU_ID:
                BaseDanmaku danmaku = mContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
//                Log.i(TAG,"已经运行了");
                if (danmaku == null || mDanmakuView == null) {
//                    Log.i(TAG,"在这儿就返回了");
                    return;
                }
                //0字幕
                String text = args.getString(0);
                //1padding
                int padding = args.getInt(1);
                //2优先级
                byte priority = Byte.valueOf(String.valueOf(args.getInt(2)));
                //3是否是直播
                boolean isLive = args.getBoolean(3);
                //4字幕的时间
                int time = args.getInt(4);
                //5字幕大小
                float textSize = 20f * (mParser.getDisplayer().getDensity() - 0.6f);
                int fontSize = args.getInt(5);
                if (fontSize != -1) {
                    textSize = Float.parseFloat(String.valueOf(fontSize));
                }
                //6字幕颜色
                String color = args.getString(6);
                int textColor = Color.BLACK;
                if (color != null && !color.equals("")) {
                    textColor = Color.parseColor(color);
                }
                danmaku.text = text;
                danmaku.padding = padding;//default:5
                danmaku.priority = priority;//default:0  可能会被各种过滤器过滤并隐藏显示
                danmaku.isLive = isLive;//default:true
                danmaku.setTime(mDanmakuView.getCurrentTime() + time);//time:default:2000
                danmaku.textSize = textSize; //文本弹幕字体大小
                danmaku.textColor = textColor; //文本的颜色
                //danmaku.textShadowColor = getRandomColor(); //文本弹幕描边的颜色
                //danmaku.underlineColor = Color.DKGRAY; //文本弹幕下划线的颜色
                //danmaku.borderColor = getRandomColor(); //边框的颜色
                Log.i(TAG, "text:" + text);
                mDanmakuView.addDanmaku(danmaku);
                break;
            default:
                break;


        }
        super.receiveCommand(mDanmakuView, commandId, args);
    }

    @Override
    public void onHostResume() {
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }

    @Override
    public void onHostPause() {
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    public void onHostDestroy() {
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
}
