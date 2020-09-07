package ru.abramov.hw3;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;


public class Main3 {
    private static EntityManagerFactory emFactory;

    public static void main(String[] args) {
//        SessionFactory sessionFactory = new Configuration()
//                .buildSessionFactory();
        emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = emFactory.createEntityManager();

        em.getTransaction().begin();
        fillTablesIfEmpty(em);
        em.getTransaction().commit();

        String str = "Ivan";
        List<String> p = allProductsCostomer(em,str);
        System.out.println(str + " = " + p);

        str = "nokia";
        p = allCustomersBayThisProduct(em,str);
        System.out.println(str + " = " + p);

        deleteProductCustomer(em,str,"Boby");

        em.getTransaction().begin();

        em.getTransaction().commit();

        emFactory.close();
    }

    private static void fillTablesIfEmpty(EntityManager em) {
        List<Customer> list = em.createQuery("Select c FROM Customer c", Customer.class).getResultList();
        if(list.isEmpty()) {
            em.persist(new Customer(null, "Vasia"));
            em.persist(new Customer(null, "Iurii"));
            em.persist(new Customer(null, "Ivan"));
            em.persist(new Customer(null, "Boby"));
            list = em.createQuery("Select c FROM Customer c", Customer.class).getResultList();
        }
        fillTableProductsIfEmpty(em,list);
    }

    private static void fillTableProductsIfEmpty(EntityManager em, List<Customer> customers) {
        List<Product> list = em.createQuery("Select p FROM Product p", Product.class).getResultList();
        if(list.isEmpty()) {
            em.persist(new Product(null, "siemens", 100L, customers.get(1)));
            em.persist(new Product(null, "sony", 101L, customers.get(2)));
            em.persist(new Product(null, "samsung", 102L, customers.get(3)));
            em.persist(new Product(null, "nokia", 103L, customers.get(0)));
            em.persist(new Product(null, "LG", 104L, customers.get(1)));
            em.persist(new Product(null, "HTC", 105L, customers.get(2)));
            em.persist(new Product(null, "Texet", 106L, customers.get(3)));
            em.persist(new Product(null, "apple", 107L, customers.get(0)));
            em.persist(new Product(null, "nokia", 103L, customers.get(3)));
        }
    }

    private static List<String> allCustomersBayThisProduct(EntityManager em, String product) {
        em.getTransaction().begin();

        List<Product> products = em.createQuery("FROM Product p WHERE p.name = :product", Product.class)
                .setParameter("product",product)
                .getResultList();

        List<String> customerList = new ArrayList<>();
        for (Product p : products) {
            String customer = em.createQuery("SELECT c.name FROM Customer c WHERE id = :id_customer", String.class)
                    .setParameter("id_customer", p.getCustomer().getId())
                    .getSingleResult();
            customerList.add(customer);
        }

        em.getTransaction().commit();
        return customerList;
    }

    private static void deleteProductCustomer(EntityManager em, String productName, String customerName){
        em.getTransaction().begin();
        Integer id = getIdCustomer(customerName, em);
        Customer customer = em.find(Customer.class,id);
        System.out.println(customer);
        List<Product> products = em.createQuery("FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", productName)
                .getResultList();
        for (Product p : products) {
            if(p.getCustomer().getId()==id){
                System.out.println(p);
                em.remove(p);
            }
        }
        em.getTransaction().commit();
    }

    private static List<String> allProductsCostomer(EntityManager em, String name) {
        em.getTransaction().begin();
        Integer id = getIdCustomer(name, em);

        List<String> productList = em.createQuery("SELECT p.name FROM Product p WHERE customer_id = : id", String.class)
                .setParameter("id", id)
                .getResultList();
        em.getTransaction().commit();

        return productList;
    }

    private static Integer getIdCustomer(String name, EntityManager em) {
        return em.createQuery("SELECT c.id FROM Customer c WHERE name = :name", Integer.class)
                    .setParameter("name", name)
                    .getSingleResult();
    }
}
