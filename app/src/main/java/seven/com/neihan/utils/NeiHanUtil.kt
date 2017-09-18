package seven.com.neihan.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Rect
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import java.security.MessageDigest

/**
 * Created by Seven on 2017/8/17.
 */
object NeiHanUtil {

    /**
     *获取屏幕的宽度
     * */
    fun getScrenWidth(context: Context): Int {
        return  context.resources.displayMetrics.widthPixels
    }

    /**
     *获取屏幕的高度
     * */
    fun getScreenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

    /**
     * 获取状态栏高度
     * */
    fun getStatusHeight(activity: Activity): Int {
        val rectangle = Rect()
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }

    /**
     * 获取状态栏高度
     * */
    fun getStatusBarHeight(context: Context): Int {
        var c: Class<*>?
        var obj: Any?
        var field: java.lang.reflect.Field? = null
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c!!.newInstance()
            field = c.getField("status_bar_height")
            x = Integer.parseInt(field!!.get(obj).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(x)
            return statusBarHeight
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return statusBarHeight
    }

    /**
     * 显示alertDialog
     * */
    fun showAlertDialog(activity: AppCompatActivity, title: String?, message: String?, isSetCancelable: Boolean, cancelAction: (() -> Unit)?, positiveTitle: String? , positiveAction: ((var1: DialogInterface, var2: Int) -> Unit)?) {
        activity.runOnUiThread{
            val builder = AlertDialog.Builder(activity)
            builder.setTitle(title ?: let { "" })
            builder.setMessage(message ?: let { "" })
            builder.setCancelable(isSetCancelable)
            if (cancelAction != null) {
                builder.setOnCancelListener {
                    cancelAction()
                }
            }
            if (positiveTitle != null) {
                builder.setPositiveButton(positiveTitle, DialogInterface.OnClickListener{ dialogInterface, i ->
                    if (positiveAction != null) {
                        positiveAction(dialogInterface, i)
                    }
                })
            }
            builder.create()
            builder.show()
        }
    }

    /**
     * 根据手机分辨率从dp的单位转成px
     * */
    fun getPxFromDp(context: Context, dpValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5).toInt()
    }

    /**
     * 根据手机分辨率从px的单位转成为dp
     * */
    fun getDpFromPx(context: Context, pxValue: Float): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (pxValue/scale + 0.5f).toInt()
    }



}