package seven.com.neihan.main.model

import android.content.BroadcastReceiver
import android.os.Build
import seven.com.neihan.baseNet.RequestMethod
import seven.com.neihan.baseClass.BaseActivity
import seven.com.neihan.utils.REQUESTSUCCESS
import seven.com.neihan.utils.ShareDataManger
import seven.com.neihan.utils.NeiHanUtil
import yanglanqing.com.kotlindemo.RequestManage

/**
 * Created by Seven on 2017/7/28.
 */
class ContentTypeModel {
    fun onRequestContentType(aty: BaseActivity, successResult: () -> Unit){
        val request: RequestManage = RequestManage()
        val version_sdk = Build.VERSION.SDK_INT
        val version_brand = android.os.Build.BRAND
        var version_type = android.os.Build.MODEL
        val url: String = "http://lf.snssdk.com/neihan/service/tabs/?" +
                "essence=1" +
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
                "&device_brand=${version_brand}" +
                "&os_api=${version_sdk}" +
                "&os_version=6.10.1" +
                "&uuid=326135942187625" +
                "&openudid=3dg6s95rhg2a3dg5" +
                "&manifest_version_code=612" +
                "&resolution=1450*2800" +
                "&dpi=620" +
                "&update_version_code=6120"
        request.request(url = url, dataClass = ContentTypeBean:: class.java, method = RequestMethod.GET, result = { data, error ->
             if (data != null) {
                 val model = data as ContentTypeBean
                 if (model?.message == REQUESTSUCCESS) {
                     val model = data as ContentTypeBean
                     ShareDataManger.get().contentTypeBean = model.copy()
                 }else {
                     NeiHanUtil.showAlertDialog(aty,title = "提示", message = error, isSetCancelable = true, cancelAction = null, positiveTitle = "确认", positiveAction = { var1, var2 ->
                         var1.dismiss()
                     } )
                 }
             }else {
                 NeiHanUtil.showAlertDialog(aty,title = "提示", message = error, isSetCancelable = true, cancelAction = null, positiveTitle = "确认", positiveAction = { var1, var2 ->
                     var1.dismiss()
                 } )
             }
        })
    }

    abstract class MainRequestReceiver: BroadcastReceiver() {

    }
}



