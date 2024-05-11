package uk.ac.le.co2103.part2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "shopping_lists")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private int listId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image")
    private byte [] image;




    public ShoppingList(String name, byte[] image) {
        this.name = name;
        this.image = image;


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

    public byte[] getImage() {
        return image;
    }








}
