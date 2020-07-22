package com.laughing.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.Service.CityService;
import com.laughing.message.Service.DayWeatherService;
import com.laughing.message.Service.PhoneService;
import com.laughing.message.Service.SendSmsService;
import com.laughing.message.dao.Phone;
import com.laughing.message.dao.WeatherDay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/15 16:06
 */
@Slf4j
@Api(tags = "天气接口")
@RestController
@PropertySource("classpath:/task-config.properties")
@RequestMapping("/weather")
public class WeatherController {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    private DayWeatherService dayWeatherService;
    @Autowired
    private SendSmsService sendSms;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private CityService cityService;


    /**
     * 存储全部用户绑定城市的7日天气信息
     *
     * @throws IOException
     */
    @ApiOperation("存储全部用户绑定城市的7日天气信息")
    @Scheduled(cron = "${cron_save_all}")
    @GetMapping("/setAll/7d")
    public void setAllWeather() throws IOException {
        log.info("现在时间：" + dateFormat.format(new Date()) + ",存储全部用户绑定城市的7日天气信息");
        List<String> cityCodeList = phoneService.getAllCityCodes();
        for (int i = 0; i < cityCodeList.size(); i++) {
            dayWeatherService.setWeatherData(cityCodeList.get(i));
        }
    }

    /**
     * 全部用户发送明日天气短信
     * 模板： 尊敬的{1}，提醒您{2}天气情况：{3}，天气{4}，当日温度{5}
     *
     * @throws IOException
     */
    @ApiOperation("全部用户发送明日天气短信 当晚发送明日天气短信 晚上8点10分")
    @Scheduled(cron = "${cron_tom_send}")
    @GetMapping("/send/msg/tom")
    public void sendWeatherMsgTom() throws IOException {
        List<Phone> phoneList = phoneService.getAllUserone();
        for (int i = 0; phoneList.size() > i; i++) {
            // 用户信息
            String phone = phoneList.get(i).getPhone();
            String cityCode = phoneList.get(i).getCityCode();
            String name = phoneList.get(i).getName();
            // 城市信息
            String cityName = cityService.getCityNameByCode(cityCode);
            // 天气信息
            WeatherDay weatherDay = dayWeatherService.getTomorrowWeather(cityCode);
            String time = weatherDay.getTime();
            String weather = weatherDay.getWeather();
            String temperature = weatherDay.getTemperature();
            // 设置发送参数
            String[] phoneNumbers = {phone};
            String[] templateParams = {name, cityName, time, weather, temperature};
            String templateID = "663325";
            sendSms.sendMsg(templateID, phoneNumbers, templateParams);
            log.info("现在时间：" + dateFormat.format(new Date()) + ",明日预报已发送给" + name);
        }
    }


    /**
     * 全部用户发送今日天气短信
     * 模板： 尊敬的{1}，提醒您{2}天气情况：{3}，天气{4}，当日温度{5}
     *
     * @throws IOException
     */
    @ApiOperation("全部用户发送今日天气短信 当天发送今日短信 早上7点10分")
    @GetMapping("/send/msg/tod")
    @Scheduled(cron = "${cron_tod_send}")
    public void sendWeatherMsgTod() throws IOException {
        List<Phone> phoneList = phoneService.getAllUserthree();
        for (int i = 0; phoneList.size() > i; i++) {
            // 用户信息
            String phone = phoneList.get(i).getPhone();
            String cityCode = phoneList.get(i).getCityCode();
            String name = phoneList.get(i).getName();
            // 城市信息
            String cityName = cityService.getCityNameByCode(cityCode);
            // 天气信息
            WeatherDay weatherDay = dayWeatherService.getTodayWeather(cityCode);
            String time = weatherDay.getTime();
            String weather = weatherDay.getWeather();
            String temperature = weatherDay.getTemperature();
            // 设置发送参数
            String[] phoneNumbers = {phone};
            String[] templateParams = {name, cityName, time, weather, temperature};
            String templateID = "663325";
            sendSms.sendMsg(templateID, phoneNumbers, templateParams);
            log.info("现在时间：" + dateFormat.format(new Date()) + ",今日预报已发送给" + name);
        }
    }

