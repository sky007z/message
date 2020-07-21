package com.laughing.message.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.dao.CityList;
import com.laughing.message.dao.Phone;
import com.laughing.message.mapper.CityListMapper;
import com.tencentcloudapi.ecm.v20190719.models.City;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/16 11:32
 */
@Service
public class CityService {
    @Autowired
    public CityList cityList;
    @Autowired
    public CityListMapper cityListMapper;

    /**
     * 获取全部城市分页
     *
     * @return
     */
    public Page<CityList> getAllCityPages(int current, int size, String name, String code) {
        QueryWrapper<CityList> wrapper = new QueryWrapper<>();
        if (!StringUtil.isBlank(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtil.isBlank(code)) {
            wrapper.like("code", code);
        }

        Page<CityList> cityPage = new Page<>(current, size);//参数一是当前页，参数二是每页个数
        cityPage = cityListMapper.selectPage(cityPage, wrapper);
        return cityPage;

    }

    /**
     * 根据城市代码获取城市名
     *
     * @param cityCode
     * @return
     */
    public String getCityNameByCode(String cityCode) {
        Map selectCityCodeMap = new HashMap();
        selectCityCodeMap.put("code", cityCode);

        CityList cityList = (CityList) cityListMapper.selectByMap(selectCityCodeMap).get(0);
        return cityList.getName();

    }


}
