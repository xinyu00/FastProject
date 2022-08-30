package com.fast.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fast.res.R;
import com.fast.common.base.BaseApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自定义toast工具类
 *
 * @author Administrator
 */
@SuppressLint({"InflateParams", "SoonBlockedPrivateApi"})
public class ToastUtils {

    private static Toast toast;

    private static void proxyToastTn(Toast toast) {
        try {
            Class toastClass = toast.getClass();
            Field tnField = toastClass.getDeclaredField("mTN");
            tnField.setAccessible(true);
            final Object tn = tnField.get(toast);
            final Class tnClass = tn.getClass();
            Timber.i("tn class:" + tnClass.getName(), "ToastUtils");
            Field handlerField = tnClass.getDeclaredField("mHandler");
            handlerField.setAccessible(true);
            handlerField.set(tn, new ProxyTNHandler(tn));
        } catch (Exception e) {
            Timber.e(e.getMessage(), "ToastUtils");
        }
    }

    private static class ProxyTNHandler extends Handler {
        private Object tnObject;
        private Method handleShowMethod;
        private Method handleHideMethod;

        ProxyTNHandler(Object tnObject) {
            this.tnObject = tnObject;
            try {
                this.handleShowMethod = tnObject.getClass().getDeclaredMethod("handleShow", IBinder.class);
                this.handleShowMethod.setAccessible(true);
                this.handleHideMethod = tnObject.getClass().getDeclaredMethod("handleHide");
                this.handleHideMethod.setAccessible(true);
            } catch (Exception e) {
                Timber.e(e.getMessage(), "ToastUtils");
            }
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 0: {
                        //SHOW
                        IBinder token = (IBinder) msg.obj;
                        if (handleShowMethod != null) {
                            Timber.i("tn invoke show", "ToastUtils");
                            handleShowMethod.invoke(tnObject, token);
                        }
                        break;
                    }

                    case 1: {
                        //HIDE
                        if (handleHideMethod != null) {
                            Timber.i("tn invoke hide", "ToastUtils");
                            handleHideMethod.invoke(tnObject);
                        }
                        break;
                    }
                    case 2: {
                        //CANCEL
                        if (handleHideMethod != null) {
                            handleHideMethod.invoke(tnObject);
                        }
                        break;
                    }

                }
                super.handleMessage(msg);
            } catch (Exception e) {
                Timber.e(e.getMessage(), "ToastUtils");
            }
        }
    }

    private static Toast myToast;
    /**
     * 文本ToastTextView
     */
    private static View toastTextView;
    /**
     * 文本ToastTextViewTextView
     */
    private static TextView toastTextTextView;

    public static void hideLoadingToast(Context context) {
        if (myToast != null) {
            myToast.cancel();
        }
    }

    public static void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Context context = BaseApplication.getInstance();
        try {
            if (toastTextView == null)
                toastTextView = LayoutInflater.from(context).inflate(R.layout.toast_text_layout, null);
            if (toastTextTextView == null) {
                toastTextTextView = toastTextView.findViewById(R.id.tv_message);
                toastTextTextView.setGravity(Gravity.CENTER);
            }
            toastTextTextView.setText(msg);
            if (toast != null)
                toast.cancel();
            toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(toastTextView);
            toast.show();
        } catch (Exception e) {
            try {
                myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                    proxyToastTn(myToast);
                }
                myToast.show();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static boolean isHasToastShow() {
        if (myToast == null) {
            return false;
        }
        if (myToast.getView() == null) {
            return false;
        } else {
            return true;
        }
    }

}
