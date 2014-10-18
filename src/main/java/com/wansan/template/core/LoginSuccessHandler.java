package com.wansan.template.core;

import com.wansan.template.model.Person;
import com.wansan.template.service.IPersonService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 14-7-12.
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
    @Resource
    private IPersonService personService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Person person = (Person)authentication.getPrincipal();
        person.setLastlogin(Utils.getNow());
        // get IP
        // String ipAddr = request.getRemoteAddr();
        personService.txUpdate(person,person,false);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
