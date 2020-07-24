package com.laughing.message.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laughing.message.service.CityService;
import com.laughing.message.dao.CityList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: 城市控制层
 * @date 2020/7/16 17:11
 */
@RestController
@Api(tags = "城市接口")
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    /**
     * 获取全部city分页 模糊查询
     *
     * @return
     */
    @ApiOperation("获取全部city分页 模糊查询")
    @GetMapping("/view/getcity/{current}/{size}")
    public Page<CityList> getAllPhonePage(@PathVariable("current") int current,
                                          @PathVariable("size") int size,
                                          @RequestParam("name") String name,
                                          @RequestParam("code") String code) {

        return cityService.getAllCityPages(current, size, name, code);
    }

    /**
     * 查询全部
     */
    @ApiOperation("获取全部city")
    @GetMapping("/getcity")
    public List<String> getAll() {

        return cityService.getAll();
    }
}
