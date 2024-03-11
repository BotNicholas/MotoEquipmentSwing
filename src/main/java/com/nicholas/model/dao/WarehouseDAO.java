package com.nicholas.model.dao;

import com.nicholas.model.dao.connection.DBconnection;
import com.nicholas.model.entities.Equipment;
import com.nicholas.model.entities.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO implements DAO<Integer, Warehouse>{
    private final String SELECT_ALL = "SELECT * FROM warehouse;";
    private final String SELECT_ALL_FROM_WAREHOUSE_EQUIPMENT = "select * from warehouse_equipment where warehouse_id = ?;";
    private final String SELECT_BY_KEY = "SELECT * FROM warehouse WHERE id=?;";
    private final String SELECT_BY_NAME = "SELECT * FROM warehouse WHERE name=?;";
    private final String SELECT_WAREHOUSE_FOR_EQUIPMENT = "select warehouse_id from warehouse_equipment where equipment_id = ?";
    private final String SAVE = "insert into warehouse (name, address) values (?, ?);";
    private final String ADD_TO_WAREHOUSE = "insert into warehouse_equipment (equipment_id, warehouse_id) values (?, ?)";
    private final String UPDATE_FOR_EQUIPMENT = "update warehouse_equipment set warehouse_id=? where equipment_id=? and warehouse_id=?;";
    private final String UPDATE = "update warehouse set name=?, address=? where id=?";
    private final String DELETE = "delete from warehouse where id=?";
    private final String DELETE_FROM_WAREHOUSE_EQUIPMENT = "delete from warehouse_equipment where warehouse_id=?";
    private static EquipmentDAO equipmentDAO;

    private WarehouseDAO(){}

    public static WarehouseDAO createInstance(EquipmentDAO newEquipmentDAO){
        DBconnection.initDBconnection();
        equipmentDAO = newEquipmentDAO;
        return new WarehouseDAO();
    }
    @Override
    public List<Warehouse> findAll() {
        PreparedStatement statement;
        try{
            statement = DBconnection.getDBconnection().prepareStatement(SELECT_ALL);
            List<Warehouse> warehouses = new ArrayList<>();
            ResultSet result = statement.executeQuery();

            while(result.next()){
                Warehouse warehouse = new Warehouse(result.getInt(1), result.getString(2), result.getString(3));
                statement = DBconnection.getDBconnection().prepareStatement(SELECT_ALL_FROM_WAREHOUSE_EQUIPMENT);
                statement.setInt(1, warehouse.getId());

                ResultSet equipmentResult = statement.executeQuery();

                while(equipmentResult.next()){
                    warehouse.addEquipment(equipmentDAO.findByKey(equipmentResult.getInt(1)));
                }

                warehouses.add(warehouse);
            }

            statement.close();

            if(!warehouses.isEmpty()){
                return warehouses;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Warehouse findByKey(Integer key) {
        PreparedStatement statement;
        try{
            statement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_KEY);
            statement.setInt(1, key);
            ResultSet result = statement.executeQuery();
            Warehouse warehouse = null;

            if(result.next()){
                warehouse = new Warehouse(result.getInt(1), result.getString(2), result.getString(3));
                statement = DBconnection.getDBconnection().prepareStatement(SELECT_ALL_FROM_WAREHOUSE_EQUIPMENT);
                statement.setInt(1, warehouse.getId());

                ResultSet equipmentResult = statement.executeQuery();

                while(equipmentResult.next()){
                    warehouse.addEquipment(equipmentDAO.findByKey(equipmentResult.getInt(1)));
                }
            }

            statement.close();

            if(warehouse!=null){
                return warehouse;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Warehouse findByName(String name) {
        PreparedStatement statement;
        try{
            statement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_NAME);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            Warehouse warehouse = null;

            if(result.next()){
                warehouse = new Warehouse(result.getInt(1), result.getString(2), result.getString(3));
                statement = DBconnection.getDBconnection().prepareStatement(SELECT_ALL_FROM_WAREHOUSE_EQUIPMENT);
                statement.setInt(1, warehouse.getId());

                ResultSet equipmentResult = statement.executeQuery();

                while(equipmentResult.next()){
                    warehouse.addEquipment(equipmentDAO.findByKey(equipmentResult.getInt(1)));
                }
            }

            statement.close();

            if(warehouse!=null){
                return warehouse;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Warehouse o) throws SQLException {
        PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, o.getName());
        statement.setString(2, o.getAddress()
        );

        int result = statement.executeUpdate();

        if(result>0){
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                o.setId(generatedKeys.getInt(1));
            }
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
    public Warehouse update(Integer key, Warehouse o) {
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(UPDATE)){
            statement.setString(1, o.getName());
            statement.setString(2, o.getAddress());
            statement.setInt(3, key);

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
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(DELETE_FROM_WAREHOUSE_EQUIPMENT)){
            statement.setInt(1, key);

            int result = statement.executeUpdate();
            PreparedStatement warehouseStatement = DBconnection.getDBconnection().prepareStatement(DELETE);
            warehouseStatement.setInt(1, key);

            result += warehouseStatement.executeUpdate();

            if(result>0) {
                System.out.println(result + " rows affected!");
                return true;
            }

        } catch (SQLException e) {
            if(e.getMessage().contains("foreign key")){
                return false;
            } else
                throw new RuntimeException(e);
        }
        return false;
    }

    public boolean addToWarehouse(Equipment equipment, Warehouse warehouse){
        if(warehouse!=null) {
            System.out.println(equipment.getId() + " " + warehouse.getId());
            try (PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(ADD_TO_WAREHOUSE)) {
                statement.setInt(1, equipment.getId());
                statement.setInt(2, warehouse.getId());

                int result = statement.executeUpdate();

                if (result > 0) {
                    System.out.println(result + " rows affected!");
                    return true;
                } else {
                    System.err.println("Something went wrong!");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean updateForEquipment(Equipment equipment, Warehouse warehouse){
        if(getWarehouseForEquipment(equipment).getId()!=null) {
            try (PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(UPDATE_FOR_EQUIPMENT)) {
                statement.setInt(1, warehouse.getId());
                statement.setInt(2, equipment.getId());
                statement.setInt(3, getWarehouseForEquipment(equipment).getId());

                int result = statement.executeUpdate();

                if (result > 0) {
                    System.out.println(result + " rows affected!");
                    return true;
                } else {
                    System.err.println("Something went wrong!");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            return addToWarehouse(equipment, warehouse);
        }

    }

    public Warehouse getWarehouseForEquipment(Equipment equipment){
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(SELECT_WAREHOUSE_FOR_EQUIPMENT)){
            statement.setInt(1, equipment.getId());
            ResultSet result = statement.executeQuery();

            if(result.next()){
                return findByKey(result.getInt(1));
            } else {
                return new Warehouse();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
