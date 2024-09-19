package com.example.countnum;

/**
 * @ClassName
 * @Author name
 * @Dates 2024/9/13
 * Description
 */

import android.content.Context;
import android.view.Gravity;

/**


 */
public class UserDialog {
    private static volatile UserDialog mInstance = null;

    private UserDialog() {

    }

    public static UserDialog getInstance() {
        if (mInstance == null) {
            synchronized (UserDialog.class) {
                if (mInstance == null) {
                    mInstance = new UserDialog();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context context, int layout) {
        return new DialogView(context,layout, R.style.CustomDialog, Gravity.CENTER);
    }

    public DialogView initView(Context context,int layout,int gravity) {
        return new DialogView(context,layout, R.style.CustomDialog, gravity);
    }

    /**
     * 显示
     * @param view
     */
    public void show(DialogView view) {
        if (view != null) {
            if (!view.isShowing()) {
                view.show();
            }
        }
    }

    /**
     * 隐藏
     * @param view
     */
    public void hide(DialogView view) {
        if (view != null) {
            if (view.isShowing()) {
                view.dismiss();
            }
        }
    }
}

