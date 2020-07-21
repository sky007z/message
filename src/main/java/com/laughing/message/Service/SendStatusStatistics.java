package com.laughing.message.Service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

// 导入 SMS 模块的 client
import com.tencentcloudapi.sms.v20190711.SmsClient;

// 导入要请求接口对应的 request response 类
import com.tencentcloudapi.sms.v20190711.models.SendStatusStatisticsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendStatusStatisticsResponse;


import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
/**
 * @author Fu zihao
 * @version 1.0
 * @Description: 短信统计信息拉取
 * @date 2020/7/17 14:24
 */


/**
 * Tencent Cloud Sms SendStatusStatistics
 * https://cloud.tencent.com/document/product/382/38774
 */
@Service
public class SendStatusStatistics {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");

    public String getSendStatusStatistics(String appid, long start, long end) throws TencentCloudSDKException {
        Credential cred = new Credential("xxxxxxxxxxxxxxxx", "yyyyyyyyyyyyyyyy");

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setReqMethod("POST");
        httpProfile.setConnTimeout(60);
        httpProfile.setEndpoint("sms.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod("HmacSHA256");
        clientProfile.setHttpProfile(httpProfile);
        SmsClient client = new SmsClient(cred, "", clientProfile);

        SendStatusStatisticsRequest req = new SendStatusStatisticsRequest();
        req.setSmsSdkAppid(appid);
        Long limit = 100L;
        req.setLimit(limit);
        Long offset = 0L;
        req.setOffset(offset);
        req.setStartDateTime(start);
        req.setEndDataTime(end);

        SendStatusStatisticsResponse res = client.SendStatusStatistics(req);

        // 输出 JSON 格式的字符串回包
        System.out.println(SendStatusStatisticsResponse.toJsonString(res));

        return SendStatusStatisticsResponse.toJsonString(res);
    }


}