package company;

import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private Double unitPrice;
    private Integer stock;
    private Integer remainingStock;
    private UUID categoryId;

    public Product(UUID id, String name, Double unitPrice, Integer stock, Integer remainingStock, UUID categoryId) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.remainingStock = remainingStock;
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getRemainingStock() {
        return remainingStock;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
}
