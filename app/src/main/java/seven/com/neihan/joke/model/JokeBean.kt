package seven.com.neihan.joke.model

import android.accounts.AuthenticatorDescription
import okhttp3.internal.platform.Platform

/**
 * Created by Seven on 2017/8/2.
 */
data class JokeBean(var message: String?,
                    var data: Data?) {
    data class Data(var has_more: Boolean?,
                    var tip: String?,
                    var has_new_message: Boolean?,
                    var data: List<Data>?) {
        data class Data(var group: Group?,
                        var `type`: Int?,
                        var ad: Ad?,
                        var comments: List<Comment>?) {
            data class Comment(var text: String?,
                               var user_verified: Boolean?,
                               var user_bury: Int?,
                               var user_id: Long?,
                               var bury_count: Int?,
                               var share_url: String?,
                               var id: Long?,
                               var platform: String?,
                               var is_digg: Int?,
                               var user_name: String?,
                               var user_profile_image_url: String?,
                               var status: Int?,
                               var avatar_url: String?,
                               var group_id: Long?)
            data class Group(var text: String?,
                             var neihan_hot_start_time: String?,
                             var id: Long?,
                             var favorite_count: Int?,
                             var go_detail_count: Int?,
                             var user_favorite: Int?,
                             var share_type: Int?,
                             var user: User?,
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
                             var is_neihan_hot: Boolean?,
                             var digg_count: Int?,
                             var has_hot_comments: Int?,
                             var allow_dislike: Boolean?,
                             var user_repin: Int?,
                             var group_id: Long?,
                             var category_id: Int?,
                             var dislike_reason: List<DislikeReason>?) {
                data class User(var user_id: Long?,
                                var name: String?,
                                var followings: Int?,
                                var user_verified: Boolean?,
                                var ugc_count: Int?,
                                var avatar_url: String?,
                                var followers: Int?,
                                var is_following: Boolean?,
                                var is_pro_user: Boolean?)

                data class DislikeReason(var type: Int?,
                                         var title: String?)
            }

            data class Ad(var log_extra: LogExtra?,
                          var open_url: String?,
                          var track_url: String?,
                          var display_info: String?,
                          var web_url: String?,
                          var avatar_name: String?,
                          var id: Long?,
                          var display_image_height: Int?,
                          var display_image_width: Int?,
                          var title: String?,
                          var label: String?,
                          var display_image: String?,
                          var `type`: String?,
                          var is_ad: Int?,
                          var gif_url: String?,
                          var ad_id: Long?,
                          var button_text: String?,
                          var display_type: Int?,
                          var click_delay: Int?,
                          var ab_show_style: Int?,
                          var avatar_url: String?,
                          var filter_words: List<FilterWords>?) {
                data class LogExtra(var rit: Int?,
                                    var ad_price: String?,
                                    var req_id: String?,
                                    var convert_id: Int?)

                data class FilterWords(var id: String?,
                                       var name: String?,
                                       var is_selected: Boolean?)
            }
        }
    }
}