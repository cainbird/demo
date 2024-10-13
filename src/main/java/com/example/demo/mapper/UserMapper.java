package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.pojo.User;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    public User findById(Integer id);

}
