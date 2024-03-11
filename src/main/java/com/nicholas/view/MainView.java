package com.nicholas.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.MalformedURLException;

public class MainView extends JFrame {
    private final JPanel panel;
    private JTable table;
    private final JMenuBar menuBar;

    private final Toolkit toolkit;
    private final Dimension screen;

    private final Dimension frame_d;

        private JMenu tables;
    private JMenu manufacturers;
    private JMenu supplier;
    private JMenu equipment;
    private JMenu warehouse;
    private JScrollPane scrollPane;
    private JButton save;

    private JLabel error;
    private JPanel actionPanel;

    private JPanel searchAndFilter;

    public MainView() throws MalformedURLException {
        setIconImage(new ImageIcon("src/main/resources/images/icon.jpg").getImage());
        setTitle("MotoMoto Store");
        setCursor(Cursor.HAND_CURSOR);
        setResizable(false);


        toolkit = Toolkit.getDefaultToolkit();
        screen = toolkit.getScreenSize();
        frame_d = new Dimension(800, 450);


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        searchAndFilter = new JPanel();
        searchAndFilter.setVisible(false);
//        searchAndFilter.setBackground(Color.red);

        panel.add(searchAndFilter);


        scrollPane = new JScrollPane();
        table = new JTable();
        table.setRowHeight(20);

        scrollPane.setViewportView(table);
        scrollPane.setBorder(new EmptyBorder(0, 10, 10, 10));
        scrollPane.setPreferredSize(new Dimension(frame_d.width-20, frame_d.height-110));
//        scrollPane.setBackground(Color.GREEN);
        panel.add(scrollPane);

        actionPanel = new JPanel(new GridLayout(2, 1));
        actionPanel.setMaximumSize(new Dimension(200, 100));
        actionPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
//        actionPanel.setBackground(Color.GREEN);
        error = new JLabel();
        error.setForeground(Color.red);
        error.setVisible(false);
        error.setHorizontalAlignment(SwingConstants.CENTER);
        actionPanel.add(error);


        save = new JButton("Save!");
        save.setVisible(false);

        save.addActionListener((e)->{
            save.setVisible(false);
        });
        actionPanel.add(save);

        panel.add(actionPanel);

//        menuItems = new HashMap<>();
        menuBar = new JMenuBar();

        tables = new JMenu("Tables");

        manufacturers = new JMenu("Manufacturers");
        manufacturers.add(new JMenuItem("Add"));
        manufacturers.add(new JMenuItem("Open"));

        supplier = new JMenu("Supplier");
        supplier.add(new JMenuItem("Add"));
        supplier.add(new JMenuItem("Open"));

        equipment = new JMenu("Equipment");
        equipment.add(new JMenuItem("Add"));
        equipment.add(new JMenuItem("Open"));

        warehouse = new JMenu("Warehouse");
        warehouse.add(new JMenuItem("Add"));
        warehouse.add(new JMenuItem("Open"));


//        menuItems.put(manufacturers, List.of(new JMenuItem("Open"),
//                                             new JMenuItem("Edit"),
//                                             new JMenuItem("Delete")));
//        menuItems.get(manufacturers).forEach(jmi -> manufacturers.add(jmi));
//
//        menuItems.put(supplier, List.of(new JMenuItem("Open"),
//                                        new JMenuItem("Edit"),
//                                        new JMenuItem("Delete")));
//        menuItems.get(supplier).forEach(jmi -> supplier.add(jmi));
//
//        menuItems.put(equipment, List.of(new JMenuItem("Open"),
//                                         new JMenuItem("Edit"),
//                                         new JMenuItem("Delete")));
//        menuItems.get(equipment).forEach(jmi -> equipment.add(jmi));
//
//        menuItems.put(warehouse, List.of(new JMenuItem("Open"),
//                                         new JMenuItem("Edit"),
//                                         new JMenuItem("Delete")));
//        menuItems.get(warehouse).forEach(jmi -> warehouse.add(jmi));


//        menuItems.put(manufacturers, null);
//        menuItems.put(supplier, null);
//        menuItems.put(equipment, null);
//        menuItems.put(warehouse, null);

//        for(JMenu menu : menuItems.keySet()){
//            menuBar.add(menu);
//        }

//        menuItems.keySet().forEach(mi -> menuBar.add(mi));

        tables.add(manufacturers);
        tables.add(supplier);
        tables.add(equipment);
        tables.add(warehouse);

        menuBar.add(tables);

        setJMenuBar(menuBar);
        setContentPane(panel);
//        pack();
//        setSize(frame_d);
        setBounds((screen.width/2) - (frame_d.width/2), (screen.height/2)-(frame_d.height/2), frame_d.width, frame_d.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

//    public HashMap<JMenu, List<JMenuItem>> getMenuItems(){
//        return menuItems;
//    }



    public JPanel getPanel(){
        return panel;
    }

    public JMenuBar getViewMenuBar(){
        return menuBar;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JButton getSaveButton() {
        return save;
    }

    public JLabel getError() {
        return error;
    }

    public void setError(JLabel error) {
        this.error = error;
    }

    public JPanel getSearchAndFilter() {
        return searchAndFilter;
    }

    public void setSearchAndFilter(JPanel searchAndFilter) {
        this.searchAndFilter = searchAndFilter;
    }
}
