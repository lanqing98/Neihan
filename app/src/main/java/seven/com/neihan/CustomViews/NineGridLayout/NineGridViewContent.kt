package seven.com.neihan.CustomViews.NineGridLayout

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import seven.com.neihan.R
import kotlinx.android.synthetic.main.customview_ninegrid_content.view.*
import seven.com.neihan.baseNet.NetEnvironment
import seven.com.neihan.baseNet.NetUtil
import seven.com.neihan.jokePic.model.JokePicPushBean
import seven.com.neihan.utils.GlideApp
import seven.com.neihan.utils.NINE_PICTURE_TRANSITIONNAME
import seven.com.neihan.utils.NeiHanUtil


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
/**
 * Created by Seven on 2017/8/22.
 */
//Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes
class NineGridViewContent(context: Context, attrs: AttributeSet): RelativeLayout(context, attrs) {
    var img: ImageView? = null
    private var gifImg: ImageView? = null
    private var bottomView: TextView? = null
    private var type: NineGridViewType = NineGridViewType.NORMAL_IMG
    private var index: Int = 0
    private var picBean: JokePicPushBean? = null
    private var saveGifIsHidden = false
    private var isGif:Boolean = false
    private var saveBottomIsHidden = false
    // 记录当前img的状态
    private var futureOperation: ImageClickHandle = ImageClickHandle.enlarged
    var imageClick: ((staus: ImageClickHandle) -> Unit)? = null

    init {
        View.inflate(context, R.layout.customview_ninegrid_content, this)
        img = image
        gifImg = gifImagView
        bottomView = textView
        setFrameLayoutOnCLickListener()
    }

    private fun setFrameLayoutOnCLickListener() {
        val frame = frameLayout
        frame.setOnClickListener {
            if (type == NineGridViewType.SINGLE_IMG) {
                if (isGif) {
                    if (saveGifIsHidden) {
                        //已经播放
                        futureOperation = ImageClickHandle.stopPlayGif


                    }else {
                        // 未播放
                        futureOperation = ImageClickHandle.playGif
                    }
                    handleGif(futureOperation)
                }else {
                    // 图片放大
                    futureOperation = ImageClickHandle.enlarged
                    if (imageClick != null) {
                        imageClick!!(futureOperation)
                    }
                }
            }else {
                // 统一放大
                futureOperation = ImageClickHandle.enlarged
                if (imageClick != null) {
                    imageClick!!(futureOperation)
                }
            }
        }
    }

    // 加载图片
    private fun loadingImg(width: Int, height: Int) {
        val netEnvironment = NetUtil.getApnType(context)
        val imgList = if (netEnvironment == NetEnvironment.wifi) picBean?.large else picBean?.little
        val imgBean = imgList?.get(index)
        setGifImgStatus(imgBean, netEnvironment)
        setBottomStatus(imgBean)
        if (type == NineGridViewType.SINGLE_IMG) {
            setSingleImg(imgBean?.url)
        }else {
            setImg(imgBean?.url, width, height)
        }
    }

    private fun handleGif(staus: ImageClickHandle) {
        val bean = picBean?.large?.get(index)
        val width = NeiHanUtil.getScrenWidth(context)
        val height = (width * (bean?.width ?: 0)) / (bean?.height ?: 1)
        if (staus == ImageClickHandle.stopPlayGif) {
            stopGif(bean?.url, width, height)
        }else {
            palayGif(bean?.url, width, height)
        }
    }

    private fun palayGif(url: String?, width: Int, height: Int) {
//        futureOperation = ImageClickHandle.playGif
        saveGifIsHidden = true
        gifImg?.visibility = View.INVISIBLE
        GlideApp.with(context)
                .asGif()
                .load(url)
                .into(img)
    }

    private fun stopGif(url: String?,width: Int, height: Int) {
        gifImg?.visibility = View.VISIBLE
//        futureOperation = ImageClickHandle.stopPlayGif
        saveGifIsHidden = false
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(img)
    }


    private fun setImg(url: String?, width: Int, height: Int) {
        GlideApp
                .with(context)
                .load(url)
                .override(width, height)
                .placeholder(R.mipmap.loading)
                .into(img)
    }

    // 加载过长的图片的时候使用
    private fun setSingleImg(url: String?) {
        GlideApp
                .with(context)
                .asBitmap()
                .load(url)
                .placeholder(R.mipmap.loading)
                .into(img)
    }

    // 设置gif标识是否隐藏或者显示
    private fun setGifImgStatus(imgBean: JokePicPushBean.ImageBean?, netEnvironment: NetEnvironment) {
        if(imgBean?.isGif == true) {
            isGif = true
            if (type == NineGridViewType.SINGLE_IMG && netEnvironment == NetEnvironment.wifi) {
                gifImg?.visibility = View.INVISIBLE
                saveGifIsHidden = true
            }else {
                saveBottomIsHidden = false
                gifImg?.visibility = View.VISIBLE
            }
        }else {
            isGif = false
            saveBottomIsHidden = true
            gifImg?.visibility = View.INVISIBLE
        }
    }

    private fun setBottomStatus(imgBean: JokePicPushBean.ImageBean?) {

        if (type == NineGridViewType.SINGLE_IMG && getIsLongPicture(imgBean)) {
            bottomView?.visibility = View.VISIBLE
            saveBottomIsHidden = false
        }else {
            saveBottomIsHidden = true
            bottomView?.visibility = View.INVISIBLE
        }
    }

    private fun getIsLongPicture(imgBean: JokePicPushBean.ImageBean?): Boolean{
        val showWidth = NeiHanUtil.getScrenWidth(context)
        val width = imgBean?.width ?: 0
        val height = imgBean?.height ?: 0
        val showHeight = (showWidth * height)/ width
        val maxHeight: Int = getSingleImgMaxHeight()
        return showHeight > maxHeight
    }

    // 图片高度超过这个值的胃长图
    private fun getSingleImgMaxHeight(): Int {
        return  (NeiHanUtil.getScreenHeight(context).toFloat() * 1.5f).toInt()
    }

    // 设置参数
    fun setParameters(viewType: NineGridViewType, i: Int, bean: JokePicPushBean?, imgWidth: Int, imgHeight: Int) {
        type = viewType
        index = i
        picBean = bean
        loadingImg(imgWidth, imgHeight)
        img?.transitionName = NINE_PICTURE_TRANSITIONNAME.get(i)
    }

}