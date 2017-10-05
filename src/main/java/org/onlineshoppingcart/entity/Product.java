package org.onlineshoppingcart.entity;
 
import java.io.Serializable;
import java.util.Date;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
@Entity
@Table(name = "Phones")
public class Product implements Serializable {
 
    private static final long serialVersionUID = -100L;
 
    private String code;
    private String name;
    private String memory;
    private double price;
    private byte[] image;
 
    private Date createDate;
 
    public Product() {
    }
 
    @Id
    @Column(name = "Code", length = 20, nullable = false)
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    @Column(name = "Name", length = 255, nullable = false)
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    @Column(name = "Memory", length = 10, nullable = false)
    public String getMemory() {
        return memory;
    }
 
    public void setMemory(String memory) {
        this.memory = memory;
    }
    
    @Column(name = "Price", nullable = false)
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    public Date getCreateDate() {
        return createDate;
    }
 
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
 
    @Lob
    @Column(name = "Image", length = Integer.MAX_VALUE, nullable = true)
    public byte[] getImage() {
        return image;
    }
 
    public void setImage(byte[] image) {
        this.image = image;
    }
 
}