package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductRepository {
    private final ProductDao productDao;

    ProductRepository(Application application) {
        ShoppingListDatabase database= ShoppingListDatabase.getDatabase(application);
        productDao = database.productDao();
    }

    LiveData<List<Product>> getProductsByShoppingListId(int shoppingListId) {
        return productDao.getProductsByShoppingListId(shoppingListId);
    }

    void insert(Product product) {
        ShoppingListDatabase.databaseWriteExecutor.execute(() -> productDao.insert(product));
    }

    Boolean productExistsInList(String name, int shoppingListId) {
        return productDao.productExistsInList(name, shoppingListId);
    }

    Boolean productExistsInListAndIdSame(String name, int shoppingListId, int productId) {
        return productDao.productExistsInListAndIdSame(name, shoppingListId, productId);
    }

    void delete(Product product) {
        ShoppingListDatabase.databaseWriteExecutor.execute(() -> productDao.delete(product));
    }

    void updateProductGivenId(int productId, String newName, String newQuantity, String newUnit) {
        ShoppingListDatabase.databaseWriteExecutor.execute(() -> productDao.updateProductGivenId(productId, newName, newQuantity, newUnit));
    }
}
