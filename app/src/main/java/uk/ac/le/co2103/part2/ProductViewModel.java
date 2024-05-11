package uk.ac.le.co2103.part2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository repo;

    public ProductViewModel(Application application) {
        super(application);
        repo = new ProductRepository(application);
    }

    LiveData<List<Product>> getProductsByShoppingListId(int shoppingListId) {
        return repo.getProductsByShoppingListId(shoppingListId);
    }

    Boolean productExistsInList(String name, int shoppingListId) {
        return repo.productExistsInList(name, shoppingListId);
    }


    public void insert(Product product) {
        repo.insert(product);
    }

    public void delete(Product product) {
        repo.delete(product);
    }


    Boolean productExistsInListAndIdSame(String name, int shoppingListId, int productId) {
        return repo.productExistsInListAndIdSame(name, shoppingListId, productId);
    }

    void updateProductGivenId(int productId, String newName, String newQuantity, String newUnit) {
        repo.updateProductGivenId(productId, newName, newQuantity, newUnit);
    }
}
