package com.laughing.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.laughing.message.dao.Phone;
import com.laughing.message.dao.WeatherDay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/16 10:14
 */
@Repository
public interface PhoneMapper extends BaseMapper<Phone> {

    /**
     * @param wrapper
     * @return
     */
    @Select("SELECT COUNT(*) FROM phone GROUP BY city_code")
    List<String> countCity(Wrapper wrapper);
}

