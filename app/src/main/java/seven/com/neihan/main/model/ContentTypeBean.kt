package seven.com.neihan.main.model

/**
 * Created by Seven on 2017/8/1.
 */
data class ContentTypeBean(var message: String?, var data: List<ContentTypeList>?) {
    data class ContentTypeList(var double_col_mode: Boolean?,
                               var umeng_event: String?,
                               var is_default_tab: Boolean?,
                               var url: String?,
                               var list_id: Int?,
                               var refresh_interval: Int?,
                               var type: Int?,
                               var name: String?)
}