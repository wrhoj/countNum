package com.example.countnum;

/**
 * @ClassName
 * @Author name
 * @Dates 2024/9/13
 * Description
 */

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**

 */
public class DialogView extends Dialog {

    public DialogView(@NonNull Context context, int layout, int style, int gravity) {
        super(context, style);
        setContentView(layout);
        Window mWindow = getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置背景颜色为透明度为0
        params.dimAmount = 0.5f;
        //设置位置在中间
        params.x = 50;
        params.y = 50;
        params.gravity = gravity;
        mWindow.setAttributes(params);
    }
}

