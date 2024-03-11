package com.nicholas.view;

import com.nicholas.model.dao.ManufacturerDAO;
import com.nicholas.model.dao.SupplierDAO;
import com.nicholas.model.entities.Manufacturer;
import com.nicholas.model.entities.Supplier;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SupplierAddDialog extends AbstractAddDialogue{
    private ManufacturerDAO manufacturerDAO;
    private SupplierDAO supplierDAO;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel manufacturerLabel;
    private JComboBox<Object> manufacturers;
    public SupplierAddDialog(Frame owner, String title) {
        super(owner, title);

        manufacturerDAO = ManufacturerDAO.createInstance();
        supplierDAO = SupplierDAO.createInstance(manufacturerDAO);

        JPanel input = getInput();
        input.setLayout(new GridLayout(4, 1));
        nameLabel = new JLabel("Enter name:");
        name = new JTextField(20);
        input.add(nameLabel);
        input.add(name);

        manufacturerLabel = new JLabel("Select manufacturer:");
        List<Manufacturer> manufacturersList = manufacturerDAO.findAll();
        manufacturers = new JComboBox<>(manufacturersList.stream().map(m -> m.getName()).toArray());
        input.add(manufacturerLabel);
        input.add(manufacturers);

        getSave().addActionListener((e)->{
            try {
                getError().setVisible(false);
                Supplier supplier = new Supplier(null, name.getText(), manufacturerDAO.findByName(manufacturers.getSelectedItem().toString()));
                supplierDAO.save(supplier);
            } catch (SQLException ex) {
                getError().setText("Such supplier already exists!");
                getError().setVisible(true);
                throw new RuntimeException(ex);
            }
        });

        setDialogDimension(new Dimension(250, 250));
        setVisible(true);
    }
}
