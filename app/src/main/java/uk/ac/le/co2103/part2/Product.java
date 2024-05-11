package uk.ac.le.co2103.part2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "products", foreignKeys = @ForeignKey(entity = ShoppingList.class, parentColumns = "listId", childColumns = "listId", onDelete = ForeignKey.CASCADE))
public class Product{
    @PrimaryKey(autoGenerate = true)
    private int productId;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "quantity")
    private String quantity;
    @ColumnInfo(name = "unit")
    private String unit;

    public int listId;

    public Product(String name, String quantity, String unit, int listId) {

        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.listId = listId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
