package com.nicholas.view;

import com.nicholas.model.dao.EquipmentDAO;
import com.nicholas.model.dao.ManufacturerDAO;
import com.nicholas.model.dao.SupplierDAO;
import com.nicholas.model.dao.WarehouseDAO;
import com.nicholas.model.entities.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class WarehouseAddDialog extends AbstractAddDialogue{
    private ManufacturerDAO manufacturerDAO;
    private SupplierDAO supplierDAO;
    private EquipmentDAO equipmentDAO;
    private WarehouseDAO warehouseDAO;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel addressLabel;
    private JTextField address;

    public WarehouseAddDialog(Frame owner, String title) {
        super(owner, title);

        manufacturerDAO = ManufacturerDAO.createInstance();
        supplierDAO = SupplierDAO.createInstance(manufacturerDAO);
        equipmentDAO = EquipmentDAO.createInstance(manufacturerDAO, supplierDAO);
        warehouseDAO = WarehouseDAO.createInstance(equipmentDAO);

        JPanel input = getInput();
        input.setLayout(new GridLayout(4, 1));

        nameLabel = new JLabel("Enter name: ");
        name = new JTextField(20);
        input.add(nameLabel);
        input.add(name);

        addressLabel = new JLabel("Enter address: ");
        address = new JTextField(20);
        input.add(addressLabel);
        input.add(address);

        setDialogDimension(new Dimension(250, 250));

        getSave().addActionListener((e)->{
            try {
                getError().setVisible(false);
                Warehouse warehouse = new Warehouse(null, name.getText(), address.getText());

                warehouseDAO.save(warehouse);
            } catch (SQLException ex) {

                getError().setText("Such warehouse already exists!");
                getError().setVisible(true);
                throw new RuntimeException(ex);
            }
        });

        showDialogue(true);
    }
}
