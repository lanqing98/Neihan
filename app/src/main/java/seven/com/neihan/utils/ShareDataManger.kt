package seven.com.neihan.utils

import seven.com.neihan.main.model.ContentTypeBean

/**
 * Created by Seven on 2017/7/31.
 */
class ShareDataManger {

    //contentTypeBean
    var contentTypeBean: ContentTypeBean? = null

    companion object {
        fun get(): ShareDataManger {
            return Inner.data
        }

        private object Inner {
            val data = ShareDataManger()
        }
    }
}