    /**
     * 全部用户发送明日天气短信（仅极端天气）
     */
    @ApiOperation("全部用户发送明日天气短信（仅极端天气）晚上8点10分")
    @Scheduled(cron = "${cron_tom_send}")
    @GetMapping("/send/msg/tom/rain")
    public void sendRainWeatherMsgTom() throws IOException {
        List<Phone> phoneList = phoneService.getAllUserotwo();
        for (int i = 0; phoneList.size() > i; i++) {
            // 用户信息
            String phone = phoneList.get(i).getPhone();
            String cityCode = phoneList.get(i).getCityCode();
            String name = phoneList.get(i).getName();
            // 城市信息
            String cityName = cityService.getCityNameByCode(cityCode);
            // 天气信息
            WeatherDay weatherDay = dayWeatherService.getTomorrowWeather(cityCode);
            String time = weatherDay.getTime();
            String weather = weatherDay.getWeather();
            String temperature = weatherDay.getTemperature();

            if (weather.contains("雨") || weather.contains("雪") || weather.contains("暴")
                    || weather.contains("雹") || weather.contains("沙")) {
                // 设置发送参数
                String[] phoneNumbers = {phone};
                String[] templateParams = {name, cityName, time, weather, temperature};
                String templateID = "663325";
                sendSms.sendMsg(templateID, phoneNumbers, templateParams);
                log.info("现在时间：" + dateFormat.format(new Date()) + ",明日预报已发送给" + name);
            }
        }
    }


    /**
     * 全部用户发送今日天气短信（仅极端天气）
     */
    @ApiOperation("全部用户发送今日天气短信（仅极端天气）早上7点10分")
    @Scheduled(cron = "${cron_tod_send}")
    @GetMapping("/send/msg/tod/rain")
    public void sendRainWeatherMsgTod() throws IOException {
        List<Phone> phoneList = phoneService.getAllUserfour();
        for (int i = 0; phoneList.size() > i; i++) {
            // 用户信息
            String phone = phoneList.get(i).getPhone();
            String cityCode = phoneList.get(i).getCityCode();
            String name = phoneList.get(i).getName();
            // 城市信息
            String cityName = cityService.getCityNameByCode(cityCode);
            // 天气信息
            WeatherDay weatherDay = dayWeatherService.getTodayWeather(cityCode);
            String time = weatherDay.getTime();
            String weather = weatherDay.getWeather();
            String temperature = weatherDay.getTemperature();

            if (weather.contains("雨") || weather.contains("雪") || weather.contains("暴")
                    || weather.contains("雹") || weather.contains("沙")) {
                // 设置发送参数
                String[] phoneNumbers = {phone};
                String[] templateParams = {name, cityName, time, weather, temperature};
                String templateID = "663325";
                sendSms.sendMsg(templateID, phoneNumbers, templateParams);
                log.info("现在时间：" + dateFormat.format(new Date()) + ",今日预报已发送给" + name);
            }
        }
    }


    /**
     * 根据cityCode查询明日天气
     *
     * @param cityCode
     * @return
     * @throws IOException
     */
    @ApiOperation("根据cityCode查询明日天气")
    @GetMapping("/get/tomorrowweather/{cityCode}")
    public WeatherDay getTomorrowByCityCode(@PathVariable("cityCode") String cityCode) throws IOException {
        return dayWeatherService.getTomorrowWeather(cityCode);
    }


    /**
     * 根据cityCode存储更新 7天 天气数据
     *
     * @param cityCode
     * @throws IOException
     */
    @ApiOperation("根据cityCode存储更新 7天 天气数据")
    @GetMapping("/set/7d/{cityCode}")
    public void setByCityCode(@PathVariable("cityCode") String cityCode) throws IOException {
        dayWeatherService.setWeatherData(cityCode);
    }

    /**
     * 获取全部city分页
     *
     * @return
     */
    @ApiOperation("获取全部天气分页 模糊查询")
    @GetMapping("/view/getcity/{current}/{size}")
    public Page<WeatherDay> getAllWeatherPage(@PathVariable("current") int current,
                                              @PathVariable("size") int size,
                                              @RequestParam("time") String time,
                                              @RequestParam("weather") String weather,
                                              @RequestParam("cityCode") String cityCode
    ) {

        return dayWeatherService.getAllWeatherPages(current, size, cityCode, time, weather);
    }



}
