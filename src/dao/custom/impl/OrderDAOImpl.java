package dao.custom.impl;

import entity.Item;
import entity.OrderDetail;
import entity.Orders;
import dao.custom.OrderDAO;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;
import view.tm.CartTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public String CreateOrderId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT orderId FROM orders ORDER BY orderId DESC";
        Query query = session.createQuery(hql);
        query.setMaxResults(1);
        List<String> list = query.list();

        transaction.commit();
        session.close();


        if (!list.isEmpty()){

            int tempId = Integer.
                    parseInt(list.get(0).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "o-00" + tempId;
            } else if (tempId <= 99) {
                return "o-0" + tempId;
            } else {
                return "o-" + tempId;
            }

        } else {
            return "o-001";
        }

    }

    @Override
    public void updateOrder(String id, ObservableList<CartTM> cartTMArrayList) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT id FROM orderDetail WHERE orders.orderId=:order_id";
        Query query = session.createQuery(hql);
        query.setParameter("order_id",id);
        List<Integer> list = query.list();

        for (int s:list) {
            OrderDetail orderDetail = session.get(OrderDetail.class, s);
            session.delete(orderDetail);
        }

        for (CartTM tm : cartTMArrayList){

            Item item = session.get(Item.class, tm.getItemCode());
            Orders orders = session.get(Orders.class, id);

            OrderDetail orderDetail = new OrderDetail(item, orders, tm.getQty(), tm.getDiscount(), tm.getTotal());
            session.save(orderDetail);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public ArrayList<String> getOrderId(String id) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT orderId FROM orders WHERE customer.custId =:customer_id";
        Query query = session.createQuery(hql);
        query.setParameter("customer_id",id);
        List<String> list = query.list();

        transaction.commit();
        session.close();

        ArrayList<String> orderId = new ArrayList<>();
        orderId.addAll(list);
        return orderId;

    }

    @Override
    public ArrayList<String> getDate(String from, String to) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT orderId FROM orders  WHERE date BETWEEN :from AND :to";
        Query query = session.createQuery(hql);
        query.setParameter("from", LocalDate.parse(from));
        query.setParameter("to",LocalDate.parse(to));
        List<String> list = query.list();

        transaction.commit();;
        session.close();

        ArrayList<String>orderList=new ArrayList<>();
        orderList.addAll(list);
        return orderList;

    }

    @Override
    public boolean add(Orders dto) throws SQLException, ClassNotFoundException {
         Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(dto);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Orders orders = session.get(Orders.class, s);
        session.delete(orders);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Orders DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
