package seven.com.neihan.CustomViews.NineGridLayout

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.google.android.flexbox.FlexboxLayout
import seven.com.neihan.R
import kotlinx.android.synthetic.main.customview_ninegrid.view.*
import seven.com.neihan.CustomViews.Model.CGFrame
import seven.com.neihan.CustomViews.Model.PhotoExhibitionInfo
import seven.com.neihan.CustomViews.PicturesShowActivity.PicturesShowActivity
import seven.com.neihan.baseNet.NetEnvironment
import seven.com.neihan.baseNet.NetUtil
import seven.com.neihan.jokePic.model.JokePicPushBean
import seven.com.neihan.utils.GlideApp
import seven.com.neihan.utils.NeiHanUtil
import kotlin.collections.ArrayList

/**
 * Created by Seven on 2017/8/21.
 */
class  NineGridView (context: Context, val attrs: AttributeSet): FlexboxLayout(context, attrs) {

    private var viewType: NineGridViewType = NineGridViewType.NORMAL_IMG
    private var contentView1: NineGridViewContent? = null
    private var contentView2: NineGridViewContent? = null
    private var contentView3: NineGridViewContent? = null
    private var contentView4: NineGridViewContent? = null
    private var contentView5: NineGridViewContent? = null
    private var contentView6: NineGridViewContent? = null
    private var contentView7: NineGridViewContent? = null
    private var contentView8: NineGridViewContent? = null
    private var contentView9: NineGridViewContent? = null
    private var margint: Int = 0
    private var picBean: JokePicPushBean? = null
    private var contentList: MutableList<NineGridViewContent> = mutableListOf()

    init {
        initView()
    }

    private fun initView() {
        View.inflate(context, R.layout.customview_ninegrid,this)
        contentView1 = findViewById(R.id.content1)
        contentView2 = findViewById(R.id.content2)
        contentView3 = findViewById(R.id.content3)
        contentView4 = findViewById(R.id.content4)
        contentView5 = findViewById(R.id.content5)
        contentView6 = findViewById(R.id.content6)
        contentView7 = findViewById(R.id.content7)
        contentView8 = findViewById(R.id.content8)
        contentView9 = findViewById(R.id.content9)
        setContentList()
        setContentViewTag()
        setContentViewCallBack()
    }

    // 将所有的contentView放在list中
    private fun setContentList() {
        contentList.add(content1!!)
        contentList.add(content2!!)
        contentList.add(content3!!)
        contentList.add(content4!!)
        contentList.add(content5!!)
        contentList.add(content6!!)
        contentList.add(content7!!)
        contentList.add(content8!!)
        contentList.add(content9!!)
    }

    // 设置contentViewTag
    private fun setContentViewTag() {
        for (item in contentList) {
            item.tag = contentList.indexOf(item)
        }
    }

    // 设置点击图片的回调
    private fun setContentViewCallBack() {
        for (item in contentList) {
            setCallBack(item)
        }
    }

    private fun setCallBack(view: NineGridViewContent?) {
        val little = picBean?.little?.get(view?.tag as Int)
        val larger = picBean?.large?.get(view?.tag as Int)
        view?.imageClick = {staus ->
            if (staus == ImageClickHandle.enlarged) {
                startPictureActivity(view)
            }
        }
    }

    // 点击image的界面跳转
    private fun startPictureActivity (view: NineGridViewContent?) {
        val tag: Int = (view?.tag ?: 0) as Int
        var arrlist = arrayListOf<JokePicPushBean.ImageBean>()
        if (picBean?.large == null) return
        for (item in picBean!!.large!!) {
            arrlist.add(item)
        }
        val info = PhotoExhibitionInfo(picterBeanList = arrlist, picterFrameList = getContenViewsRects(picBean!!.large!!.size), openIndex = tag, currentIndext = tag)
        PicturesShowActivity.startPictersShowActivity(context as Activity, info)
    }

