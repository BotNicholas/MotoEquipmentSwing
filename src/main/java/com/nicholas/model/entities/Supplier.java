package com.nicholas.model.entities;

public class Supplier extends Entity{
    private Integer id;
    private String name;
    private Manufacturer manufacturer;

    public Supplier() {
    }

    public Supplier(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Supplier(Integer id, String name, Manufacturer manufacturer) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public int getId() {
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

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
