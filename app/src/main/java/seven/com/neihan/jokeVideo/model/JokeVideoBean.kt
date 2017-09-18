package seven.com.neihan.jokeVideo.model

import seven.com.neihan.joke.model.JokeBean

/**
 * Created by Seven on 2017/9/15.
 */
data class JokeVideoBean(var message: String?, var data: Data?) {
    data class Data(var has_more: Boolean?, var tip: String?, var has_new_message: Boolean?, var data: List<Data>?) {
        data class Data(var group: Group?, var `type`: Int?) {
            data class Group(var video_id: String?,
                             var mp4_url: String?,
                             var text: String?,
                             var share_url: String?,
                             var keywords: String?,
                             var id: Long?,
                             var m3u8_url: String?,
                             var title: String?,
                             var user: JokeVideoBean.Data.Data.Group.User,
                             var is_can_share: Int?,
                             var category_type: Int?,
                             var download_url: String?,
                             var label: Int?,
                             var content: String?,
                             var video_height: Int?,
                             var comment_count: Int?,
                             var cover_image_uri: String?,
                             var id_str: String?,
                             var media_type: Int?,
                             var share_count: Int?,
                             var `type`: Int?,
                             var category_id: Int?,
                             var status: Int?,
                             var has_comments: Int?,
                             var publish_time: String?,
                             var user_bury: Int?,
                             var status_desc: String?,
                             var neihan_hot_start_time: String?,
                             var play_count: Int?,
                             var user_repin: Int?,
                             var quick_comment: Boolean?,
                             var neihan_hot_end_time: String?,
                             var user_digg: Int?,
                             var video_width: Int?,
                             var online_time: Int?,
                             var category_name: String?,
                             var flash_url: String?,
                             var category_visible: Boolean?,
                             var bury_count: Int?,
                             var is_anonymous: Boolean?,
                             var repin_count: Int?,
                             var is_neihan_hot: Boolean?,
                             var uri: String?,
                             var is_public_url: Int?,
                             var has_hot_comments: Int?,
                             var allow_dislike: Boolean?,
                             var cover_image_url: String?,
                             var group_id: Long?,
                             var is_video: Int?,
                             var display_type: Int?,
                             var origin_video: Group_video?,
                             var large_cover: Group_video?,
                             var medium_cover: Group_video?){
                data class User(var name: String?, var avatar_url: String?)
                data class Group_video (var width: Int?, var uri: String?, var height: Int?, var url_list:List<UrlList> ){
                    data class UrlList(var url: String?)
                }
            }
        }
    }
}