package dao.custom.impl;

import entity.Item;
import dao.custom.ItemDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public String CreateItemId() throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT itemCode FROM item ORDER BY itemCode DESC";
        Query query = session.createQuery(hql);
        query.setMaxResults(1);
        List<String> list = query.list();

        transaction.commit();
        session.close();

        if (!list.isEmpty()) {

            int tempId = Integer.
                    parseInt(list.get(0).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "i-00" + tempId;
            } else if (tempId <= 99) {
                return "i-0" + tempId;
            } else {
                return "i-" + tempId;
            }

        } else {
            return "i-001";
        }

    }

    @Override
    public ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "From item ";
        Query query = session.createQuery(hql);
        List<Item> list = query.list();

        transaction.commit();
        session.close();

        ArrayList<String> items = new ArrayList();
        for (Item i: list) {
            items.add(i.getItemCode());
        }
        return items;
    }

    @Override
    public ArrayList<Item> getAllItemDetails() throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM item WHERE qtyOnHand !=0";
        Query query = session.createQuery(hql);
        List<Item> list = query.list();

        ArrayList<Item> itemList =new ArrayList();
        itemList.addAll(list);

        transaction.commit();
        session.close();

        return itemList;
    }

    @Override
    public boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, itemCode);

        int newQty=item.getQtyOnHand()-qty;
        item.setQtyOnHand(newQty);

        session.update(item);

        transaction.commit();
        session.close();

        return true;

    }

    @Override
    public void updateQtyFromManageOrder(String itemCode, int qty) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, itemCode);

        int newQty=item.getQtyOnHand()+qty;
        item.setQtyOnHand(newQty);
        session.update(item);

        transaction.commit();
        session.close();

    }

    @Override
    public boolean add(Item dto) throws SQLException, ClassNotFoundException {
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

        Item item = session.get(Item.class, s);
        session.delete(item);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(Item dto) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(dto.getItemCode(),dto);

        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Item item = session.get(Item.class, s);
        return item;
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM item ";
        Query query = session.createQuery(hql);
        List<Item> list = query.list();


        transaction.commit();
        session.close();

        ArrayList<Item> items = new ArrayList<>();
        items.addAll(list);
        return items;
    }

}
