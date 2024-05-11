package uk.ac.le.co2103.part2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ShoppingList.class, Product.class}, version = 1, exportSchema = false)
public abstract class ShoppingListDatabase extends RoomDatabase {
    private static volatile ShoppingListDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract ShoppingListDao shoppingListDao(); // Corrected method signature
    public abstract ProductDao productDao(); // Corrected method signature

    static ShoppingListDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoppingListDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ShoppingListDatabase.class, "shopping_db").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ShoppingListDao shoppingListDao = INSTANCE.shoppingListDao();
                ProductDao productDao = INSTANCE.productDao();
                shoppingListDao.deleteAll();
                productDao.deleteAll();
            });
        }
    };
}
