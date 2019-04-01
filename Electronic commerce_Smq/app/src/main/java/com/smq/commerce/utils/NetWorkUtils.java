package com.smq.commerce.utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
/**
 * Time:2019/3/29
 * <p>
 * Author:Lenovo
 * <p>
 * Description:
 */
public class NetWorkUtils {
    //判断是否有网络连接
    public static final boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    //判断WiFi 是否可用
    public static final boolean getWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    //判断移动数据是否可用
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    // 判断网络类型
    public static String getNetWorkType(Context context){
        String netWorkState = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isAvailable()){
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                //网络状态为wifi
                netWorkState = "WIFI";
                return netWorkState;
            }
            else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                //网络状态为手机
                //判断手机网络是2g , 3g, 以及4g
                //String subtypeName = networkInfo.getSubtypeName();
                //System.out.println("网络类型: subtypeName:" + subtypeName);
                //这里我根据的是电话管理器中的类型来进行判断,
                //当然你也可以根据subtuypeName来判断,这个是从连接管理器中拿到的
                int type = telephonyManager.getNetworkType();
                System.out.println("网络类型: type:" + type);
                switch (type){
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        netWorkState = "2G";
                        return netWorkState;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        netWorkState = "3G";
                        return netWorkState;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        netWorkState = "4G";
                        return netWorkState;
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    default:
                        netWorkState = "unknown network type";
                        return netWorkState;

                }

            }
        }
        return "";
    }
    public static void getConnectivityManager(final Context context) {
        final Dialog loadingDialog = WeiboDialogUtils.createLoadingDialog(context, "加载中......");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                    Log.i("eee", "onAvailable: =========已获取到网络");
                    loadingDialog.dismiss();
                }

                @Override
                public void onUnavailable() {
                    super.onUnavailable();
                    Log.i("eee", "onUnavailable: =========没有获取到网络");
                    if (!isNetworkConnected(context)) {
                        Toast.makeText(context, "请检查网络", Toast.LENGTH_SHORT).show();
                    }
                    loadingDialog.show();
                }

                @Override
                public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties);
                    Log.i("eee", "onLinkPropertiesChanged: 当为此请求连接的框架所在的网络发生更改时调用LinkProperties");
                }

                @Override
                public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities);
                    Log.i("eee", "onCapabilitiesChanged: ===当为此请求连接的框架所在的网络更改功能但仍满足所述需求时调用");
                }

                @Override
                public void onLosing(Network network, int maxMsToLive) {
                    super.onLosing(network, maxMsToLive);
                    Log.i("eee", "onLosing: ================网络断开中");
                }

                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    loadingDialog.show();
                }
            });
        }
    }
}
