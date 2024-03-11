package com.nicholas.model.dao;

import com.nicholas.model.entities.Manufacturer;
import com.nicholas.model.entities.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.nicholas.model.dao.connection.*;

public class SupplierDAO implements DAO<Integer, Supplier> {
    private final String SELECT_ALL = "SELECT * FROM supplier;";
    private final String SELECT_BY_KEY = "SELECT * FROM supplier WHERE id=?;";
    private final String SELECT_BY_NAME = "SELECT * FROM supplier WHERE name=?;";
    private final String SAVE = "insert into supplier (name, manufacturer_id) values (?, ?);";
    private final String UPDATE = "update supplier set name=?, manufacturer_id=? where id=?";
    private final String DELETE = "delete from supplier where id=?";

    private static ManufacturerDAO manufacturerDAO;

    private SupplierDAO() {
    }

    public static SupplierDAO createInstance(ManufacturerDAO newManufacturerDAO) {
        DBconnection.initDBconnection();
        manufacturerDAO = newManufacturerDAO;
        return new SupplierDAO();
    }

    @Override
    public List<Supplier> findAll() {
        try(Statement statement = DBconnection.getDBconnection().createStatement()){
            ResultSet result = statement.executeQuery(SELECT_ALL);
            List<Supplier> suppliers = new ArrayList<>();

            while(result.next()){
                Supplier supplier = new Supplier();
                supplier.setId(result.getInt(1));
                supplier.setName(result.getString(2));

                Manufacturer manufacturer = manufacturerDAO.findByKey(result.getInt(3));

                if(manufacturer!=null){
                    supplier.setManufacturer(manufacturer);
                } else {
                    supplier.setManufacturer(null);
                }

                suppliers.add(supplier);
            }

            if(!suppliers.isEmpty())
                return suppliers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Supplier findByKey(Integer key) {
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_KEY)){
            statement.setInt(1, key);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                Supplier supplier = new Supplier();
                supplier.setId(result.getInt(1));
                supplier.setName(result.getString(2));

                Manufacturer manufacturer = manufacturerDAO.findByKey(result.getInt(3));

                if(manufacturer!=null){
                    supplier.setManufacturer(manufacturer);
                } else {
                    supplier.setManufacturer(null);
                }

                return supplier;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Supplier findByName(String name) {
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_NAME)){
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                Supplier supplier = new Supplier();
                supplier.setId(result.getInt(1));
                supplier.setName(result.getString(2));

                Manufacturer manufacturer = manufacturerDAO.findByKey(result.getInt(3));

                if(manufacturer!=null){
                    supplier.setManufacturer(manufacturer);
                } else {
                    supplier.setManufacturer(null);
                }

                return supplier;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Supplier o) throws SQLException {
        //here we say, that we will need to get back generated id
        PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, o.getName());
        statement.setInt(2, o.getManufacturer().getId());

        int result = statement.executeUpdate();

        if(result>0){
            //setting new id
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
    public Supplier update(Integer key, Supplier o) {
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(UPDATE)){
            statement.setString(1, o.getName());
            statement.setInt(2, o.getManufacturer().getId());
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
        try(PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(DELETE)){
            statement.setInt(1, key);

            int result = statement.executeUpdate();

            System.out.println(result + " rows affected!");

            if(result>0)
                return true;

        } catch (SQLException e) {
//            if(e.getMessage().contains("foreign key")){
//                return false;
//            } else
                throw new RuntimeException(e);
        }
        return false;
    }
}
