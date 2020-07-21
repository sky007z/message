package com.laughing.message.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/16 10:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class Phone {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * name
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;
    /**
     * 所在城市代码
     */
    @ApiModelProperty(value = "所在城市代码")
    private String cityCode;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态:0->关闭;1->开启")
    private String state;
    /**
     * 配置规则
     */
    @ApiModelProperty(value = "状态:1->晚上发送明日;2->晚上发送明日（极端天气）;3->早上发送今日;4->早上发送今日（极端天气）;")
    private String rule;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date birthday;
    @ApiModelProperty(value = "生日开启状态")
    private String birthdayState;
}
