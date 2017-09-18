package seven.com.neihan.jokePic.model

import android.os.Build
import seven.com.neihan.baseNet.BASEDOMAINNAME
import seven.com.neihan.baseNet.RequestMethod
import seven.com.neihan.utils.REQUESTSUCCESS
import yanglanqing.com.kotlindemo.RequestManage

/**
 * Created by Seven on 2017/8/4.
 */
class JokePicViewModel(var screenWidth: Int) {
    private var page: Int = 1
    private var pageSize: Int = 30
    private var list: MutableList<JokePicBean.Data.Data> = mutableListOf()

    fun download(isRefresh: Boolean, result: (data: List<JokePicBean.Data.Data>, tip: String, error: String?) -> Unit) {
        if (isRefresh) {
            refreshViewHolder(result)
        }else {
            loadMoreViewHolder(result)
        }
    }

    private fun refreshViewHolder(result: (List<JokePicBean.Data.Data>, String, String?) -> Unit) {
        page = 1
        request(true, result)
    }

    private fun loadMoreViewHolder(result: (List<JokePicBean.Data.Data>, String, String?) -> Unit) {
        page ++
        request(false, result)

    }

    private fun request(isRefresh: Boolean, result: (List<JokePicBean.Data.Data>, String, String?) -> Unit) {
        val version_sdk = Build.VERSION.SDK_INT
        val brand = android.os.Build.BRAND
        val version_type = android.os.Build.MODEL
        val nowTime = System.currentTimeMillis()/1000
        val url: String = BASEDOMAINNAME +
                "stream/mix/v1/?mpic=1&webp=1" +
                "&essence=1" +
                "&content_type=-103" +
                "&message_cursor=-1" +
                "&am_longitude=110" +
                "&am_latitude=120" +
                "&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82" +
                "&am_loc_time=${nowTime}" +
                "&count=${pageSize}" +
                "&min_time=1489226061" +
                "&screen_width=${screenWidth}" +
                "&do00le_col_mode=0" +
                "&iid=3216590132" +
                "&device_id=32613520945" +
                "&ac=wifi" +
                "&channel=360" +
                "&aid=7" +
                "&app_name=joke_essay" +
                "&version_code=612" +
                "&version_name=6.1.2" +
                "&device_platform=android" +
                "&ssmix=a" +
                "&device_type=${version_type}" +
                "&device_brand=${brand}" +
                "&os_api=${version_sdk}" +
                "&os_version=6.10.1" +
                "&uuid=326135942187625" +
                "&openudid=3dg6s95rhg2a3dg5" +
                "&manifest_version_code=612" +
                "&resolution=1450*2800" +
                "&dpi=620" +
                "&update_version_code=6120"
        val http = RequestManage()
        http.request(url, null, JokePicBean:: class.java, RequestMethod.GET,result = { data, error ->
            var e: String? = null
            var t: String = ""
                if (data != null) {
                    if (data.message == REQUESTSUCCESS) {
                        // 成功
                        if (isRefresh) {
                            list.clear()
                        }
                        if (data?.data?.data != null) {
                            list.addAll((data?.data?.data)!!)
                        }
                        t = data?.data?.tip ?: ""
                        result(list, t, e)
                    } else {
                        e = data.message
                        result(list, t, e)
                    }
                }else {
                    //请求失败
                    e = error
                    result(list, t, e)
                }
        })
    }
}