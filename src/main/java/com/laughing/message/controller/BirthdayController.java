package com.laughing.message.controller;

import com.laughing.message.service.DayWeatherService;
import com.laughing.message.service.PhoneService;
import com.laughing.message.service.SendSmsService;
import com.laughing.message.dao.Phone;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: 生日控制层配置
 * @date 2020/7/20 9:11
 */
@Slf4j
@RestController
@PropertySource("classpath:/task-config.properties")
@Api(tags = "生日接口")
@RequestMapping("/birthday")
public class BirthdayController {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat birthdayFormat = new SimpleDateFormat("MM-dd");
    @Autowired
    private DayWeatherService dayWeatherService;
    @Autowired
    private SendSmsService sendSms;
    @Autowired
    private PhoneService phoneService;

    /**
     * 生日祝福短信发送
     */
    @Scheduled(cron = "${cron_birthday_send}")
    @GetMapping("/send")
    private void sendBirthday(){
        List<Phone> phoneList = phoneService.getAllBirthdayUser();
        for (int i = 0; phoneList.size() > i; i++) {
            // 用户信息
            String phone = phoneList.get(i).getPhone();
            String name = phoneList.get(i).getName();
            Date birthday = phoneList.get(i).getBirthday();
            if(birthdayFormat.format(new Date()).equals(birthdayFormat.format(birthday)) ){
                String[] phoneNumbers = {phone};
                String[] templateParams = {name,"laughing"};
                String templateId = "665407";
                sendSms.sendMsg(templateId, phoneNumbers, templateParams);
                log.info("生日短信发送，日期：" + birthdayFormat.format(new Date()) + ",生日祝福短信已发送给" + name);

            }

        }
    }

}
