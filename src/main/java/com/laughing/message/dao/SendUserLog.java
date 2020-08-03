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
 * @Description: 发送记录日志
 * @date 2020/8/3 13:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class SendUserLog {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private int id;
    @ApiModelProperty(value = "用户id")
    private int phoneId;
    /**
     * name
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;


    @ApiModelProperty(value = "信息内容")
    private String msg;

    @ApiModelProperty(value = "发送时间")
    private Date sendtTime;
}
