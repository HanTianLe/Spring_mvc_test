package com.htl.dao.impl;

import com.htl.dao.RoleDao;
import com.htl.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoleDaoImpl implements RoleDao {

    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> findAll() {
        List<Role> roleList = jdbcTemplate.
                query("select * from sys_role", new BeanPropertyRowMapper<Role>(Role.class));
        return roleList;
    }

    /**
     * 新建角色
     * @param role
     */
    public void save(Role role) {
        jdbcTemplate.update("insert into sys_role values(?,?,?)",
                null,role.getRoleName(),role.getRoleDesc());
    }

    /**
     * 根据 用户 id 查询角色
     * @param id
     * @return
     */
    public List<Role> findRoleByUserId(Long id) {
        List<Role> roles = jdbcTemplate.query("select * from sys_user_role ur,sys_role r where ur.roleId=r.id and ur.userId=?",
                new BeanPropertyRowMapper<Role>(Role.class), id);
        return roles;
    }

    /**
     * 删除sys_user_role关系表数据（先）
     * @param roleId
     */
    public void deleteUserRoleRel(Long roleId) {
        jdbcTemplate.update("delete from sys_user_role where roleId=?",roleId);
    }

    /**
     * 删除sys_role表数据（后）
     * @param roleId
     */
    public void deleteRole(Long roleId) {
        jdbcTemplate.update("delete from sys_role where id=?",roleId);
    }

}