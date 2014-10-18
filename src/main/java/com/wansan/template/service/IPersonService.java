package com.wansan.template.service;

import com.wansan.template.model.Person;

import java.io.Serializable;

/**
 * Created by Administrator on 14-4-15.
 */
public interface IPersonService extends IBaseDao<Person> {
    public Person findPersonByName(String username);
    public void txUpdate(Person person,Person oper,boolean cp);
}
