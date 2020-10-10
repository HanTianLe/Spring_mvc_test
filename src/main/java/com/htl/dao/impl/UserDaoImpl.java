package com.htl.dao.impl;

import com.htl.dao.UserDao;
import com.htl.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 查询所有用户
     * @return userList
     */
    public List<User> findAll() {
        List<User> userList = jdbcTemplate.
                query("select * from sys_user", new BeanPropertyRowMapper<User>(User.class));
        return userList;
    }

    /**
     * 向 sys_user表中存储数据，并返回当前保存用户的id
     * @param user
     */
    public Long save(final User user) {
        //jdbcTemplate.update("insert into sys_user values(?,?,?,?,?)",null,user.getUsername(),user.getEmail(),user.getPassword(),user.getPhoneNum());
        //创建PrepareStatementCreator
        PreparedStatementCreator creator = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                //使用原始jdbc完成有个PreparedStatement的组件
                PreparedStatement preparedStatement = connection.prepareStatement("insert into sys_user values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1,null);
                preparedStatement.setString(2,user.getUsername());
                preparedStatement.setString(3,user.getEmail());
                preparedStatement.setString(4,user.getPassword());
                preparedStatement.setString(5,user.getPhoneNum());
                return preparedStatement;
            }
        };
        //创建KeyHolder
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(creator,keyHolder);
        //获得生成的主键
        long userId = keyHolder.getKey().longValue();
        return userId; //返回当前保存用户的id 该id是数据库自动生成的。
        //这种获得主键的方法不需要记忆，了解就行。（MyBatis能够很好的解决该需求。）
    }

    /**
     * 向 sys_user_role关系表中存储多条数据
     * @param userId
     * @param roleIds
     */
    public void saveUserRoleRel(Long userId, Long[] roleIds) {
        for (Long roleId:roleIds){
            jdbcTemplate.update("insert into sys_user_role values(?,?)",userId,roleId);
        }
    }

    /**
     * 删除sys_user_role关系表数据（先）
     * @param userId
     */
    public void deleteUserRoleRel(Long userId) {
        jdbcTemplate.update("delete from sys_user_role where userId=?",userId);
    }

    /**
     * 删除sys_user表数据（后）
     * @param userId
     */
    public void deleteUser(Long userId) {
        jdbcTemplate.update("delete from sys_user where id=?",userId);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password) throws EmptyResultDataAccessException {
        User user = jdbcTemplate.queryForObject("select * from sys_user where username=? and password=?",
                new BeanPropertyRowMapper<User>(User.class), username, password);
        return user;
    }

}