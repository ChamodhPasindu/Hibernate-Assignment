package dao.custom.impl;

import dto.OrderDetailDTO;
import entity.OrderDetail;
import dao.custom.OrderDetailsDAO;
import dto.CustomDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public boolean add(OrderDetail dto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(dto);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM orderDetail WHERE orders.orderId =:order_id";
        Query query = session.createQuery(hql);
        query.setParameter("order_id",id);
        List<OrderDetail> list = query.list();

        transaction.commit();
        session.close();

        ArrayList<OrderDetailDTO> orderDetailList = new ArrayList<>();
        for (OrderDetail od:list) {
            orderDetailList.add(new OrderDetailDTO(od.getItem().getItemCode(),od.getOrderQty(),od.getDiscount(),od.getCost()));
        }
        return orderDetailList;
    }

    @Override
    public boolean removeItemFromOrder(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(id);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public ArrayList<CustomDTO> findMovableItem() throws SQLException, ClassNotFoundException {


        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT item.itemCode,count(*) FROM orderDetail GROUP BY item.itemCode ORDER BY 1";
        Query query = session.createQuery(hql);
        List<Object[]> list = query.list();

        transaction.commit();
        session.close();

        ArrayList<CustomDTO>customDTOS = new ArrayList<>();
        int i=0;
        for (Object[] o:list) {
            customDTOS.add(new CustomDTO(o[i].toString(),Integer.parseInt(o[i+1].toString())));
            i++;
        }

        return customDTOS;
    }

    @Override
    public double getSumOfTotal(String id) throws SQLException, ClassNotFoundException {

        double i=0.0;

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT SUM(cost) FROM orderDetail WHERE orders.orderId =:order_id";
        Query query = session.createQuery(hql);
        query.setParameter("order_id",id);
        List<Double> list = query.list();

        if (!list.isEmpty()){
            i=list.get(0);
        }

        transaction.commit();
        session.close();

        return i;
    }
}
