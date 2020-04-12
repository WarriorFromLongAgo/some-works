package com.orhon.smartcampus.modules.core.controller;


import com.orhon.smartcampus.modules.core.graphql.orm.entity.Module;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GqlController {


    @Autowired
    private SessionFactory sessionFactory;


    @GetMapping("/htest")
    public List<Module> test2222(){

        String hql = "from Module";
        Query q = sessionFactory.openSession().createQuery(hql);
        q.setMaxResults(10);
        q.setFirstResult(1);

        List<Module> list = q.list();

        return list;

    }
}
