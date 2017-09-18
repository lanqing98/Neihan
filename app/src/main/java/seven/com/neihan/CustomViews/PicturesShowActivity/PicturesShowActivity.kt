package seven.com.neihan.CustomViews.PicturesShowActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_picturesshow.*
import kotlinx.android.synthetic.main.activity_picturesshow.view.*
import seven.com.neihan.CustomViews.Model.PhotoExhibitionInfo
import seven.com.neihan.R
import kotlinx.android.synthetic.main.photo_exhibition_subsvaleimage.view.*


/**
 * Created by Seven on 2017/8/31.
 */
class PicturesShowActivity : AppCompatActivity(){

    private var mViewPage: ViewPager? = null
    private var mAdapater: ImagePreviewAdapter? = null
    private var pageNumber: TextView? = null
    private var photoExhibitionInfo: PhotoExhibitionInfo? = null
    private var pageViews: List<View> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picturesshow)
        photoExhibitionInfo = intent.getParcelableExtra("pictureInfo")
        getPageViews()
        initView()
     }

    // 传参设置
    companion object {
        fun startPictersShowActivity(startActivity: Activity, picInfo: PhotoExhibitionInfo) {
            val intent = Intent(startActivity, PicturesShowActivity:: class.java)
            intent.putExtra("pictureInfo", picInfo)
            startActivity.startActivity(intent)
            startActivity.overridePendingTransition(0, 0)
        }
    }

    // 初始化界面
    private fun initView() {
        mViewPage = viewPages
        mAdapater = ImagePreviewAdapter(this, pageViews, photoExhibitionInfo)
        mViewPage?.adapter = mAdapater
        mViewPage?.currentItem = photoExhibitionInfo?.openIndex ?: 0
        mViewPage?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                photoExhibitionInfo?.currentIndext = position
                setPageNumber(position)
            }
        })
        setPageNumber(photoExhibitionInfo?.openIndex ?: 0)
    }

    // 对pageViews进行赋值
    private fun getPageViews() {
        if (photoExhibitionInfo?.picterBeanList?.size == 0) return
        var items: MutableList<View> = mutableListOf()
        for (item in photoExhibitionInfo!!.picterBeanList!!) {
            if (item.isGif) {
                val view = layoutInflater.inflate(R.layout.photo_exhibition_imageview, null)
                view.setOnClickListener {
                    finish()
                }
                items.add(view)
            }else {
                val view = layoutInflater.inflate(R.layout.photo_exhibition_subsvaleimage, null)
                val imageView = view.subScaleImageView
                imageView.setOnClickListener {
                    finish()
                }
                items.add(view)
            }
        }
        pageViews = items
    }

    // 设置页码
    private fun setPageNumber(index: Int) {
        pageNumber = pictureActivityTextView
        val denominator = if (photoExhibitionInfo == null) 0 else photoExhibitionInfo!!.picterBeanList!!.size
        var i = index + 1
        pageNumber?.text = "${i}/${denominator}"
    }

    // adapter
    class ImagePreviewAdapter(var activity: PicturesShowActivity, var pages: List<View>, var info: PhotoExhibitionInfo?): PagerAdapter() {

        var isFirstInitlize = true

        override fun getCount(): Int {
            if (info == null) return 0
            return info!!.getChildCount()
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return `object` == view
        }

        /// 初始化一个页卡
        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            val view = pages.get(position)
            val data = info?.getBean(position)
            PicturesShowUtils.downloadImage(activity, data, view)
            container?.addView(view)
            return view
        }

        //
//        override fun setPrimaryItem(container: ViewGroup?, position: Int, `object`: Any?) {
//            if (isFirstInitlize && info?.picterFrameList?.get(position) != null && `object` is View && position == info?.openIndex) {
//                isFirstInitlize = false
//                if (info?.getBean(position)?.isGif == true) {
//
//                }else {
//                    `object`.subScaleImageView.startEnterAnimation(info!!.picterFrameList!!.get(position))
//                }
//            }
//            super.setPrimaryItem(container, position, `object`)
//        }

        override fun finishUpdate(container: ViewGroup?) {
            super.finishUpdate(container)

        }

        // 溢出一个给定为的页面
        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(pages.get(position))
        }
    }

}