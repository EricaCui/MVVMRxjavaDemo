package com.example.networkrequest.interceptor;

import android.text.TextUtils;

import com.example.networkrequest.cache.ACache;
import com.example.networkrequest.cache.EnumKey;
import com.example.networkrequest.utils.AppUtils;
import com.example.networkrequest.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 *  头部拦截器
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String token = ACache.get(Utils.getContext()).getAsString(EnumKey.User.USER_TOKEN);
        String flowId = ACache.get(Utils.getContext()).getAsString(EnumKey.User.FLOW_ID);
        String companyId = ACache.get(Utils.getContext()).getAsString(EnumKey.User.COMPANY_ID);
        String  employeeId  = ACache.get(Utils.getContext()).getAsString(EnumKey.User.EMPLOYEE_ID);
        if (TextUtils.isEmpty(token)) {
            token = "";
        }
        if (TextUtils.isEmpty(flowId)) {
            flowId = "";
        }
        if (TextUtils.isEmpty(companyId)) {
            companyId = "";
        }
        if(TextUtils.isEmpty(employeeId)){
            employeeId = "";
        }
        AppUtils.AppInfo appInfo = AppUtils.getAppInfo(Utils.getContext());
        String version = appInfo.getVersionName();
        Request.Builder requestBuilder = original.newBuilder()
                .addHeader("X-SYSTEM-VERSION", "ANDROID"+version)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .method(original.method(), original.body());
        return chain.proceed(requestBuilder.build());

    }

}