package com.laughing.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.service.DealService;
import com.laughing.message.service.PhoneService;
import com.laughing.message.service.SendSmsService;
import com.laughing.message.dao.Deal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: 提醒短信控制层
 * @date 2020/7/20 12:05
 */
@RestController
@Slf4j
@Api(tags = "提醒短信接口")
@PropertySource("classpath:/task-config.properties")
@RequestMapping("/deal")
public class DealController {
    @Autowired
    private SendSmsService sendSms;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private DealService dealService;
    @Autowired
    public Deal deal;
    private static final SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat Format2 = new SimpleDateFormat("MM-dd");

    @ApiOperation("添加提醒短信")
    @PostMapping("/add")
    public int addDeal(@RequestParam(value = "userId") int userId,
                       @RequestParam(value = "msg") String msg,
                       @RequestParam(value = "msgFlag") String msgFlag,
                       @RequestParam(value = "ning") String ning,
                       @RequestParam(value = "msgDate", required = false) String msgDate,
                       @RequestParam(value = "weekDays", required = false) String weekDays,
                       @RequestParam(value = "monthDays", required = false) String monthDays
    ) throws ParseException {


        deal.setUserId(userId);
        deal.setName(phoneService.getUserById(userId));
        deal.setMsg(msg);
        deal.setMsgFlag(msgFlag);
        deal.setNing(ning);
        if (!StringUtil.isBlank(weekDays) && "3".equals(msgFlag)) {
            deal.setWeekDays(weekDays);
            deal.setMsgDate(null);
            deal.setMonthDays(null);
        }
        if (!StringUtil.isBlank(monthDays) && "2".equals(msgFlag)) {
            deal.setMonthDays(monthDays);
            deal.setMsgDate(null);
            deal.setWeekDays(null);
        }
        if (!StringUtil.isBlank(msgDate) && "1".equals(msgFlag)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            Date date = simpleDateFormat.parse(msgDate);
            deal.setMsgDate(date);
            deal.setMonthDays(null);
            deal.setWeekDays(null);
        }
        deal.setCreatTime(new Date());
        return dealService.addDeal(deal);

    }


    @ApiOperation("查询待办短信")
    @GetMapping("/get/{current}/{size}")
    public Page<Deal> selectDeal(@PathVariable("current") int current,
                                 @PathVariable("size") int size,
                                 @RequestParam(required = false, value = "name") String name) {
        return dealService.getDeal(current, size, name);
    }

