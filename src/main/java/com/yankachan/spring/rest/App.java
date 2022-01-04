package com.yankachan.spring.rest;

import com.yankachan.spring.rest.configuration.MyConfig;
import com.yankachan.spring.rest.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        communication.saveUser(new User((long) 3, "James", "Brown", (byte) 25));
        communication.editUser(new User((long) 3, "Thomas", "Shelby", (byte) 25));
        communication.deleteUser((long) 3);

    }
}
