package com.laughing.message.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/16 9:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class WeatherDay {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 预报时间
     */
    @ApiModelProperty(value = "预报时间")
    private String time;
    /**
     * 天气状况
     */
    @ApiModelProperty(value = "天气状况")
    private String weather;
    /**
     * 城市代码
     */
    @ApiModelProperty(value = "城市代码")
    private String cityCode;
    /**
     * 温度
     */
    @ApiModelProperty(value = "温度")
    private String temperature;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;


}
