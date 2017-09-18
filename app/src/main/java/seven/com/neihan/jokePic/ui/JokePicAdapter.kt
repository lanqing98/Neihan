package seven.com.neihan.jokePic.ui

import android.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import seven.com.neihan.R
import seven.com.neihan.jokePic.model.JokePicBean
import kotlinx.android.synthetic.main.fragment_jokepic_recycler_picitem.view.*
import seven.com.neihan.baseNet.PICDOMAINNAMEP1
import seven.com.neihan.jokePic.model.JokePicPushBean
import seven.com.neihan.utils.GlideApp

/**
 * Created by Seven on 2017/8/4.
 */
class JokePicAdapter(var fragment: JokePicFragment): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 数据源
    private var items: List<JokePicBean.Data.Data> = listOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_jokepic_recycler_picitem, parent, false)
        return  PictureViewHolder(view, fragment)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val data = items.elementAt(position)
        if (holder is PictureViewHolder) {
            holder.loadData(data)
        }else if (holder is PictureViewHolder) {
            holder.loadData(data)
        }
    }

    // viewHolder
    class PictureViewHolder(var view: View, var fragment: JokePicFragment): RecyclerView.ViewHolder(view) {
        fun loadData(bean: JokePicBean.Data.Data?) {
            //头像
            val avatar = view.pic_avatar
            GlideApp.with(fragment).load(bean?.group?.user?.avatar_url).circleCrop().into(avatar)
            //用户名字
            val name = view.pic_userName
            name.text = bean?.group?.user?.name //+ "${bean?.group?.is_gif}"
            //title
            val title = view.pic_title
            title.text = bean?.group?.content
            if (bean?.group?.content == null) {
                title.visibility = View.GONE
            }else {
                title.visibility = View.VISIBLE
            }
            val pic = view.nineGridView
            val list = getJokePicPushBean(bean = bean?.group)
            pic.setData(list)
        }

        private fun getJokePicPushBean(bean: JokePicBean.Data.Data.Group?): JokePicPushBean {
            val type = bean?.media_type
            when (type) {
                1 -> {
                    // 单张正常图片
                    return getSinglePushBean(false, bean)
                }
                2-> {
                    // 单张gif图片
                    return getSinglePushBean(true, bean)
                }
                4 -> {
                    // 多张图片
                    return getMorePushBean(bean)
                }

                else -> {
                    return  JokePicPushBean(listOf(), listOf())
                }
            }
        }

        private fun getSinglePushBean(isGif: Boolean, bean: JokePicBean.Data.Data.Group?): JokePicPushBean {
            val lageImage = getSingleImageBean(true, isGif, bean)
            val littleImage = getSingleImageBean(false, isGif, bean)
            return JokePicPushBean(large = listOf(lageImage), little = listOf(littleImage))
        }

        private fun getSingleImageBean(isLarge: Boolean, isGif: Boolean, bean: JokePicBean.Data.Data.Group?): JokePicPushBean.ImageBean {
            val large = if (isLarge) bean?.large_image else bean?.middle_image
            val url = PICDOMAINNAMEP1 + large?.uri
            var width = bean?.middle_image?.width ?: let { 0 }
            if (width == 0) {
                width = bean?.middle_image?.r_width ?: let { 0 }
            }
            var height = bean?.middle_image?.height ?: let { 0 }
            if (height == 0) {
                height = bean?.middle_image?.r_height ?: let { 0 }
            }
            val largePushBean = JokePicPushBean.ImageBean(url, isGif, width, height,large?.uri)
            return largePushBean
        }

        private fun getMorePushBean(bean: JokePicBean.Data.Data.Group?): JokePicPushBean {
            val large = getMoreImageBean(bean?.large_image_list)
            val little = getMoreImageBean(bean?.thumb_image_list)
            return JokePicPushBean(large,little)
        }

        private fun getMoreImageBean(list: List<JokePicBean.Data.Data.Group.ThumbImageList>?): List<JokePicPushBean.ImageBean> {
            val arr: MutableList<JokePicPushBean.ImageBean> = mutableListOf()
            if (list?.size == 0) return  arr
            for (item: JokePicBean.Data.Data.Group.ThumbImageList in list!!) {
                val url = PICDOMAINNAMEP1 + item.uri
                val width = item.width
                val height = item.height ?: let { 0 }
                val bean = JokePicPushBean.ImageBean(url, item.is_gif ?: let { false }, width, height, item.uri)
                arr.add(bean)
            }
            return arr
        }


    }


    fun updataRecyclerViewAdapter(data: List<JokePicBean.Data.Data>) {
        items = data
        notifyDataSetChanged()
    }
}