package com.laughing.message.controller;

import com.laughing.message.Service.SendStatusStatistics;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/17 14:52
 */
@RestController
@Api(tags = "短信统计接口")
@RequestMapping("/msg")
public class SendMsgController {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
    @Autowired
    public SendStatusStatistics sendStatusStatistics;

    @ApiOperation("短信统计")
    @GetMapping("/getSend")
    public String countSend() throws TencentCloudSDKException {
        long start = 2020071000;  // 创建时间
        long end = Long.parseLong(dateFormat.format(new Date()));
        return sendStatusStatistics.getSendStatusStatistics("1400398100", start, end);
    }

    @ApiOperation("短信统计今日发送量")
    @GetMapping("/getSend/today")
    public String countTodaySend() throws TencentCloudSDKException {
        long start = Long.parseLong(dateFormat.format(new Date()).substring(0, 8) + "00");
        long end = Long.parseLong(dateFormat.format(new Date()));
        return sendStatusStatistics.getSendStatusStatistics("1400398100", start, end);
    }
}
