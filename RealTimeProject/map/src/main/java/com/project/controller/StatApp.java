/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: StatApp
 * Author:   臧浩鹏
 * Date:     2018/7/19 15:13
 * Description: 项目的controller层
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.project.controller;

import com.alibaba.fastjson.JSONArray;
import com.project.model.people;
import com.project.service.PeopleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈项目的controller层〉
 *
 * @author 臧浩鹏
 * @create 2018/7/19
 * @since 1.0.0
 */
@RestController
public class StatApp {
    @Autowired
    PeopleResult peopleResult;
    @RequestMapping(value = "/map")
    public ModelAndView getMap(){
        ModelAndView modelAndView = new ModelAndView("map");
        List<people> res = peopleResult.query();
        JSONArray jsonArray = JSONArray.parseArray(JSONArray.toJSONString(res));
        System.out.println(jsonArray);
        modelAndView.addObject("datas",jsonArray);
        return modelAndView;
    }

}
