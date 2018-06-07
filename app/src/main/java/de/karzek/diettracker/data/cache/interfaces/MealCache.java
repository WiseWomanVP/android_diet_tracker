package de.karzek.diettracker.data.cache.interfaces;

import java.util.List;

import de.karzek.diettracker.data.cache.model.MealEntity;
import de.karzek.diettracker.data.cache.model.UnitEntity;
import io.reactivex.Observable;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface MealCache {
    boolean isExpired();
    boolean isCached();

    Observable<Boolean> putAllMeals(List<MealEntity> mealEntities);

    Observable<List<MealEntity>> getAllMeals();

    Observable<Long> getMealCount();
}
