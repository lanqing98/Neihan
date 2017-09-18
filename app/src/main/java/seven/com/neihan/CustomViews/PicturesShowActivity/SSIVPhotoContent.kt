package seven.com.neihan.CustomViews.PicturesShowActivity

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import seven.com.neihan.CustomViews.Model.CGFrame
import seven.com.neihan.utils.NeiHanUtil

/**
 * Created by Seven on 2017/9/8.
 */
class SSIVPhotoContent(context: Context, attributeSet: AttributeSet): SubsamplingScaleImageView(context, attributeSet) {

    private var startAnimation: Boolean = false
    private var smallFrame: CGFrame = CGFrame(0, 0, 0, 0)
    private var selfFram: CGFrame = CGFrame(0, 0,0,0)

    fun startEnterAnimation(frame: CGFrame) {
        startAnimation = true
        smallFrame = frame
        smallFrame.y = smallFrame.y - NeiHanUtil.getStatusBarHeight(context)
        selfFram.x = 0
        selfFram.y = NeiHanUtil.getStatusBarHeight(context)
        selfFram.width = NeiHanUtil.getScrenWidth(context)
        selfFram.height = NeiHanUtil.getScreenHeight(context) - NeiHanUtil.getStatusBarHeight(context)
//        val scale = smallFrame.width.toFloat() / selfFram.width.toFloat()
//        val cententX = selfFram.x.toFloat() + selfFram.width / 2
//        val cententY = selfFram.y.toFloat() + selfFram.height / 2
//        setScaleAndCenter(scale, PointF(cententX, cententY))
    }

    override fun onReady() {
        super.onReady()
        if (startAnimation) {

        }
    }
}