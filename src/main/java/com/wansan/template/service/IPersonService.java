package com.wansan.template.service;

import com.wansan.template.model.AccountStatusEnum;
import com.wansan.template.model.Person;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 14-4-15.
 */
public interface IPersonService extends IBaseDao<Person> {
    public List<Person> getUserList(Person person);
    public Person findPersonByName(String username);
    public void txUpdate(Person person,Person oper,boolean cp);
    public void txChangeStatus(AccountStatusEnum statusType,String id);
}
