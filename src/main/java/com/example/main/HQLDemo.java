package com.example.main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.Product;
import com.example.util.HibernateUtil;

public class HQLDemo {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();

        insertProducts(session);
        sorting(session);
        pagination(session);
        aggregates(session);
        likeQueries(session);
        updateDelete(session);

        session.close();
        System.out.println("Skill-3 Completed Successfully ✅");
    }

    // INSERT
    static void insertProducts(Session session) {
        Transaction tx = session.beginTransaction();

        session.save(new Product("Laptop", "Electronics", 75000, 5));
        session.save(new Product("Mobile", "Electronics", 20000, 10));
        session.save(new Product("Table", "Furniture", 5000, 20));
        session.save(new Product("Chair", "Furniture", 3000, 15));
        session.save(new Product("Watch", "Accessories", 1500, 30));
        session.save(new Product("Keyboard", "Electronics", 2500, 12));

        tx.commit();
        System.out.println("Products Inserted");
    }

    // SORTING
    static void sorting(Session session) {
        List<Product> list = session.createQuery(
                "from Product order by price desc", Product.class).list();

        list.forEach(p -> System.out.println(p.getName() + " " + p.getPrice()));
    }

    // PAGINATION
    static void pagination(Session session) {
        Query<Product> q = session.createQuery("from Product", Product.class);
        q.setFirstResult(0);
        q.setMaxResults(3);
        q.list().forEach(p -> System.out.println("Page: " + p.getName()));
    }

    // AGGREGATES
    static void aggregates(Session session) {
        Long count = session.createQuery(
                "select count(*) from Product", Long.class).uniqueResult();
        System.out.println("Total Products: " + count);
    }

    // LIKE
    static void likeQueries(Session session) {
        List<Product> list = session.createQuery(
                "from Product where name like 'L%'", Product.class).list();
        list.forEach(p -> System.out.println("LIKE: " + p.getName()));
    }

    // UPDATE & DELETE
    static void updateDelete(Session session) {
        Transaction tx = session.beginTransaction();

        session.createQuery(
                "update Product set price = price + 1000").executeUpdate();

        session.createQuery(
                "delete from Product where quantity = 0").executeUpdate();

        tx.commit();
    }
}