package com.example.main;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.entity.Product;
import com.example.util.HibernateUtil;

public class ProductApp {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        String productName = "Laptop";

        // 🔍 Check if product exists
        Query<Product> query = session.createQuery(
                "from Product where name = :name", Product.class);
        query.setParameter("name", productName);

        List<Product> productList = query.list();

        if (productList.isEmpty()) {

            Product product = new Product(
                    "Laptop",
                    "Gaming Laptop",
                    75000,
                    5
            );

            session.persist(product);
            System.out.println("✅ Product inserted successfully!");

        } else {
            System.out.println("❌ Product already exists.");
        }

        // 🔥 DISPLAY ALL PRODUCTS
        List<Product> allProducts = session.createQuery(
                "from Product", Product.class).list();

        System.out.println("\n----- PRODUCT LIST -----");

        for (Product p : allProducts) {
            System.out.println("ID: " + p.getId());
            System.out.println("Name: " + p.getName());
            System.out.println("Description: " + p.getDescription());
            System.out.println("Price: " + p.getPrice());
            System.out.println("Quantity: " + p.getQuantity());
            System.out.println("-------------------------");
        }

        tx.commit();
        session.close();
        HibernateUtil.getSessionFactory().close();
    }
}