package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShoppingListRepository {
    private final ShoppingListDao shoppingListDao;
    private final LiveData<List<ShoppingList>> allShoppingLists;

    ShoppingListRepository(Application application) {
        ShoppingListDatabase database = ShoppingListDatabase.getDatabase(application);
        shoppingListDao = database.shoppingListDao();
        allShoppingLists = shoppingListDao.getAll();
    }

    LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }

    void insert(ShoppingList shoppingList) {
        ShoppingListDatabase.databaseWriteExecutor.execute(() -> shoppingListDao.insert(shoppingList));
    }

    void delete(ShoppingList shoppingList) {
        ShoppingListDatabase.databaseWriteExecutor.execute(() -> shoppingListDao.delete(shoppingList));
    }
}
