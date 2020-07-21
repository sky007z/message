package com.laughing.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laughing.message.dao.CityList;
import com.laughing.message.dao.Phone;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/16 11:31
 */
@Repository
public interface CityListMapper extends BaseMapper<CityList> {
    @Select("SELECT name FROM city_list")
    List<String> cityListName(Wrapper wrapper);
}
