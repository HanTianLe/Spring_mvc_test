package com.htl.dao;

import com.htl.domain.User;

import java.util.List;

public interface UserDao {
    //查询所有用户
    public List<User> findAll();
    //向 sys_user表中存储数据，并返回当前保存用户的id
    public Long save(User user);
    //向 sys_user_role关系表中存储多条数据
    public void saveUserRoleRel(Long userId, Long[] roleIds);
    //删除sys_user_role关系表数据（先）
    public void deleteUserRoleRel(Long userId);
    //删除sys_user表数据（后）
    public void deleteUser(Long userId);
    //用户登录
    public User findByUsernameAndPassword(String username, String password);
}