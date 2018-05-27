package de.karzek.diettracker.data.cache.interfaces;

import java.util.List;

import de.karzek.diettracker.data.database.model.GroceryEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface GroceryCache {
    boolean isExpired();
    boolean isCached();
    Observable<List<GroceryEntity>> getAllGroceries();
    Observable<GroceryEntity> getGroceryByID(int id);
    Observable<GroceryEntity> getGroceryByBarcode(int barcode);
    Observable<GroceryEntity> getGroceryByName(String name);
    void put(GroceryEntity groceryEntity);
}