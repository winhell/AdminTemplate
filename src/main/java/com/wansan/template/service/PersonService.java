package com.wansan.template.service;

import com.wansan.template.core.Utils;
import com.wansan.template.model.AccountStatusEnum;
import com.wansan.template.model.OperEnum;
import com.wansan.template.model.Person;
import com.wansan.template.model.Syslog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 14-4-15.
 */

@Service
public class PersonService extends BaseDao<Person> implements IPersonService {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private IRoleService roleService;

    public List<Person> getUserList(Person person){
        String roles = roleService.getRolesByUserID(person.getId());
        if(roles.contains("super"))
            return listAll();
        else{
            String hql = "from Person where id not in (select userid from UserRole where roleid = 'super')";
            Query query = getSession().createQuery(hql);
            return query.list();
        }
    }

    @Override
    public Person findPersonByName(String username) {
        List<Person> persons = findByProperty("name",username);
        if(null==persons||persons.size()==0){
            return null;
        }
        return persons.get(0);
    }

    @Override
    public Serializable txSave(Person person,Person oper){

        String orgiPasswd = person.getPassword();
        person.setPassword(Utils.encodePassword(orgiPasswd,person.getName()));

        Syslog syslog = new Syslog();
        syslog.setUserid(oper.getName());
        syslog.setName(OperEnum.CREATE.toString());
        syslog.setId(Utils.getNewUUID());
        syslog.setCreatetime(Utils.getNow());
        syslog.setComment("user "+oper.getName()+" 创建用户--> "+person.getName());
        getSession().save(syslog);
        person.setLocked(false);
        person.setExpired(false);
        person.setEnabled(true);
        return save(person);
    }

    //Todo: 添加日志信息
    @Override
    public void txDelete(String idList,Person oper){
        String[] ids = idList.split(",");
        for(String id:ids){
            executeQuery("delete from UserRole where userid = '"+id+"'");
        }
        delete(idList);
    }

    //Todo: 添加日志信息
    @Override
    public void txUpdate(Person person,Person oper,boolean cp){
        Person temp = findById(person.getId());
        if(!"".equals(person.getPassword())){
            if(cp)
                temp.setPassword(Utils.encodePassword(person.getPassword(),person.getName()));
        }
        temp.setComment(person.getComment());
        temp.setDepartId(person.getDepartId());
        temp.setLastlogin(person.getLastlogin());
        saveOrUpdate(temp, false);
    }

    public void txChangeStatus(AccountStatusEnum statusType,String id){
        String hql = "update person set ";
        switch (statusType){
            case enable:
                hql += "enable = NOT enable ";
                break;
            case locked:
                hql += "locked = NOT locked ";
                break;
            case expired:
                hql += "expired = NOT expired ";
                break;
            default:
                break;
        }
        hql += "where id=:userID";
//        Query query = getSession().createQuery(hql);
        Query query = getSession().createSQLQuery(hql);
        query.setParameter("userID",id);
        query.executeUpdate();
    }
}
