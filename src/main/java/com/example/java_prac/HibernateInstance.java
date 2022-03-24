package com.example.java_prac;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateInstance {
    private static final SessionFactory session_factory;
    static {
        try{
            StandardServiceRegistry standard_registry =
                    new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metadata =
                    new MetadataSources(standard_registry).getMetadataBuilder().build();
            session_factory = metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("SessionFactory init failed - " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return session_factory;
    }
}
