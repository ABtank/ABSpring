package ru.abramov.hw3;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Customer customer;

    @Column
    private BigDecimal price;

    @Column
    private Integer count;

    public OrderItem() {
    }

    public OrderItem(Integer id, Product product, Customer customer, BigDecimal price, Integer count) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.price = price;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
