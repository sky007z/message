package com.laughing.message.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.dao.Phone;
import com.laughing.message.mapper.PhoneMapper;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/16 10:15
 */
@Service
public class PhoneService {
    @Autowired
    public Phone phone;
    @Autowired
    public PhoneMapper phoneMapper;

    /**
     * 获取全部天气服务开启用户
     *
     * @return
     */
    public List<Phone> getAllWeatherUser() {
        Map selectUserMap = new HashMap();
        selectUserMap.put("state", "1");
        return phoneMapper.selectByMap(selectUserMap);
    }

    /**
     * 获取全部用户
     *
     * @return
     */
    public List<Phone> getAllUser() {
        Map selectUserMap = new HashMap();
        return phoneMapper.selectByMap(selectUserMap);
    }

    /**
     * 获取全部生日用户
     *
     * @return
     */
    public List<Phone> getAllBirthdayUser() {
        Map selectUserMap = new HashMap();
        selectUserMap.put("birthday_state", "1");
        return phoneMapper.selectByMap(selectUserMap);
    }

    /**
     * 获取全部用户 配置规则为 1
     * 晚上发送明日
     *
     * @return
     */
    public List<Phone> getAllUserone() {
        Map selectUserMap = new HashMap();
        selectUserMap.put("state", "1");
        selectUserMap.put("rule", "1");
        return phoneMapper.selectByMap(selectUserMap);
    }

    /**
     * 获取全部用户 配置规则为 2
     * 晚上发送明日（极端天气）
     *
     * @return
     */
    public List<Phone> getAllUserotwo() {
        Map selectUserMap = new HashMap();
        selectUserMap.put("state", "1");
        selectUserMap.put("rule", "2");
        return phoneMapper.selectByMap(selectUserMap);
    }

    /**
     * 获取全部用户 配置规则为 3
     * 早上发送今日
     *
     * @return
     */
    public List<Phone> getAllUserthree() {
        Map selectUserMap = new HashMap();
        selectUserMap.put("state", "1");
        selectUserMap.put("rule", "3");
        return phoneMapper.selectByMap(selectUserMap);
    }

    /**
     * 获取全部用户 配置规则为 4
     * 晚上发送明日(极端天气)
     *
     * @return
     */
    public List<Phone> getAllUserfour() {
        Map selectUserMap = new HashMap();
        selectUserMap.put("state", "1");
        selectUserMap.put("rule", "4");
        return phoneMapper.selectByMap(selectUserMap);
    }

    /**
     * 获取全部用户的城市代码
     *
     * @return cityCodeList
     */
    public List<String> getAllCityCodes() {
        List<String> cityCodeList = new ArrayList<>();
        for (int i = 0; phoneMapper.selectList(null).size() > i; i++) {
            cityCodeList.add(phoneMapper.selectList(null).get(i).getCityCode());
        }
        return cityCodeList;
    }

    /**
     * 获取全部用户的手机号
     *
     * @return
     */
    public List<String> getAllPhones() {
        List<String> phonesList = new ArrayList<>();
        for (int i = 0; phoneMapper.selectList(null).size() > i; i++) {
            phonesList.add(phoneMapper.selectList(null).get(i).getPhone());
        }
        return phonesList;
    }

    /**
     * 添加用户
     *
     * @param phoneNumber
     * @param cityCode
     */
    public int addPhone(String name, String phoneNumber, String cityCode, String state, String rule, Date birthday, String birthdayState) {
        phone.setName(name);
        phone.setPhone(phoneNumber);
        phone.setCityCode(cityCode);
        phone.setState(state);
        phone.setRule(rule);
        phone.setBirthday(birthday);
        phone.setBirthdayState(birthdayState);
        return phoneMapper.insert(phone);
    }


    /**
     * 获取全部用户的手机号分页 模糊查询
     *
     * @return
     */
    public Page<Phone> getAllPhonesPages(int current, int size, String name, String phone, String cityCode) {

        QueryWrapper<Phone> wrapper = new QueryWrapper<>();
        if (!StringUtil.isBlank(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtil.isBlank(phone)) {
            wrapper.like("phone", phone);
        }
        if (!StringUtil.isBlank(cityCode)) {
            wrapper.like("city_code", cityCode);
        }
        Page<Phone> page = new Page<>(current, size);
        page = phoneMapper.selectPage(page, wrapper);
        return page;
    }

    /**
     * 修改用户信息
     */
    public int updatePhone(int id, String name, String phoneNumber, String cityCode, String state, String rule, Date birthday, String birthdayState) {

        QueryWrapper<Phone> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        phone.setId(id);
        phone.setName(name);
        phone.setPhone(phoneNumber);
        phone.setCityCode(cityCode);
        phone.setState(state);
        phone.setRule(rule);
        phone.setBirthday(birthday);
        phone.setBirthdayState(birthdayState);
        return phoneMapper.update(phone, wrapper);
    }

    /**
     * 关闭全部天气用户状态
     */
    public int turnOff() {
        phone.setState("0");
        return phoneMapper.update(phone, null);
    }

    /**
     * 开启全部天气用户状态
     */
    public int turnOn() {
        phone.setState("1");
        return phoneMapper.update(phone, null);
    }

    /**
     * 关闭全部生日用户状态
     */
    public int turnOffBirthday() {
        phone.setBirthdayState("0");
        return phoneMapper.update(phone, null);
    }

    /**
     * 开启全部生日用户状态
     */
    public int turnOnBirthday() {
        phone.setBirthdayState("1");
        return phoneMapper.update(phone, null);
    }

    /**
     * 关闭单个生日用户状态
     */
    public int turnOffBirthdayById(int id) {
        phone.setBirthdayState("0");
        phone.setId(id);
        return phoneMapper.updateById(phone);

    }

    /**
     * 开启单个生日用户状态
     */
    public int turnOnBirthdayById(int id) {
        phone.setBirthdayState("1");
        phone.setId(id);
        return phoneMapper.updateById(phone);
    }

    /**
     * 删除用户
     */
    public int deletePhone(int id) {

        return phoneMapper.deleteById(id);
    }

    /**
     * 用户数量
     */
    public int countPhone() {

        return phoneMapper.selectCount(null);
    }

    /**
     * 用户绑定城市数量
     */
    public int countCity() {


        return phoneMapper.countCity(null).size();
    }

    /**
     * 根据id查姓名
     *
     * @return
     */
    public String getUserById(int id) {
        return phoneMapper.selectById(id).getName();

    }

    /**
     * 根据id查号码
     *
     * @return
     */
    public String getUserPhoneById(int id) {
        return phoneMapper.selectById(id).getPhone();

    }
}
