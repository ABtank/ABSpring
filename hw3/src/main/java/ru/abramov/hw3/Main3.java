package ru.abramov.hw3;

import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
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

        em.getTransaction().begin();
        Customer customer = em.find(Customer.class, 1);
        Product product = em.find(Product.class, 1);
        OrderItem orderItem = new OrderItem(null,product,customer,product.getPrice(),1);
        em.persist(orderItem);
        em.getTransaction().commit();
//
//        String str = "Ivan";
//        List<String> p = allProductsCustomer(em, str);
//        System.out.println(str + " = " + p);
//
//        str = "nokia";
//        p = allCustomersBayThisProduct(em, str);
//        System.out.println(str + " = " + p);
//
//        deleteProductCustomer(em, str, "Boby");
//
//        em.getTransaction().begin();
//
//        em.getTransaction().commit();

        emFactory.close();
    }

    private static void fillTablesIfEmpty(EntityManager em) {
        List<Customer> list = em.createQuery("Select c FROM Customer c", Customer.class).getResultList();
        if (list.isEmpty()) {
            em.persist(new Customer(null, "Vasia"));
            em.persist(new Customer(null, "Iurii"));
            em.persist(new Customer(null, "Ivan"));
            em.persist(new Customer(null, "Boby"));
            list = em.createQuery("Select c FROM Customer c", Customer.class).getResultList();
        }
        fillTableProductsIfEmpty(em, list);
    }

    private static void fillTableProductsIfEmpty(EntityManager em, List<Customer> customers) {
        List<Product> products = em.createQuery("Select p FROM Product p", Product.class).getResultList();
        if (products.isEmpty()) {
            em.persist(new Product(null, "siemens", new BigDecimal(100)));
            em.persist(new Product(null, "sony", new BigDecimal(101)));
            em.persist(new Product(null, "samsung", new BigDecimal(102)));
            em.persist(new Product(null, "nokia", new BigDecimal(103)));
            em.persist(new Product(null, "LG", new BigDecimal(104)));
            em.persist(new Product(null, "HTC", new BigDecimal(105)));
            em.persist(new Product(null, "Texet", new BigDecimal(106)));
            em.persist(new Product(null, "apple", new BigDecimal(107)));
            em.persist(new Product(null, "nokia", new BigDecimal(103)));
            products = em.createQuery("Select p FROM Product p", Product.class).getResultList();
        }
//        fillTableOrderItem(em, customers, products);
    }

    private static void fillTableOrderItem(EntityManager em, List<Customer> customers, List<Product> products) {
        em.persist(new OrderItem(null, products.get(0), customers.get(0), products.get(0).getPrice(), 1));
        em.persist(new OrderItem(null, products.get(0), customers.get(1), products.get(0).getPrice(), 1));
        em.persist(new OrderItem(null, products.get(1), customers.get(2), products.get(0).getPrice(), 1));
        em.persist(new OrderItem(null, products.get(1), customers.get(3), products.get(0).getPrice(), 1));
        em.persist(new OrderItem(null, products.get(2), customers.get(4), products.get(0).getPrice(), 1));
        em.persist(new OrderItem(null, products.get(2), customers.get(5), products.get(0).getPrice(), 1));
        em.persist(new OrderItem(null, products.get(3), customers.get(6), products.get(0).getPrice(), 1));
        em.persist(new OrderItem(null, products.get(3), customers.get(7), products.get(0).getPrice(), 1));
    }

//    private static List<String> allCustomersBayThisProduct(EntityManager em, String product) {
//        em.getTransaction().begin();
//
//        List<Product> products = em.createQuery("FROM Product p WHERE p.name = :product", Product.class)
//                .setParameter("product", product)
//                .getResultList();
//
//        List<String> customerList = new ArrayList<>();
//        for (Product p : products) {
//            String customer = em.createQuery("SELECT c.name FROM Customer c WHERE id = :id_customer", String.class)
//                    .setParameter("id_customer", p.getCustomer().getId())
//                    .getSingleResult();
//            customerList.add(customer);
//        }
//
//        em.getTransaction().commit();
//        return customerList;
//    }
//
//    private static void deleteProductCustomer(EntityManager em, String productName, String customerName) {
//        em.getTransaction().begin();
//        Integer id = getIdCustomer(customerName, em);
//        Customer customer = em.find(Customer.class, id);
//        System.out.println(customer);
//        List<Product> products = em.createQuery("FROM Product p WHERE p.name = :name", Product.class)
//                .setParameter("name", productName)
//                .getResultList();
//        for (Product p : products) {
//            if (p.getCustomer().getId() == id) {
//                System.out.println(p);
//                em.remove(p);
//            }
//        }
//        em.getTransaction().commit();
//    }

    private static List<String> allProductsCustomer(EntityManager em, String name) {
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
