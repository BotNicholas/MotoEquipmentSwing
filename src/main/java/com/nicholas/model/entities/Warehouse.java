package com.nicholas.model.entities;

import java.util.ArrayList;
import java.util.List;

public class Warehouse extends Entity{
    private Integer id;
    private String name;
    private String address;

    private List<Equipment> equipment;

    public Warehouse() {
        this.equipment = new ArrayList<>();
    }

    public Warehouse(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.equipment = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Equipment> equipment) {
        this.equipment = equipment;
    }

    public void addEquipment(Equipment equipment){
        this.equipment.add(equipment);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", equipment=" + equipment +
                '}';
    }
}
