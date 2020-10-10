package com.htl.service;

import com.htl.domain.Role;

import java.util.List;

public interface RoleService {
    //查询所有角色
    public List<Role> list();
    //新建角色
    public void save(Role role);
    //删除角色操作
    public void delete(Long roleId);
}