package dao.custom;

import dto.OrderDetailDTO;
import entity.OrderDetail;
import dao.CrudDAO;
import dto.CustomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetail,String> {

    ArrayList<OrderDetailDTO> getOrderDetail(String id) throws SQLException, ClassNotFoundException;

    boolean removeItemFromOrder(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> findMovableItem() throws SQLException, ClassNotFoundException;

    double getSumOfTotal(String id) throws SQLException, ClassNotFoundException;



}
