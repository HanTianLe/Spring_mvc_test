package com.htl.controller;

import com.htl.domain.Role;
import com.htl.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/role")
@Controller
public class RoleController {

    @Resource(name = "roleService")
    private RoleService roleService;

    /**
     * 查询所有角色
     * @return modelAndView
     */
    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView modelAndView = new ModelAndView();
        List<Role> roleList = roleService.list();
        //设置模型
        modelAndView.addObject("roleList",roleList);
        //设置视图
        modelAndView.setViewName("role-list");
        return modelAndView;
    }

    /**
     * 新建角色
     * @param role
     * @return
     */
    @RequestMapping("/save")
    public String save(Role role){
        roleService.save(role);
        return "redirect:/role/list";
    }

    /**
     * 删除角色操作
     * @param roleId
     * @return
     */
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId") Long roleId){
        roleService.delete(roleId);
        return "redirect:/role/list";
    }

}