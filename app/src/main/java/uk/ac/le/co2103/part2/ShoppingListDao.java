package uk.ac.le.co2103.part2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ShoppingListDao {
    @Insert
    void insert(ShoppingList shoppingList);
    @Query("SELECT * FROM shopping_lists ORDER BY name ASC")
    LiveData<List<ShoppingList>> getAll();
    @Query("DELETE FROM shopping_lists")
    void deleteAll();
    @Delete
    void delete(ShoppingList shoppingList);
}
