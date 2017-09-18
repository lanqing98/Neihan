package seven.com.neihan.jokeVideo.ui

import android.media.projection.MediaProjectionManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager
import com.volokh.danylo.video_player_manager.meta.MetaData
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper
import kotlinx.android.synthetic.main.fragment_jokevideo_recycler_item.view.*
import seven.com.neihan.R
import seven.com.neihan.jokeVideo.model.JokeVideoBean
import seven.com.neihan.utils.GlideApp

/**
 * Created by Seven on 2017/9/15.
 */
class JokeVideoAdapter(var fragment: JokeVideoFragment): RecyclerView.Adapter<JokeVideoAdapter.VideoViewHolder>() {

    private var mPlayerManager: VideoPlayerManager<MetaData>? = null

    init {
        mPlayerManager = SingleVideoPlayerManager(object : PlayerItemChangeListener{
            override fun onPlayerItemChanged(currentItemMetaData: MetaData?) {

            }
        })
    }

    private var items: List<JokeVideoBean.Data.Data> = listOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_jokevideo_recycler_item, parent, false)
        return VideoViewHolder(view, fragment, mPlayerManager)
    }

    override fun onBindViewHolder(holder: VideoViewHolder?, position: Int) {
        val item = items.get(position)
        holder?.onLoadViewHolder(item)
    }

    class VideoViewHolder(view: View, var fragment: JokeVideoFragment, var playerManager: VideoPlayerManager<MetaData>?): RecyclerView.ViewHolder(view) {
        private val avatar = itemView.video_avatar
        private val name = itemView.video_userName
        private val title = itemView.video_title
        private val video = itemView.video_VideoPlayerView
        private val playTag = itemView.playVideoTag
        private val cover = itemView.video_cover
        private var dataItem: JokeVideoBean.Data.Data? = null
        private var isPlay: Boolean = false

        init {
            video.addMediaPlayerListener(object : MediaPlayerWrapper.MainThreadMediaPlayerListener{
                override fun onBufferingUpdateMainThread(percent: Int) {

                }
                //
                override fun onVideoCompletionMainThread() {
                    isPlay = false
                    playTag.visibility = View.VISIBLE
                    cover.visibility = View.VISIBLE
                }
                // 播放停止
                override fun onVideoStoppedMainThread() {
                    isPlay = false
                    playTag.visibility = View.VISIBLE
                    cover.visibility = View.VISIBLE
                }
                // 开始播放
                override fun onVideoPreparedMainThread() {
                    isPlay = true
                    playTag.visibility = View.INVISIBLE
                    cover.visibility = View.INVISIBLE
                }
                // 错误
                override fun onErrorMainThread(what: Int, extra: Int) {
                }
                // videoSizeChangedMainThread
                override fun onVideoSizeChangedMainThread(width: Int, height: Int) {
                }
            })
            playTag.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    if (isPlay) {
                        playerManager?.stopAnyPlayback()
                    }else {
                        playerManager?.playNewVideo(null,video, dataItem?.group?.mp4_url ?: let { dataItem?.group?.origin_video?.url_list?.first()?.url})
                    }

                }
            })
        }

        fun onLoadViewHolder(item: JokeVideoBean.Data.Data){
            dataItem = item
            GlideApp.with(fragment)
                    .load(item.group?.user?.avatar_url)
                    .circleCrop()
                    .into(avatar)
            name.text = item.group?.user?.name ?: ""
            title.text = item.group?.text ?: let { item.group?.content }
            GlideApp.with(fragment)
                    .load( item.group?.medium_cover?.url_list?.first()?.url  ?: let { item.group?.large_cover?.url_list?.first()?.url })
                    .centerCrop()
                    .into(cover)
        }
    }

    fun updataRecyclerViewAdapter(data: List<JokeVideoBean.Data.Data>) {
        items = data
        notifyDataSetChanged()
    }
}