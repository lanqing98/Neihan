package seven.com.neihan.jokePic.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Seven on 2017/8/23.
 */

data class JokePicPushBean(var large: List<ImageBean>?,
                           var little: List<ImageBean>?) {
    data class ImageBean(var url: String?,
                         var isGif: Boolean = false,
                         var width: Int?,
                         var height: Int?,
                         var uri: String?) : Parcelable{
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readByte() != 0.toByte(),
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readValue(Int::class.java.classLoader) as? Int,
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(url)
            parcel.writeByte(if (isGif) 1 else 0)
            parcel.writeValue(width)
            parcel.writeValue(height)
            parcel.writeString(uri)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ImageBean> {
            override fun createFromParcel(parcel: Parcel): ImageBean {
                return ImageBean(parcel)
            }

            override fun newArray(size: Int): Array<ImageBean?> {
                return arrayOfNulls(size)
            }
        }

    }
}

