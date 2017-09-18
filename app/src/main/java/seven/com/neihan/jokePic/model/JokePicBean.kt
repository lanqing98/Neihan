package seven.com.neihan.jokePic.model

import seven.com.neihan.joke.model.JokeBean

/**
 * Created by Seven on 2017/8/4.
 */
data class JokePicBean(var message: String?,
                       var data: Data?) {
    data class Data(var has_more: Boolean?,
                    var tip: String?,
                    var has_new_message: Boolean?,
                    var data: List<Data>?) {
        data class Data(var group: Group?,
                        var `type`: Int?,
                        var ad: Ad?,
                        var comments: List<JokeBean.Data.Data.Comment>?) {
            data class Group(var user: User?,
                             var text: String?,
                             var neihan_hot_start_time: String?,
                             var id: Long?,
                             var favorite_count: Int?,
                             var go_detail_count: Int?,
                             var user_favorite: Int?,
                             var share_type: Int?,
                             var max_screen_width_percent: Double?,
                             var is_can_share: Int?,
                             var category_type: Int?,
                             var share_url: String?,
                             var label: Int?,
                             var content: String?,
                             var comment_count: Int?,
                             var id_str: String?,
                             var media_type: Int?,
                             var share_count: Int?,
                             var `type`: Int?,
                             var status: Int?,
                             var has_comments: Int?,
                             var large_image: MiddleImage?,
                             var user_bury: Int?,
                             var status_desc: String?,
                             var quick_comment: Boolean?,
                             var display_type: Int?,
                             var neihan_hot_end_time: String?,
                             var is_personal_show: Boolean?,
                             var user_digg: Int?,
                             var category_name: String?,
                             var category_visible: Boolean?,
                             var bury_count: Int?,
                             var is_anonymous: Boolean?,
                             var repin_count: Int?,
                             var min_screen_width_percent: Double?,
                             var is_neihan_hot: Boolean?,
                             var digg_count: Int?,
                             var has_hot_comments: Int?,
                             var allow_dislike: Boolean?,
                             var image_status: Int?,
                             var user_repin: Int?,
                             var group_id: Long?,
                             var middle_image: MiddleImage?,
                             var category_id: Int?,
                             var is_gif: Int?,
                             var dislike_reason: List<DislikeReason>?,
                             var thumb_image_list: List<ThumbImageList>?,
                             var large_image_list: List<ThumbImageList>?){
                data class ThumbImageList(var uri: String?,
                                          var height: Int?,
                                          var width: Int?,
                                          var is_gif: Boolean?)
                data class User(var user_id: Long?,
                                var name: String?,
                                var followings: Int?,
                                var user_verified: Boolean?,
                                var ugc_count: Int?,
                                var avatar_url: String?,
                                var followers: Int?,
                                var is_following: Boolean?,
                                var is_pro_user: Boolean?)

                data class LargeImage(var width: Int?,
                                      var r_height: Int?,
                                      var r_width: Int?,
                                      var uri: String?,
                                      var height: Int?,
                                      var url_list: List<UrlList>?) {
                    data class UrlList(var url: String?)
                }

                data class MiddleImage(var width: Int?,
                                       var r_height: Int?,
                                       var r_width: Int?,
                                       var uri: String?,
                                       var height: Int?,
                                       var url_list: List<UrlList>?) {
                    data class UrlList(var url: String?)
                }

                data class DislikeReason(var `type`: Int?,
                                         var title: String?)
            }

            data class Ad(var log_extra: LogExtra?,
                          var open_url: String?,
                          var track_url: String?,
                          var display_info: String?,
                          var web_url: String?,
                          var id: Long?,
                          var display_image_height: Int?,
                          var display_image_width: Int?,
                          var title: String?,
                          var download_url: String?,
                          var label: String?,
                          var source: Any?,
                          var display_image: String?,
                          var avatar_name: String?,
                          var `type`: String?,
                          var is_ad: Int?,
                          var gif_url: String?,
                          var ad_id: Long?,
                          var button_text: String?,
                          var display_type: Int?,
                          var video_play_in_detail: Boolean?,
                          var click_delay: Int?,
                          var ab_show_style: Int?,
                          var `package`: String?,
                          var hide_if_exists: Int?,
                          var video_info: VideoInfo?,
                          var avatar_url: String?,
                          var ipa_url: String?,
                          var video_auto_play: Int?,
                          var filter_words: List<FilterWords>?) {
                data class LogExtra(var rit: Int?,
                                    var ad_price: String?,
                                    var req_id: String?,
                                    var convert_id: Int?)

                data class VideoInfo(var video_url: String?,
                                     var backup_url: String?,
                                     var cover_url: String?,
                                     var height: Int?,
                                     var width_height_ratio: Double?,
                                     var width: Int?,
                                     var video_duration: Int?)

                data class FilterWords(var id: String?,
                                       var name: String?,
                                       var is_selected: Boolean?)
            }
        }
    }
}