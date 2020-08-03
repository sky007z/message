package com.laughing.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.dao.SendUserLog;
import com.laughing.message.service.SendUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 20202020/8/3 14:18
 */
@RestController
@RequestMapping("/log")
public class SendUserLogController {
    @Autowired
    private SendUserLogService sendUserLogService;

    @GetMapping("/view/{current}/{size}")
    public Page<SendUserLog> getLogs(@PathVariable("current") int current,
                                     @PathVariable("size") int size,
                                     @RequestParam(required = false, value = "name") String name) {
        return sendUserLogService.getSendLogs(current, size, name);
    }
}