    @ApiOperation("修改待办短信")
    @PostMapping("/update")
    public int updateDeal(@RequestParam(value = "id") int id,
                          @RequestParam(value = "msg") String msg,
                          @RequestParam(value = "msgDate") String msgDate
    ) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        Date date = simpleDateFormat.parse(msgDate);
        return dealService.updateDeal(id, msg, date);
    }

    @ApiOperation("删除待办短信")
    @DeleteMapping("/delete/{id}")
    public int deleteDeal(@PathVariable("id") int id) {
        return dealService.deleteById(id);

    }

    /**
     * 早间提醒
     * 模板： 提醒：您在{1}当天，有以下待办事项： {2}，请及时处理！
     *
     * @throws IOException
     */
    @ApiOperation("发送待办短信-早间提醒")
    @Scheduled(cron = "${cron_tom_send}")
    @GetMapping("/send/morning")
    public void sendMsgMorning() throws IOException {
        List<Deal> dealList = dealService.getAllDealMorning();
        for (int i = 0; dealList.size() > i; i++) {
            // 用户信息
            String phone = phoneService.getUserPhoneById(dealList.get(i).getUserId());
            String msg = dealList.get(i).getMsg();
            String msgFlag = dealList.get(i).getMsgFlag();
            if ("1".equals(msgFlag)) {
                if (Format.format(new Date()).equals(Format.format(dealList.get(i).getMsgDate())) ) {
                    String[] phoneNumbers = {phone};
                    String[] templateParams = {Format2.format(new Date()), msg};
                    String templateId = "665408";
                    sendSms.sendMsg(templateId, phoneNumbers, templateParams);
                    log.info("现在时间：" + Format.format(new Date()) + ",单次提醒信息已发送给：" + phoneService.getUserById(dealList.get(i).getUserId()));
                }
            }
            if ("2".equals(msgFlag)) {
                if (String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).equals(dealList.get(i).getMonthDays())) {
                    String[] phoneNumbers = {phone};
                    String[] templateParams = {Format2.format(new Date()), msg};
                    String templateId = "665408";
                    sendSms.sendMsg(templateId, phoneNumbers, templateParams);
                    log.info("现在时间：" + Format.format(new Date()) + ",每月提醒信息已发送给：" + phoneService.getUserById(dealList.get(i).getUserId()));
                }
            }
            if ("3".equals(msgFlag)) {
                String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
                Calendar calendar = Calendar.getInstance();
                if (weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1].equals(dealList.get(i).getWeekDays())) {
                    String[] phoneNumbers = {phone};
                    String[] templateParams = {Format2.format(new Date()), msg};
                    String templateId = "665408";
                    sendSms.sendMsg(templateId, phoneNumbers, templateParams);
                    log.info("现在时间：" + Format.format(new Date()) + ",每周提醒信息已发送给：" + phoneService.getUserById(dealList.get(i).getUserId()));
                }
            }
        }
    }

    /**
     * 晚间提醒
     * 模板： 提醒：您在{1}当天，有以下待办事项： {2}，请及时处理！
     *
     * @throws IOException
     */
    @ApiOperation("发送待办短信-晚间提醒")
    @GetMapping("/send/evening")
    @Scheduled(cron = "${cron_tod_send}")
    public void sendMsgEvening() throws IOException {
        List<Deal> dealList = dealService.getAllDealEvening();
        for (int i = 0; dealList.size() > i; i++) {
            // 用户信息
            String phone = phoneService.getUserPhoneById(dealList.get(i).getUserId());
            String msg = dealList.get(i).getMsg();
            String msgFlag = dealList.get(i).getMsgFlag();
            if ("1".equals(msgFlag)) {
                if (Format.format(new Date()).equals(Format.format(dealList.get(i).getMsgDate()))) {
                    String[] phoneNumbers = {phone};
                    String[] templateParams = {Format2.format(new Date()), msg};
                    String templateId = "665408";
                    sendSms.sendMsg(templateId, phoneNumbers, templateParams);
                    log.info("现在时间：" + Format.format(new Date()) + ",单次提醒信息已发送给：" + phoneService.getUserById(dealList.get(i).getUserId()));
                }
            }
            if ("2".equals(msgFlag)) {
                if (String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).equals(dealList.get(i).getMonthDays())) {
                    String[] phoneNumbers = {phone};
                    String[] templateParams = {Format2.format(new Date()), msg};
                    String templateId = "665408";
                    sendSms.sendMsg(templateId, phoneNumbers, templateParams);
                    log.info("现在时间：" + Format.format(new Date()) + ",每月提醒信息已发送给：" + phoneService.getUserById(dealList.get(i).getUserId()));
                }
            }
            if ("3".equals(msgFlag)) {
                String[] weekDays = {"7", "1", "2", "3", "4", "5", "6"};
                Calendar calendar = Calendar.getInstance();
                if (weekDays[calendar.get(Calendar.DAY_OF_WEEK) - 1].equals(dealList.get(i).getWeekDays())) {
                    String[] phoneNumbers = {phone};
                    String[] templateParams = {Format2.format(new Date()), msg};
                    String templateId = "665408";
                    sendSms.sendMsg(templateId, phoneNumbers, templateParams);
                    log.info("现在时间：" + Format.format(new Date()) + ",每周提醒信息已发送给：" + phoneService.getUserById(dealList.get(i).getUserId()));
                }
            }
        }
    }
}
