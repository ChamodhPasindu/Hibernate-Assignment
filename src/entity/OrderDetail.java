package entity;

import javax.persistence.*;

@Entity(name = "orderDetail")
public class OrderDetail {

    @Id
    @Column(name = "oder_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Orders orders;

    private int orderQty;
    private double discount;
    private double cost;

    public OrderDetail() {
    }

    public OrderDetail(int id, Item item, Orders orders, int orderQty, double discount, double cost) {
        this.id = id;
        this.item = item;
        this.orders = orders;
        this.orderQty = orderQty;
        this.discount = discount;
        this.cost = cost;
    }

    public OrderDetail(Item item, Orders orders, int orderQty, double discount, double cost) {
        this.item = item;
        this.orders = orders;
        this.orderQty = orderQty;
        this.discount = discount;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + id + '\'' +
                ", item=" + item +
                ", orders=" + orders +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", cost=" + cost +
                '}';
    }
}
