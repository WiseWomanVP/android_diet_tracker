package de.karzek.diettracker.presentation.main.diary.meal;

import de.karzek.diettracker.presentation.common.BasePresenter;
import de.karzek.diettracker.presentation.common.BaseView;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public interface GenericMealContract {

    interface View extends BaseView<Presenter> {

        void hideRecyclerView();

        void showGroceryListPlaceholder();

        void showNutritionDetails(String value);
    }

    interface Presenter extends BasePresenter<View> {

    }
}
