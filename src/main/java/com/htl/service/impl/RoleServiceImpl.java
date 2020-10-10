package com.htl.service.impl;

import com.htl.dao.RoleDao;
import com.htl.domain.Role;
import com.htl.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;
    public void setRoleDao(RoleDao roleDao){
        this.roleDao = roleDao;
    }

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> list() {
        List<Role> roleList = roleDao.findAll();
        return roleList;
    }

    /**
     * 新建角色
     * @param role
     */
    public void save(Role role) {
        roleDao.save(role);
    }

    /**
     * 删除角色操作
     * @param roleId
     */
    public void delete(Long roleId) {
        //1、删除sys_user_role关系表数据（先）
        roleDao.deleteUserRoleRel(roleId);
        //2、删除sys_role表数据（后）
        roleDao.deleteRole(roleId);
    }

}