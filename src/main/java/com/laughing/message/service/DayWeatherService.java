package com.laughing.message.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.dao.WeatherDay;
import com.laughing.message.mapper.WeatherDayMapper;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.*;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/15 14:07
 */
@Service
public class DayWeatherService {

    public final static String WEATHER_URL = "http://www.weather.com.cn";

    @Autowired
    public WeatherDay weatherDay;

    @Autowired
    public WeatherDayMapper weatherDayMapper;

    /**
     * 获取并存储城市 7 天天气信息
     * 存储前先清空表 此表每个城市只有最新的7天数据
     *
     * @param cityCode 城市代码
     * @throws IOException
     */
    public void setWeatherData(String cityCode) throws IOException {

        String url = WEATHER_URL + "/weather/" + cityCode + ".shtml";
        Document doc = Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozilla")
                .timeout(3000)
                .get();

        Element elements = doc.getElementById("7d");
        Element element = elements.selectFirst("ul.clearfix");
        Elements elements1 = element.getElementsByTag("li");
        if (elements1 != null && elements1.size() > 0) {
            weatherDay.setCreatTime(new Date());
            weatherDay.setCityCode(cityCode);
            Map deleteMap = new HashMap();
            deleteMap.put("city_code", cityCode);
            weatherDayMapper.deleteByMap(deleteMap);
            for (int i = 0; i < elements1.size(); i++) {
                weatherDay.setTime(elements1.get(i).getElementsByTag("h1").html());
                weatherDay.setWeather(elements1.get(i).getElementsByClass("wea").html());
                weatherDay.setTemperature(elements1.get(i).selectFirst("span").html() + "/" + elements1.get(i).selectFirst("i").html());
                weatherDayMapper.insert(weatherDay);
            }
        }


    }

    /**
     * 获取 7 天天气情况
     *
     * @return
     */
    public List<WeatherDay> getAllWeather() {
        return weatherDayMapper.selectList(null);
    }

    /**
     * 获取第二天天气
     *
     * @return
     */
    public WeatherDay getTomorrowWeather(String cityCode) {
        Map selectCityCodeMap = new HashMap();
        selectCityCodeMap.put("city_code", cityCode);
        return (WeatherDay) weatherDayMapper.selectByMap(selectCityCodeMap).get(1);
    }


    /**
     * 获取当天天气
     *
     * @return
     */
    public WeatherDay getTodayWeather(String cityCode) {
        Map selectCityCodeMap = new HashMap();
        selectCityCodeMap.put("city_code", cityCode);
        return (WeatherDay) weatherDayMapper.selectByMap(selectCityCodeMap).get(0);
    }


    /**
     * 获取全部天气分页
     *
     * @return
     */
    public Page<WeatherDay> getAllWeatherPages(int current, int size, String cityCode, String time, String weather) {

        QueryWrapper<WeatherDay> wrapper = new QueryWrapper<>();
        if (!StringUtil.isBlank(cityCode)) {
            wrapper.like("city_code", cityCode);
        }
        if (!StringUtil.isBlank(time)) {
            wrapper.like("time", time);
        }
        if (!StringUtil.isBlank(weather)) {
            wrapper.like("weather", weather);
        }


        Page<WeatherDay> weatherPage = new Page<>(current, size);//参数一是当前页，参数二是每页个数
        weatherPage = weatherDayMapper.selectPage(weatherPage, wrapper);
        return weatherPage;
    }


}
