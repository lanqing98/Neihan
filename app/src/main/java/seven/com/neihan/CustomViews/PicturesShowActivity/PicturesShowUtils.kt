package seven.com.neihan.CustomViews.PicturesShowActivity

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.ImageViewState
import kotlinx.android.synthetic.main.photo_exhibition_imageview.view.*
import kotlinx.android.synthetic.main.photo_exhibition_subsvaleimage.view.*
import seven.com.neihan.CustomViews.Model.CGFrame
import seven.com.neihan.jokePic.model.JokePicPushBean
import seven.com.neihan.utils.GlideApp
import seven.com.neihan.utils.NeiHanUtil
import java.io.File

/**
 * Created by Seven on 2017/9/6.
 */
object PicturesShowUtils {

    const val ANIMATION_DURATION: Int = 300

    fun downloadImage(activity: Activity, bean: JokePicPushBean.ImageBean?, view: View) {
        val showWidth = NeiHanUtil.getScrenWidth(activity)
        val showHeight = (showWidth * (bean?.height ?: 1))/(bean?.width ?: 1)
        if (bean?.isGif == true) {
            //gif 直接使用正常的图片
            val imageView = view.showGifImageView
            GlideApp.with(activity)
                    .load(bean?.url)
                    .override(showWidth, showHeight)
                    .into(imageView)
        }else {
            // 加载大图图片
            val scaleImageView = view.subScaleImageView
            scaleImageView.minScale = 1.0f
            scaleImageView.maxScale = 30.0f
            val width = bean?.width ?: 0
            val scale = NeiHanUtil.getScrenWidth(activity).toFloat() / width.toFloat()
            GlideApp.with(activity)
                    .asBitmap()
                    .load(bean?.url)
                    .fitCenter()
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap?, transition: Transition<in Bitmap>?) {
                            scaleImageView.setImage(ImageSource.bitmap(resource).tilingDisabled(), ImageViewState(scale, PointF(0f, 0f), 0))
                        }
                    })
        }
    }

    /**
     * 根据file获取Drawable
     * */
    fun getDrawableWithFile(context: Context, file: File?): Drawable {
        return BitmapDrawable(context.resources, getBitmapWithFile(file))
    }

    /**
     * 根据file获取Bitmap
     * */
    fun getBitmapWithFile(file: File?): Bitmap {
        val filePath = file?.absolutePath
        return BitmapFactory.decodeFile(filePath)
    }
}