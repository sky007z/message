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
 * @date 2020/7/20 12:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class Deal {
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private int id;
    @ApiModelProperty(value = "用户id")
    private int userId;
    @ApiModelProperty(value = "用户姓名")
    private String name;
    @ApiModelProperty(value = "待办内容")
    private String msg;
    @ApiModelProperty(value = "提醒种类:1->单次提醒;2->每月提醒；3->每周提醒；4->每日提醒")
    private String msgFlag;
    @ApiModelProperty(value = "单次提醒提醒时间")
    private Date msgDate;
    @ApiModelProperty(value = "每周提醒 周")
    private String weekDays;
    @ApiModelProperty(value = "每月提醒 日")
    private String monthDays;
    @ApiModelProperty(value = "早/晚 提醒")
    private String ning;
    @ApiModelProperty(value = "创建时间")
    private Date creatTime;
}
