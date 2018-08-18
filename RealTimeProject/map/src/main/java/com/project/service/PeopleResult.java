/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: PeopleResult
 * Author:   臧浩鹏
 * Date:     2018/7/19 14:54
 * Description: 从数据库获取result信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.project.service;

import com.project.model.people;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈从数据库获取result信息〉
 *
 * @author 臧浩鹏
 * @create 2018/7/19
 * @since 1.0.0
 */
@Service
public class PeopleResult {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<people> query(){

        String sql = "SELECT longitude,latitude,COUNT(1) AS SUM FROM people GROUP BY longitude,latitude";
        return jdbcTemplate.query(sql, new RowMapper<people>() {
            @Override
            public people mapRow(ResultSet resultSet, int i) throws SQLException {
                people people = new people();

                people.setLng(resultSet.getDouble("longitude"));
                people.setLat(resultSet.getDouble("latitude"));
                people.setCount(resultSet.getLong("SUM"));

                return people;
            }
        });

    }
}
