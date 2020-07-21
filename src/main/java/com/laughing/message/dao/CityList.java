package com.laughing.message.dao;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/16 11:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class CityList {
    @ApiModelProperty(value = "城市")
    private String name;
    @ApiModelProperty(value = "城市代码")
    private String code;
    @ApiModelProperty(value = "城市拼音")
    private String nameEn;
}
