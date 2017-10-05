package org.onlineshoppingcart.model;
 
import org.onlineshoppingcart.entity.Product;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
 
public class ProductInfo {
    private String code;
    private String name;
    private String memory;
    private double price;
 
    private boolean newProduct=false;
 
    private CommonsMultipartFile fileData;
 
    public ProductInfo() {
    }
 
    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.memory = product.getMemory();
        this.price = product.getPrice();
    }
 
    public ProductInfo(String code, String name, String memory, double price) {
        this.code = code;
        this.name = name;
        this.memory = memory;
        this.price = price;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getMemory() {
        return memory;
    }
 
    public void setMemory(String memory) {
        this.memory = memory;
    }
    
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    public CommonsMultipartFile getFileData() {
        return fileData;
    }
 
    public void setFileData(CommonsMultipartFile fileData) {
        this.fileData = fileData;
    }
 
    public boolean isNewProduct() {
        return newProduct;
    }
 
    public void setNewProduct(boolean newProduct) {
        this.newProduct = newProduct;
    }
 
}