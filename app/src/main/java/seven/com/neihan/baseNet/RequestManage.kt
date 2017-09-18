package yanglanqing.com.kotlindemo

import android.app.AlertDialog
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import seven.com.neihan.baseNet.RequestMethod
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by seven on 2017/7/12.
 */
class RequestManage() {
    private val client = let {
                                OkHttpClient.Builder()
                                                    .connectTimeout(30, TimeUnit.SECONDS)
                                                    .build() }

    private val gson = Gson()
    private val REQUESTLOGTAG: String = "requestLogTag"

    fun <T> request(url:String, parameters: RequestBody? = null,dataClass: Class<T> , method: RequestMethod = RequestMethod.POST, result:(data: T?, error: String?) -> Unit) {
        when (method){
            RequestMethod.POST -> {
                postRequest(url, parameters, dataClass,result)
            }
            RequestMethod.GET  -> {
                getRequest(url, dataClass, result)
            }
        }
    }

    //暂停
    fun pauseRequests() {

    }

    // 恢复
    fun resumeRequests() {

    }

    // 取消
    fun cancleRequests() {

    }

    //POST
    private fun <T> postRequest(url: String, parameters: RequestBody? = null, dataClass: Class<T>, result: (data: T?, error: String?) -> Unit) {
        val request: Request = Request.Builder()
                .url(url)
                .post(parameters)
                .build()
        Log.d(REQUESTLOGTAG, "数据请求开始")
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                //成功
                val data = gson.fromJson(response?.body()?.charStream(), dataClass)
                result(data, null)
                Log.d(REQUESTLOGTAG, "成功")
                Log.d(REQUESTLOGTAG, url)
                Log.d(REQUESTLOGTAG, response?.header("Server"))
                Log.d(REQUESTLOGTAG, response?.header("Date"))
                Log.d(REQUESTLOGTAG, response?.header("Vary"))
                Log.d(REQUESTLOGTAG, "数据请求结束")
            }

            override fun onFailure(call: Call?, e: IOException?) {
                //失败
                result(null, e?.message.toString())
                Log.d(REQUESTLOGTAG, "失败")
                Log.d(REQUESTLOGTAG, url)
                Log.d(REQUESTLOGTAG, e?.message.toString())
                Log.d(REQUESTLOGTAG, "数据请求结束")
            }
        })
    }

    //GET
    private fun <T> getRequest(url: String, dataClass: Class<T>, result: (data: T?, error: String?) -> Unit) {
        val request: Request = Request.Builder()
                .url(url)
                .get()
                .build()
        Log.d(REQUESTLOGTAG, "数据请求开始")
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call?, response: Response?) {
                val data = gson.fromJson(response?.body()?.charStream(), dataClass)
                result(data, null)
                Log.d(REQUESTLOGTAG, "成功")
                Log.d(REQUESTLOGTAG, url)
                Log.d(REQUESTLOGTAG, "数据请求结束")
            }
            override fun onFailure(call: Call?, e: IOException?) {
                //失败
                result(null, e?.message.toString())
                Log.d(REQUESTLOGTAG, "失败")
                Log.d(REQUESTLOGTAG, url)
                Log.d(REQUESTLOGTAG, e?.message.toString())
                Log.d(REQUESTLOGTAG, "数据请求结束")
            }
        })
    }

}
