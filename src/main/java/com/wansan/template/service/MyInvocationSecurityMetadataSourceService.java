package com.wansan.template.service;

import com.wansan.template.core.SpringFactory;
import com.wansan.template.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 *
 */
@Component
public class MyInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {


    private PathMatcher urlMatcher = new AntPathMatcher();

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public MyInvocationSecurityMetadataSourceService() {
        loadResourceDefine();
    }

    private void loadResourceDefine() {

        SessionFactory sessionFactory = (SessionFactory) SpringFactory.getBean("sessionFactory");

        String username = "";
        String sql = "";
        Session session = sessionFactory.openSession();
        // 在Web服务器启动时，提取系统中的所有权限。
        sql = "select name from role";


        List query = session.createSQLQuery(sql).list();

  /*
   * 应当是资源为key， 权限为value。 资源通常为url， 权限就是那些以ROLE_为前缀的角色。 一个资源可以由多个权限来访问。
   * sparta
   */
        resourceMap = new LinkedHashMap<String, Collection<ConfigAttribute>>();

        for (Object auth : query) {
            ConfigAttribute ca = new SecurityConfig((String) auth);

            List query1 = session.createSQLQuery("select b.url "
                    + "from role_resource a, resource b, role c"
                    + " where a.resourceId = b.id and a.roleId = c.id"
                    + " and c.name='"+auth+"' and b.url <> '' order by b.showOrder asc").list();

            for (Object res : query1) {

    /*
     * 判断资源文件和权限的对应关系，如果已经存在相关的资源url，则要通过该url为key提取出权限集合，将权限增加到权限集合中。
     * sparta
//     */

                if (resourceMap.containsKey(res)) {

                    Collection<ConfigAttribute> value = resourceMap.get(res);
                    value.add(ca);
                    resourceMap.put((String)res, value);
                } else {
                    Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
                    atts.add(ca);
                    resourceMap.put((String)res, atts);
                }

            }

        }
        session.close();

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    // 根据URL，找到相关的权限配置。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {

        // object 是一个URL，被用户请求的url。
        String url = ((FilterInvocation) object).getRequestUrl();

        int firstQuestionMarkIndex = url.indexOf("?");

        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }


        Iterator<String> ite = resourceMap.keySet().iterator();

        while (ite.hasNext()) {
            String resURL = ite.next();

            if (urlMatcher.match(resURL,url )) {

                return resourceMap.get(resURL);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> arg0) {

        return true;
    }

}
