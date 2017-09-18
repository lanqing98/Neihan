package seven.com.neihan.baseNet

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.telephony.TelephonyManager

/**
 * Created by Seven on 2017/8/25.
 */
object NetUtil {

      @RequiresApi(Build.VERSION_CODES.CUPCAKE)
      fun getApnType(context: Context): NetEnvironment {

          fun getNetType(id: Int): NetEnvironment {
              when (id) {
                  0 -> return NetEnvironment.notNetWork
                  1 -> return NetEnvironment.wifi
                  2 -> return NetEnvironment.g4
                  3 -> return NetEnvironment.g3
                  4 -> return NetEnvironment.g2
                  else -> return NetEnvironment.notNetWork
              }
          }
          var netType = 0
          val manager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
          val networkInfo = manager.activeNetworkInfo
          if (networkInfo == null) {
              return getNetType(netType)
          }
          val nType = networkInfo.type
          if (nType == ConnectivityManager.TYPE_WIFI) {
              netType = 1
          } else if (nType == ConnectivityManager.TYPE_MOBILE) {
              val nSubType = networkInfo.subtype
              val telephonyManager:TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
              if (nSubType == TelephonyManager.NETWORK_TYPE_LTE && !telephonyManager.isNetworkRoaming) {
                netType = 4
              } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0 && !telephonyManager.isNetworkRoaming) {
                  netType = 3
              }else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS || nSubType == TelephonyManager.NETWORK_TYPE_EDGE || nSubType == TelephonyManager.NETWORK_TYPE_CDMA && !telephonyManager.isNetworkRoaming) {
                  netType = 2
              }else {
                  netType = 2
              }
          }
          return  getNetType(netType)
      }


}