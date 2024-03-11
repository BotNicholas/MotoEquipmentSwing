package com.nicholas.view;

import com.nicholas.model.dao.EquipmentDAO;
import com.nicholas.model.dao.ManufacturerDAO;
import com.nicholas.model.dao.SupplierDAO;
import com.nicholas.model.dao.WarehouseDAO;
import com.nicholas.model.entities.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class EquipmentAddDialog extends AbstractAddDialogue{
    private ManufacturerDAO manufacturerDAO;
    private SupplierDAO supplierDAO;
    private EquipmentDAO equipmentDAO;
    private WarehouseDAO warehouseDAO;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel typeLabel;
    private JComboBox<Object> type;
    private JLabel priceLabel;
    private JTextField price;
    private JLabel sizeLabel;
    private JComboBox<Object> size;
    private JLabel weightLabel;
    private JTextField weight;
    private JLabel manufacturerLabel;
    private JComboBox<Object> manufacturer;
    private JLabel supplierLabel;
    private JComboBox<Object> supplier;
    private JLabel warehousesLabel;
    private JComboBox<Object> warehouses;
    public EquipmentAddDialog(Frame owner, String title) {
        super(owner, title);

        manufacturerDAO = ManufacturerDAO.createInstance();
        supplierDAO = SupplierDAO.createInstance(manufacturerDAO);
        equipmentDAO = EquipmentDAO.createInstance(manufacturerDAO, supplierDAO);
        warehouseDAO = WarehouseDAO.createInstance(equipmentDAO);

        JPanel input = getInput();
        input.setLayout(new GridLayout(16, 1));
        nameLabel = new JLabel("Enter Name:");
        name = new JTextField(20);
        input.add(nameLabel);
        input.add(name);

        typeLabel = new JLabel("Select type: ");
        type = new JComboBox<>(new Object[]{"HELMET", "JACKET", "PANTS", "GLOVES", "SHOES", "BODY_ARMOR", "GLASSES", "ACCESSORY"});
        input.add(typeLabel);
        input.add(type);


        priceLabel = new JLabel("Enter price:");
        price = new JTextField(20);
        input.add(priceLabel);
        input.add(price);
        price.addKeyListener(new OnlyNumbersListener());

        sizeLabel = new JLabel("Select size:");
        size = new JComboBox<>(new Object[]{"M", "L", "S", "X", "XL", "XS", "XXL"});
        input.add(sizeLabel);
        input.add(size);

        weightLabel = new JLabel("Enter weight: ");
        weight = new JTextField(20);
        input.add(weightLabel);
        input.add(weight);
        weight.addKeyListener(new OnlyNumbersListener());

        manufacturerLabel = new JLabel("Select manufacturer: ");
        List<Manufacturer> manufacturerList = manufacturerDAO.findAll();
        manufacturer = new JComboBox<>(manufacturerList.stream().map(m->m.getName()).toArray());
        input.add(manufacturerLabel);
        input.add(manufacturer);

        supplierLabel = new JLabel("Select supplier:");
        List<Supplier> supplierList = supplierDAO.findAll();
        supplier = new JComboBox<>(supplierList.stream().map(s->s.getName()).toArray());
        input.add(supplierLabel);
        input.add(supplier);

        warehousesLabel = new JLabel("Select warehouse:");
        List<Warehouse> warehouseList = warehouseDAO.findAll();
        warehouses = new JComboBox<>(warehouseList.stream().map(w->w.getName()).toArray());
        input.add(warehousesLabel);
        input.add(warehouses);

        setDialogDimension(new Dimension(250, 550));

        getSave().addActionListener((e)->{
            getError().setVisible(false);
            try {
                Equipment equipment = new Equipment(null, name.getText(), EquipmentType.valueOf(type.getSelectedItem().toString()), Integer.parseInt(price.getText()), size.getSelectedItem().toString(), Integer.parseInt(weight.getText()));
                equipment.setManufacturer(manufacturerDAO.findByName(manufacturer.getSelectedItem().toString()));
                equipment.setSupplier(supplierDAO.findByName(supplier.getSelectedItem().toString()));
                Warehouse warehouse = warehouseDAO.findByName(warehouses.getSelectedItem().toString());
                warehouse.addEquipment(equipment);

                equipmentDAO.save(equipment);
                warehouseDAO.addToWarehouse(equipment, warehouse);
            }  catch (NumberFormatException ex){
                getError().setText("Enter all fields!");
                getError().setVisible(true);
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                getError().setText("Such equipment already exists!");
                getError().setVisible(true);
                throw new RuntimeException(ex);
            }
        });

        setVisible(true);

    }
}
