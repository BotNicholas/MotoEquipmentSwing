package com.nicholas.model.dao;

import com.nicholas.model.dao.connection.DBconnection;
import com.nicholas.model.entities.Equipment;
import com.nicholas.model.entities.EquipmentType;
import com.nicholas.model.entities.Manufacturer;
import com.nicholas.model.entities.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EquipmentDAO implements DAO<Integer, Equipment> {
    private final String SELECT_ALL = "SELECT * FROM equipment;";
    private final String SELECT_BY_KEY = "SELECT * FROM equipment WHERE id=?;";
    private final String SELECT_BY_NAME = "SELECT * FROM equipment WHERE name=?;";
    private final String SELECT_ALL_LIKE_NAME = "SELECT * FROM equipment WHERE name like ?;";
    private final String SAVE = "insert into equipment (name, type, price, size, weight, manufacturer_id, supplier_id) values (?, ?, ?, ?, ?, ?, ?);";
    private final String UPDATE = "update equipment set name=?, type=?, price=?, size=?, weight=?, manufacturer_id=?, supplier_id=? where id=?;";
    private final String DELETE = "delete from equipment where id=?;";
    private static SupplierDAO supplierDAO;

    private static ManufacturerDAO manufacturerDAO;

    private EquipmentDAO(){}

    public static EquipmentDAO createInstance(ManufacturerDAO newManufacturerDAO, SupplierDAO newSupplierDAO){
        DBconnection.initDBconnection();
        manufacturerDAO = newManufacturerDAO;
        supplierDAO = newSupplierDAO;
        return new EquipmentDAO();
    }

    @Override
    public List<Equipment> findAll() {
        PreparedStatement statement;
        try{
            statement = DBconnection.getDBconnection().prepareStatement(SELECT_ALL);
            ResultSet result = statement.executeQuery();
            List<Equipment> equipments = new ArrayList<>();

            while(result.next()){
                Equipment equipment = new Equipment(result.getInt(1), result.getString(2), EquipmentType.valueOf(result.getString(3)), result.getInt(4), result.getString(5), result.getInt(6));

                Manufacturer manufacturer = manufacturerDAO.findByKey(result.getInt(7));
                if(manufacturer!=null){
                    equipment.setManufacturer(manufacturer);
                } else {
                    equipment.setManufacturer(null);
                }

                Supplier supplier = supplierDAO.findByKey(result.getInt(8));
                if(supplier!=null){
                    equipment.setSupplier(supplier);
                } else {
                    equipment.setSupplier(null);
                }

                equipments.add(equipment);
            }

            statement.close();

            if(!equipments.isEmpty())
                return equipments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Equipment findByKey(Integer key) {
        PreparedStatement statement;
        try{
            statement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_KEY);
            statement.setInt(1, key);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                Equipment equipment = new Equipment(result.getInt(1), result.getString(2), EquipmentType.valueOf(result.getString(3)), result.getInt(4), result.getString(5), result.getInt(6));

                Manufacturer manufacturer = manufacturerDAO.findByKey(result.getInt(7));
                if(manufacturer!=null){
                    equipment.setManufacturer(manufacturer);
                } else {
                    equipment.setManufacturer(null);
                }

                Supplier supplier = supplierDAO.findByKey(result.getInt(8));
                if(supplier!=null){
                    equipment.setSupplier(supplier);
                } else {
                    equipment.setSupplier(null);
                }

                return equipment;
            }

            statement.close();

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Equipment> findAllByName(String name) {
        PreparedStatement statement;
        try{
            statement = DBconnection.getDBconnection().prepareStatement(SELECT_ALL_LIKE_NAME);
            statement.setString(1, name+"%");
            ResultSet result = statement.executeQuery();
            List<Equipment> equipments = new ArrayList<>();

            while(result.next()){
                Equipment equipment = new Equipment(result.getInt(1), result.getString(2), EquipmentType.valueOf(result.getString(3)), result.getInt(4), result.getString(5), result.getInt(6));

                Manufacturer manufacturer = manufacturerDAO.findByKey(result.getInt(7));
                if(manufacturer!=null){
                    equipment.setManufacturer(manufacturer);
                } else {
                    equipment.setManufacturer(null);
                }

                Supplier supplier = supplierDAO.findByKey(result.getInt(8));
                if(supplier!=null){
                    equipment.setSupplier(supplier);
                } else {
                    equipment.setSupplier(null);
                }

                equipments.add(equipment);
            }

            statement.close();

            if(!equipments.isEmpty())
                return equipments;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Collections.emptyList();
    }

    public Equipment findByName(String name) {
        PreparedStatement statement;
        try{
            statement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_NAME);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                Equipment equipment = new Equipment(result.getInt(1), result.getString(2), EquipmentType.valueOf(result.getString(3)), result.getInt(4), result.getString(5), result.getInt(6));

                Manufacturer manufacturer = manufacturerDAO.findByKey(result.getInt(7));
                if(manufacturer!=null){
                    equipment.setManufacturer(manufacturer);
                } else {
                    equipment.setManufacturer(null);
                }

                Supplier supplier = supplierDAO.findByKey(result.getInt(8));
                if(supplier!=null){
                    equipment.setSupplier(supplier);
                } else {
                    equipment.setSupplier(null);
                }

                return equipment;
            }

            statement.close();

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Equipment o) throws SQLException {
        //Сбрасываю автоинкремент
        try(Statement statement = DBconnection.getDBconnection().createStatement()){
            statement.executeUpdate("alter table equipment AUTO_INCREMENT=1;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, o.getName());
        statement.setString(2, o.getType().toString());
        statement.setInt(3, o.getPrice());
        statement.setString(4, o.getSize());
        statement.setInt(5, o.getWeight());
        statement.setInt(6, o.getManufacturer().getId());
        statement.setInt(7, o.getSupplier().getId());

        int result = statement.executeUpdate();

        if(result>0){
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next())
                o.setId(generatedKeys.getInt(1));
            statement.close();
            System.out.println(result + " rows affected!");
            return true;
        } else {
            statement.close();
            System.err.println("Something went wrong!");
            return false;
        }
    }

    @Override
    public Equipment update(Integer key, Equipment o) {
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(UPDATE)){
            statement.setString(1, o.getName());
            statement.setString(2, o.getType().toString());
            statement.setInt(3, o.getPrice());
            statement.setString(4, o.getSize());
            statement.setInt(5, o.getWeight());
            statement.setInt(6, o.getManufacturer().getId());
            statement.setInt(7, o.getSupplier().getId());
            statement.setInt(8, o.getId());

            int result = statement.executeUpdate();


            System.out.println(result + " rows affected");

            if (statement.executeUpdate() > 0){
                statement.close();
                return o;
            }
            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer key) {
        String sql="delete from warehouse_equipment where equipment_id=?";
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(sql)){
            statement.setInt(1, key);

            int result = statement.executeUpdate();
//            System.out.println(statement.toString() + " : " + result);

//            if(result>0){
                PreparedStatement EqStatement = DBconnection.getDBconnection().prepareStatement(DELETE);
                EqStatement.setInt(1, key);
                result = EqStatement.executeUpdate();

                if(result>0)
                    return true;
//            }
        } catch (SQLException e) {
            if(e.getMessage().contains("foreign key")){
//                System.out.println("FOREIGN KEY");
                return false;
            } else {
                throw new RuntimeException(e);
            }
        }

        return false;
    }

    public int getLastID(){
        try(Statement statement = DBconnection.getDBconnection().createStatement()){
            statement.executeUpdate("alter table equipment AUTO_INCREMENT=1;");

            ResultSet result = statement.executeQuery("select max(id) from equipment;");

            if(result.next()){
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
