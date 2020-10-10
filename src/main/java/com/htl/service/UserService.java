package com.htl.service;

import com.htl.domain.User;

import java.util.List;

public interface UserService {
    //查询所有用户
    public List<User> list();
    //新建用户的保存操作
    public void save(User user, Long[] roleIds);
    //删除用户操作
    public void delete(Long userId);
    //用户登录
    public User login(String username, String password);
}