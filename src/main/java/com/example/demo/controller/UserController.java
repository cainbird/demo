package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findById")
    public User findById(Integer id){
        return userService.findById(id);
    }

}