    // 获取每个图片相对于activity的坐标
    private fun getContenViewsRects(maxIndex: Int): ArrayList<CGFrame>? {
        val viewFrame= arrayListOf<CGFrame>()
        var i = 0
        for (item in contentList) {
            if (i < maxIndex) {
                val itemRect = Rect()
                item.getGlobalVisibleRect(itemRect)
                val frame = CGFrame(itemRect.left, itemRect.top, itemRect.width(), itemRect.height())
                viewFrame.add(frame)
            }
            i ++
        }
        return viewFrame
    }

    //
    private fun  setImagesData(bean: JokePicPushBean) {
        when(bean.large?.size) {
            1 -> {
                viewType = NineGridViewType.SINGLE_IMG
            }
            4 -> {
                viewType = NineGridViewType.FOUR_IMG
            }
            else -> {
                viewType = NineGridViewType.NORMAL_IMG
            }
        }
        onReload(bean)
    }

    private fun  onReload(bean: JokePicPushBean) {
        // 获取设置的间隔
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridView)
        margint = typedArray.getInteger(R.styleable.NineGridView_spadd, 8)
        if (viewType == NineGridViewType.SINGLE_IMG) {
            //单张图片
            //当前的网络环境
            val netEnvironment = NetUtil.getApnType(context)
            val imageBeanList = if (netEnvironment == NetEnvironment.wifi) bean.large else bean.little
            val imageBean = imageBeanList?.first()
            val showWidth = NeiHanUtil.getScrenWidth(context)
            val width = imageBean?.width ?: 0
            val height = imageBean?.height ?: 0
            val showHeight = (showWidth * height)/ width
            margint = 0
            val maxHeight: Int = getSingleImgMaxHeight()
            // 根据高度判断是不是长图(没有细查api不能根据字段进行判断是不是长图)
            if (showHeight > maxHeight) {
                val nowHeight = getSingleImgShowHeight()
                setContentViewSize(showWidth, nowHeight)
            }else {
                setContentViewSize(showWidth, showHeight)
            }
        }else if (viewType == NineGridViewType.FOUR_IMG) {
            // 4张图片
            val screenWidth = NeiHanUtil.getScrenWidth(context)
            val space = margint * 4
            val showWidth =  (screenWidth - screenWidth/4 - space)/2
            setContentViewSize(showWidth, showWidth)
        }else {
            // 2 3 5 6 7 8 9 张图片
            val space = margint * 6
            val showWidth = (NeiHanUtil.getScrenWidth(context) - space )/3
            setContentViewSize(showWidth, showWidth)
        }
    }

    private fun setContentViewSize(width: Int, height: Int) {
        val imageCount = picBean?.large?.size ?: 0
        var i = 0
        while (i < 9) {
            val view = contentList.get(i)
            if (i < imageCount) {
                view.setParameters(viewType, i, picBean, width, height)
                setThisLayoutParamsWithMargins(margint, margint, margint, margint, width, height, view)
            }else {
                setContentViewParams(view,0, 0)
            }
            i ++
        }
    }

    // 设置contentView的宽高
    private fun setContentViewParams(view: NineGridViewContent?, width: Int, height: Int) {
        val parmas = view?.layoutParams
        parmas?.width = width
        parmas?.height = height
    }

    // 设置contentView的外边距和宽高
    private fun setThisLayoutParamsWithMargins(top: Int, left: Int, bottom: Int, right: Int, width: Int, height: Int, view: NineGridViewContent?){
        val lp = FlexboxLayout.LayoutParams(FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(top, left, bottom, right)
        lp.width = width
        lp.height = height
        view?.layoutParams = lp
    }

    // 图片高度超过这个值的胃长图
    private fun getSingleImgMaxHeight(): Int {
        return  (NeiHanUtil.getScreenHeight(context).toFloat() * 1.5f).toInt()
    }

    // 当图片是长图的时候展示的高度
    private fun getSingleImgShowHeight(): Int {
        return (NeiHanUtil.getScrenWidth(context).toFloat() * 1.2f).toInt()
    }

    // 设置data
    fun  setData(bean:JokePicPushBean) {
        picBean = bean
        setImagesData(bean)
    }

}