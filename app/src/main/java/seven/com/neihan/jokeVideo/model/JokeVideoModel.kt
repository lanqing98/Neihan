package seven.com.neihan.jokeVideo.model

import android.os.Build
import com.bumptech.glide.RequestManager
import seven.com.neihan.baseNet.RequestMethod
import seven.com.neihan.utils.REQUESTSUCCESS
import yanglanqing.com.kotlindemo.RequestManage

/**
 * Created by Seven on 2017/9/15.
 */
class JokeVideoModel(var screenWidth: Int) {

    private val pageSize: Int = 20
    private var page: Int = 1
    private var list: MutableList<JokeVideoBean.Data.Data> = mutableListOf()


    fun requestDownload(isRefresh: Boolean, result: (List<JokeVideoBean.Data.Data>, String, String?) -> Unit) {
        if (isRefresh) {
            refreshData(result)
        }else {
            loadMore(result)
        }
    }

    private fun refreshData(result: (List<JokeVideoBean.Data.Data>, String, String?) -> Unit){
        page = 1
        download(true, result)
    }

    private fun loadMore(result: (List<JokeVideoBean.Data.Data>, String, String?) -> Unit) {
        page ++
        download(false, result)
    }

    private fun download(isRefresh: Boolean, result: (List<JokeVideoBean.Data.Data>, String, String?) -> Unit) {
        val version_sdk = Build.VERSION.SDK_INT
        val brand = android.os.Build.BRAND
        val version_type = android.os.Build.MODEL
        val nowTime = System.currentTimeMillis()/1000
        val url = "http://iu.snssdk.com/neihan/stream/mix/v1/?" +
                "mpic=1&" +
                "webp=1&" +
                "essence=1&" +
                "content_type=-104&" +
                "message_cursor=-1&" +
                "am_longitude=110&" +
                "am_latitude=120&" +
                "am_city=%E5%8C%97%E4%BA%AC%E5%B8%82&" +
                "am_loc_time=${nowTime}&" +
                "min_time=1489143837&" +
                "screen_width=${screenWidth}&" +
                "do00le_col_mode=0&" +
                "iid=3216590132&" +
                "device_id=32613520945&ac=wifi&" +
                "channel=360&" +
                "aid=7&" +
                "app_name=joke_essay&" +
                "version_code=612&" +
                "version_name=6.1.2&" +
                "device_platform=android&" +
                "ssmix=a&" +
                "device_type=${version_type}&" +
                "device_brand=${brand}&" +
                "os_api=${version_sdk}&" +
                "os_version=6.10.1&" +
                "uuid=326135942187625&" +
                "openudid=3dg6s95rhg2a3dg5&" +
                "manifest_version_code=612&" +
                "resolution=1450*2800&" +
                "dpi=620&" +
                "update_version_code=6120"
        var manager = RequestManage()
        manager.request(url = url, dataClass = JokeVideoBean:: class.java, method = RequestMethod.GET, result = {data, error ->
            if (data != null) {
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
            }
        })
    }
}