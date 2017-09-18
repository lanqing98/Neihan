package seven.com.neihan.joke.model

import android.os.Build
import seven.com.neihan.baseNet.BASEDOMAINNAME
import seven.com.neihan.baseNet.RequestMethod
import seven.com.neihan.utils.REQUESTSUCCESS
import yanglanqing.com.kotlindemo.RequestManage

/**
 * Created by Seven on 2017/8/2.
 */
class JokeModel {
    private var page: Int = 0
    private val pageSize: Int = 30
    private var list: MutableList<JokeBean.Data.Data> = mutableListOf()

    fun download(isRefresh: Boolean, result: (data: List<JokeBean.Data.Data>, tip: String, error: String?) -> Unit) {
        if (isRefresh) {
            refresh(result)
        }else {
            refresh(result)
        }
    }

    private fun refresh(result: (data: List<JokeBean.Data.Data>, tip: String, error: String?) -> Unit) {
        page = 1
        request(true, result)
    }

    private fun loadmore(result: (data: List<JokeBean.Data.Data>, tip: String, error: String?) -> Unit) {
        page ++
        request(false, result)
    }

    private fun request(isRefresh: Boolean, result: (data: List<JokeBean.Data.Data>, tip: String, error: String?) -> Unit) {
        val version_sdk = Build.VERSION.SDK_INT
        val brand = android.os.Build.BRAND
        val version_type = android.os.Build.MODEL
        val nowTime = System.currentTimeMillis()/1000
        val url: String = BASEDOMAINNAME +
                "stream/mix/v1/?" +
                "mpic=1" +
                "&webp=1" +
                "&essence=1" +
                "&content_type=-102" +
                "&message_cursor=-1" +
                "&am_longitude=110" +
                "&am_latitude=120" +
                "&am_city=%E5%8C%97%E4%BA%AC%E5%B8%82" +
                "&am_loc_time=${nowTime}" +
                "&count=${pageSize}" +
                "&min_time=1489205901" +
                "&screen_width=1450" +
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
        val request = RequestManage()
        request.request(url, dataClass = JokeBean:: class.java, method = RequestMethod.GET, result = { data, error ->
            var e: String? = null
            var t: String = ""
            if (data != null){
                if (data.message == REQUESTSUCCESS) {
                    t = data?.data?.tip ?:let { "" }
                    if (isRefresh) {
                        if (isRefresh) {
                            list.clear()
                        }
                    }
                    if (data?.data?.data != null) {
                        list.addAll((data?.data?.data)!!)
                    }
                }else {
                    e = data.message
                }
            }else {
                e = error
            }
            result(list, t, e)
        })
    }
}