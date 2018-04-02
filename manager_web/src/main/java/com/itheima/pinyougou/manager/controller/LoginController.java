
package com.itheima.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/name")
    public Map<String,Object> name(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String,Object> map = new HashMap<>();
        map.put("loginName",name);
        return map;
    }
}
