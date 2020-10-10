package com.htl.dao;

import com.htl.domain.Role;

import java.util.List;

public interface RoleDao {
    //查询所有角色
    public List<Role> findAll();
    //新建角色
    public void save(Role role);
    //根据 用户 id 查询角色
    public List<Role> findRoleByUserId(Long id);
    //删除sys_user_role关系表数据（先）
    public void deleteUserRoleRel(Long roleId);
    //删除sys_role表数据（后）
    public void deleteRole(Long roleId);
}