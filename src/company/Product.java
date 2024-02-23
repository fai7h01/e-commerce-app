package company;

import company.category.Category;

import java.time.LocalDateTime;
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

    public LocalDateTime getDeliveryDueDate() throws Exception {
        for (Category category : StaticConstants.CATEGORY_LIST) {
            if (category.getId().toString().equals(getCategoryId().toString())){
                return category.calculateDeliveryTime();
            }
        }
        throw new Exception("Category could not found.");
    }

    public String getCategoryName() throws Exception {
        for (Category category : StaticConstants.CATEGORY_LIST) {
            if (category.getId().toString().equals(categoryId.toString())){
                return category.getName();
            }
        }
        throw new Exception("Category not found," + getName());
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
