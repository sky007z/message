package com.laughing.message.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.dao.SendUserLog;
import com.laughing.message.dao.WeatherDay;
import com.laughing.message.mapper.SendUserLogMapper;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 20202020/8/3 13:59
 */
@Service
public class SendUserLogService {


    @Autowired
    private SendUserLogMapper sendUserLogMapper;

    /**
     * 写入日志
     * @param sendUserLog
     */
    public void setSendLogs(SendUserLog sendUserLog) {
        sendUserLogMapper.insert(sendUserLog);
    }


    /**
     * 查询日志
     * @param current
     * @param size
     * @param name
     * @return
     */
    public Page<SendUserLog> getSendLogs(int current, int size,String name){

        QueryWrapper<SendUserLog> wrapper = new QueryWrapper<>();
        if (!StringUtil.isBlank(name)) {
            wrapper.like("name", name);
        }

        Page<SendUserLog> logPage = new Page<>(current, size);

        logPage = sendUserLogMapper.selectPage(logPage, wrapper);
        return logPage;

    }
}
