package com.nicholas.model.dao;

import com.nicholas.model.dao.connection.DBconnection;
import com.nicholas.model.entities.Manufacturer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ManufacturerDAO implements DAO<Integer, Manufacturer>{
    private final String SELECT_ALL = "SELECT id, name, address, phone FROM manufacturer;";
    private final String SELECT_BY_KEY = "SELECT id, name, address, phone FROM manufacturer WHERE id=?;";
    private final String SELECT_BY_NAME = "SELECT id, name, address, phone FROM manufacturer WHERE name=?;";
    private final String SAVE = "INSERT INTO manufacturer (name, address, phone) VALUES (?, ?, ?);";
    private final String UPDATE = "UPDATE manufacturer SET name=?, address=?, phone=? WHERE id=?;";
    private final String DELETE = "DELETE FROM manufacturer WHERE id=?;";


    private ManufacturerDAO(){}

    public static ManufacturerDAO createInstance(){
        DBconnection.initDBconnection();
        return new ManufacturerDAO();
    }

    @Override
    public List<Manufacturer> findAll() {
        try {
            PreparedStatement preparedStatement = DBconnection.getDBconnection().prepareStatement(SELECT_ALL);
            ResultSet result = preparedStatement.executeQuery();
            List<Manufacturer> manufacturers = new ArrayList<>();

            while(result.next()){
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(result.getInt(1));
                manufacturer.setName(result.getString(2));
                manufacturer.setAddress(result.getString(3));
                manufacturer.setPhone(result.getString(4));
                manufacturers.add(manufacturer);
            }

            result.close();
            preparedStatement.close();

            if(!manufacturers.isEmpty())
                return manufacturers;
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Manufacturer findByKey(Integer key) {
        try {
            PreparedStatement preparedStatement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_KEY);
            preparedStatement.setInt(1, key);
            ResultSet result = preparedStatement.executeQuery();

            Manufacturer manufacturer = new Manufacturer();
            if(result.next()){
                manufacturer.setId(result.getInt(1));
                manufacturer.setName(result.getString(2));
                manufacturer.setAddress(result.getString(3));
                manufacturer.setPhone(result.getString(4));

            }

            result.close();
            preparedStatement.close();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Manufacturer findByName(String name) throws SQLException {
        try {
            PreparedStatement statement = DBconnection.getDBconnection().prepareStatement(SELECT_BY_NAME);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            Manufacturer manufacturer = new Manufacturer();
            if (resultSet.next()){
                manufacturer.setId(resultSet.getInt(1));
                manufacturer.setName(resultSet.getString(2));
                manufacturer.setAddress(resultSet.getString(3));
                manufacturer.setPhone(resultSet.getString(4));
            }
            resultSet.close();
            statement.close();
            return manufacturer;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(Manufacturer obj) throws SQLException {
            PreparedStatement preparedStatement = DBconnection.getDBconnection().prepareStatement("ALTER TABLE manufacturer AUTO_INCREMENT=1;");
            preparedStatement.executeUpdate();

            preparedStatement = DBconnection.getDBconnection().prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setString(2, obj.getAddress());
            preparedStatement.setString(3, obj.getPhone());

            if(preparedStatement.executeUpdate()>0){

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if(generatedKeys.next())
                    obj.setId(generatedKeys.getInt(1));


                preparedStatement.close();
                return true;
            }
            preparedStatement.close();
            return false;
    }

    @Override
    public Manufacturer update(Integer key, Manufacturer newObj) {
        try {
            PreparedStatement preparedStatement = DBconnection.getDBconnection().prepareStatement(UPDATE);
            preparedStatement.setString(1, newObj.getName());
            preparedStatement.setString(2, newObj.getAddress());
            preparedStatement.setString(3, newObj.getPhone());
            preparedStatement.setInt(4, key);

            if (preparedStatement.executeUpdate() > 0){
                preparedStatement.close();
                return newObj;
            }
            preparedStatement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer key) {
        try {
            PreparedStatement preparedStatement = DBconnection.getDBconnection().prepareStatement(DELETE);
            preparedStatement.setInt(1, key);

            boolean result = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
