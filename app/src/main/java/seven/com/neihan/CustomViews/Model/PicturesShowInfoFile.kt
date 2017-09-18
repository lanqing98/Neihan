package seven.com.neihan.CustomViews.Model

import android.graphics.Rect
import android.os.Parcel
import android.os.Parcelable
import seven.com.neihan.jokePic.model.JokePicPushBean

/**
 * Created by Seven on 2017/9/6.
 */
data class PhotoExhibitionInfo(var picterBeanList: ArrayList<JokePicPushBean.ImageBean>?, var picterFrameList: ArrayList<CGFrame>?, var openIndex: Int, var currentIndext: Int): Parcelable {

    fun getBean(position: Int): JokePicPushBean.ImageBean? {
        return picterBeanList?.get(position)
    }

    fun getFrame(position: Int): CGFrame? {
        return  picterFrameList?.get(position)
    }

    fun getChildCount(): Int {
        return if (picterFrameList != null) picterBeanList!!.size else 0
    }

    // ==
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(JokePicPushBean.ImageBean.CREATOR),
            parcel.createTypedArrayList(CGFrame.CREATOR),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(picterBeanList)
        parcel.writeTypedList(picterFrameList)
        parcel.writeInt(openIndex)
        parcel.writeInt(currentIndext)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoExhibitionInfo> {
        override fun createFromParcel(parcel: Parcel): PhotoExhibitionInfo {
            return PhotoExhibitionInfo(parcel)
        }

        override fun newArray(size: Int): Array<PhotoExhibitionInfo?> {
            return arrayOfNulls(size)
        }
    }

}

data class CGFrame(var x: Int, var y: Int, var width: Int, var height: Int): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(x)
        parcel.writeInt(y)
        parcel.writeInt(width)
        parcel.writeInt(height)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CGFrame> {
        override fun createFromParcel(parcel: Parcel): CGFrame {
            return CGFrame(parcel)
        }

        override fun newArray(size: Int): Array<CGFrame?> {
            return arrayOfNulls(size)
        }
    }

}

data class RatiosInfo(var scaleX: Float, var scaleY:Float)