package com.laughing.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.Service.PhoneService;
import com.laughing.message.dao.Phone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: 手机用户控制层
 * @date 2020/7/16 12:08
 */
@RestController
@Api(tags = "手机用户接口")
@RequestMapping("/phone")
public class PhoneController {
    @Autowired
    private Phone phone;
    @Autowired
    private PhoneService phoneService;

    private static final SimpleDateFormat birthdayFormat = new SimpleDateFormat("MM-dd");

    /**
     * 添加用户
     *
     * @param phone
     * @param cityCode
     */
    @ApiOperation("添加用户")
    @GetMapping("/add/{name}/{phone}/{cityCode}/{state}/{rule}/{birthday}/{birthdayState}")
    public String addPhone(@PathVariable("name") String name,
                           @PathVariable("phone") String phone,
                           @PathVariable("cityCode") String cityCode,
                           @PathVariable("state") String state,
                           @PathVariable("rule") String rule,
                           @PathVariable("birthday") Date birthday,
                           @PathVariable("birthdayState") String birthdayState) {
        if (!phone.contains("+86")) {
            phone = "+86" + phone;
        }
        int result = phoneService.addPhone(name, phone, cityCode, state, rule, birthday, birthdayState);
        if (result == 1) {
            return "添加用户" + name + "成功";
        } else {
            return "添加用户失败";
        }
    }

    /**
     * 获取全部天气服务开启用户
     *
     * @return
     */
    @ApiOperation("获取全部天气服务开启用户")
    @GetMapping("/getweatherall")
    public List<Phone> getAllWeatherUser() {
        return phoneService.getAllWeatherUser();
    }

    /**
     * 获取全部用户列表
     *
     * @return
     */
    @ApiOperation("获取全部用户列表")
    @GetMapping("/getall")
    public List<Phone> getAllUser() {
        return phoneService.getAllUser();
    }

    /**
     * 获取全部用户分页
     *
     * @return
     */
    @ApiOperation("获取全部用户分页 模糊查询")
    @GetMapping(value = {"/view/getall/{current}/{size}",})
    public Page<Phone> getAllPhonePage(@PathVariable("current") int current,
                                       @PathVariable("size") int size,
                                       @RequestParam(required = false, value = "name") String name,
                                       @RequestParam(required = false, value = "phone") String phone,
                                       @RequestParam(required = false, value = "cityCode") String cityCode) {

        return phoneService.getAllPhonesPages(current, size, name, phone, cityCode);
    }

    @ApiOperation("修改用户")
    @PostMapping("/update")
    public int updatePhone(@RequestParam(value = "id") int id,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "phone") String phone,
                           @RequestParam(value = "state") String state,
                           @RequestParam(value = "rule") String rule,
                           @RequestParam(value = "cityCode") String cityCode,
                           @RequestParam("birthday") String birthday,
                           @RequestParam("birthdayState") String birthdayState) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        Date date = simpleDateFormat.parse(birthday);
        return phoneService.updatePhone(id, name, phone, cityCode, state, rule, date, birthdayState);
    }

    @ApiOperation("关闭全部用户天气短信服务")
    @GetMapping("/turn/off")
    public int turnOff() {
        return phoneService.turnOff();
    }

    @ApiOperation("开启全部用户天气短信服务")
    @GetMapping("/turn/on")
    public int turnOn() {
        return phoneService.turnOn();
    }

    @ApiOperation("关闭全部用户生日短信服务")
    @GetMapping("/birthday/turn/off")
    public int turnOffBirthday() {
        return phoneService.turnOffBirthday();
    }

    @ApiOperation("开启全部用户生日短信服务")
    @GetMapping("/birthday/turn/on")
    public int turnOnBirthday() {
        return phoneService.turnOnBirthday();
    }

    @ApiOperation("开启单个用户生日短信服务")
    @GetMapping("/birthday/turn/on/{id}")
    public int turnOnBirthdayById(@PathVariable("id") int id) {
        return phoneService.turnOnBirthdayById(id);
    }

    @ApiOperation("关闭单个用户生日短信服务")
    @GetMapping("/birthday/turn/off/{id}")
    public int turnOffBirthdayById(@PathVariable("id") int id) {
        return phoneService.turnOffBirthdayById(id);
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    public int deletePhone(@RequestParam(value = "id") int id) {

        return phoneService.deletePhone(id);
    }

    @ApiOperation("用户数量")
    @GetMapping("/count")
    public int count() {

        return phoneService.countPhone();
    }

    @ApiOperation("用户绑定城市数量")
    @GetMapping("/city/count")
    public int cityCount() {

        return phoneService.countCity();
    }

}
