package com.laughing.message.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.dao.Deal;
import com.laughing.message.mapper.DealMapper;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description:
 * @date 2020/7/20 12:19
 */
@Service
public class DealService {

    @Autowired
    public Deal deal;
    @Autowired
    public DealMapper dealMapper;


    /**
     * 插入待办短信任务
     *
     * @return
     */
    public int addDeal(Deal deal) {

        return dealMapper.insert(deal);
    }

    /**
     * 查询待办短信任务
     */
    public Page<Deal> getDeal(int current, int size, String name) {
        QueryWrapper<Deal> wrapper = new QueryWrapper<>();
        if (!StringUtil.isBlank(name)) {
            wrapper.like("name", name);
        }
        Page<Deal> dealPage = new Page<>(current, size);//参数一是当前页，参数二是每页个数
        dealPage = dealMapper.selectPage(dealPage, wrapper);
        return dealPage;
    }
    /**
     * 查询全部 早间提醒
     */
    public List<Deal> getAllDealMorning(){
        Map selectUserMap = new HashMap();
        selectUserMap.put("ning","早间提醒");
        return dealMapper.selectByMap(selectUserMap);
    }
    /**
     * 查询全部 晚间提醒
     */
    public List<Deal> getAllDealEvening(){
        Map selectUserMap = new HashMap();
        selectUserMap.put("ning","晚间提醒");
        return dealMapper.selectByMap(selectUserMap);
    }
    /**
     * 修改代办短信
     */
    public int updateDeal(int id, String msg, Date msgDate) {
        QueryWrapper<Deal> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        deal.setMsg(msg);
        deal.setMsgDate(msgDate);
        return dealMapper.update(deal,wrapper);
    }
    /**
     * 删除
     */
    public int deleteById(int id){
        return dealMapper.deleteById(id);
    }

}
