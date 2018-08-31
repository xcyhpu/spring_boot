package com.xcy.aspect;

import com.xcy.dao.UserDao;
import com.xcy.domain.UserMongo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xuchunyang on 2018/8/29 17点15分
 */
@Component
@Aspect
public class UserOperationAspect {

    @Autowired
    private UserDao userDao;


    @Pointcut("execution(public * com.xcy.service..*(..))")
    public void around() {}


    @Around("around()")
    public Object doAround(ProceedingJoinPoint pjp) {

        System.out.println("before");

        UserMongo user = new UserMongo();
		user.setAddress("中南海");
		user.setName("张三");

		final UserMongo insert = userDao.insert(user);

        try {
            final Object[] args = pjp.getArgs();
            return pjp.proceed(args);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("exception throw");
        } finally {
            System.out.println("after");
        }

        return null;

    }




}
