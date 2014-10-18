package com.wansan.template.service;

import com.wansan.template.model.Person;
import com.wansan.template.model.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 14-4-15.
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Resource
    private IPersonService personService;

    @Resource
    private IRoleService roleService;

    private Log log= LogFactory.getLog(this.getClass());

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person person = personService.findPersonByName(s);
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        if(null!=person){
            List<Role> roles = roleService.getRolesByUser(person);
            for(Role role:roles){
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.getName());
                auths.add(auth);
                }
            person.setAuthorities(auths);
            return person;
        } else
            throw new UsernameNotFoundException("用户名" + s + "不存在！");

    }

}
