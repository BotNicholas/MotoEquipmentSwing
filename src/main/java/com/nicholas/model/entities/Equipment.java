package com.nicholas.model.entities;

public class Equipment extends Entity{
    private Integer id;
    private String name;
    private EquipmentType type;
    private Integer price;
    private String size;
    private Integer weight;
    private Manufacturer manufacturer;
    private Supplier supplier;

    public Equipment() {
    }

    public Equipment(Integer id, String name, EquipmentType type, Integer price, String size, Integer weight) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
        this.weight = weight;
    }

    public Equipment(Integer id, String name, EquipmentType type, Integer price, String size, Integer weight, Manufacturer manufacturer, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.size = size;
        this.weight = weight;
        this.manufacturer = manufacturer;
        this.supplier = supplier;
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

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", weight=" + weight +
                ", manufacturer=" + manufacturer +
                ", supplier=" + supplier +
                '}';
    }
}
