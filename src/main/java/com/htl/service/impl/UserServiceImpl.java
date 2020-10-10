package com.htl.service.impl;

import com.htl.dao.RoleDao;
import com.htl.dao.UserDao;
import com.htl.domain.Role;
import com.htl.domain.User;
import com.htl.service.UserService;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class UserServiceImpl implements UserService {

    //UserDao注入
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    //RoleDao注入
    private RoleDao roleDao;
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    /**
     * 查询所有用户
     * @return userList
     */
    public List<User> list() {
        List<User> userList = userDao.findAll();
        //封装userList中的每一个User的roles数据
        for (User user:userList){
            //获得user的id
            Long id = user.getId();
            //将id作为参数 查询当前userId对应的Role集合数据
            List<Role> roles = roleDao.findRoleByUserId(id);
            user.setRoles(roles);
        }
        return userList;
    }

    /**
     * 新建用户的保存操作
     * @param user
     * @param roleIds
     */
    public void save(User user, Long[] roleIds) {
        //第一步 向sys_user表中存储数据，并返回当前保存用户的id。
        Long userId = userDao.save(user);
        //第二步 向sys_user_role关系表中存储多条数据。
        userDao.saveUserRoleRel(userId,roleIds);
    }

    /**
     * 删除用户操作
     * @param userId
     */
    public void delete(Long userId) {
        //1、删除sys_user_role关系表数据（先）
        userDao.deleteUserRoleRel(userId);
        //2、删除sys_user表数据（后）
        userDao.deleteUser(userId);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        try {
            User user = userDao.findByUsernameAndPassword(username,password);
            return user;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}