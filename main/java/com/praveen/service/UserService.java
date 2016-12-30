package com.praveen.service;

import java.util.List;

import com.praveen.model.User;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;

    public boolean saveUser(User user) {
        if (!(user.getUserType() == 1 | user.getUserType() == 2 | user.getUserType() == 3))
            return false;
        if(user.getPassword().length()<8 | !user.getEmail().contains("@"))
            return false;
        try {
            session = sessionFactory.openSession();
            session.persist(user);
            session.flush();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> findUser(String email, String pass) {
        // TODO Auto-generated method stub
        session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        criteria.add(Restrictions.eq("password", pass));
        List list = criteria.list();
        session.close();
        return list;
    }

    public boolean isVerifed(String string, int i) {
        if (i == 1) {
            session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("verificationCode", string));
            User user = (User) criteria.list().get(0);
            session.close();
            return user.getVerified();
        } else if (i == 2) {
            session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("email", string));
            User user = (User) criteria.list().get(0);
            session.close();
            return user.getVerified();
        }
        return false;
    }

    public void verifyUser(String uuid) {
        session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("verificationCode", uuid));
        User user = (User) criteria.list().get(0);
        user.setVerified(true);
        session.merge(user);
        session.flush();
        session.close();
    }

    public boolean uuidexists(String uuid) {
        session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("verificationCode", uuid));
        if (criteria.list().size() > 0) {
            session.close();
            return true;
        } else {
            session.close();
            return false;
        }
    }

   /* @Aspect
    class myAspect {

        @After("execution(* com.praveen.service.UserService.*(..))")
        public void after() {
            session.close();
        }

        @Before("execution(* com.praveen.service.UserService.*(..))")
        public void before() {
            session = sessionFactory.openSession();
        }
    }
*/
}
