package io.github.kolacbb.library;

import android.os.Message;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class SnackToast implements IToast {

    private ToastHandler mHandler;
    private TopActivityHolder mHolder;
    private ToastParam mToastParam;
    private FrameLayout mRootView;

    public SnackToast(TopActivityHolder holder, ToastHandler handler) {
        mHolder = holder;
        mHandler = handler;
    }

    @Override
    public int getDuration() {
        return mToastParam.duration;
    }

    @Override
    public void show() {
        Message msg = mHandler.obtainMessage();
        msg.what = ToastHandler.SHOW;
        msg.obj = this;
        mHandler.sendMessage(msg);
    }

    @Override
    public void cancel() {
        if (mRootView != null) {
            mRootView.removeView(mToastParam.view);
            mRootView = null;
        }
    }

    @Override
    public void setParam(ToastParam param) {
        mToastParam = param;
    }

    @Override
    public ToastParam getParam() {
        return mToastParam;
    }

    @Override
    public void handleShow() {
        mRootView = mHolder.getActivity().getWindow().getDecorView().findViewById(android.R.id.content);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = mToastParam.gravity | Gravity.CENTER;
        params.topMargin = mToastParam.y;
        params.bottomMargin = mToastParam.y;
        params.leftMargin = mToastParam.x;
        params.rightMargin = mToastParam.x;
        if (mToastParam.view == null) {
            mToastParam.view = ToastUtils.createView(mToastParam.text);
        }
        mRootView.addView(mToastParam.view, params);
    }
}
