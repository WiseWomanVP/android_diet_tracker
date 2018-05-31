package de.karzek.diettracker.domain.interactor.useCase.useCaseInterface;

import android.support.annotation.IntDef;

import java.util.List;

import de.karzek.diettracker.domain.common.BaseObservableUseCase;
import de.karzek.diettracker.domain.common.BaseUseCase;
import de.karzek.diettracker.domain.model.FavoriteGroceryDomainModel;
import de.karzek.diettracker.domain.model.GroceryDomainModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public interface GetFavoriteFoodsUseCase extends BaseObservableUseCase<GetFavoriteFoodsUseCase.Input, GetFavoriteFoodsUseCase.Output> {

    @AllArgsConstructor
    @Data
    class Input implements BaseUseCase.Input {
        int type;

        @FavoriteType
        public static final int FAVORITE_TYPE_FOOD = 0;
        public static final int FAVORITE_TYPE_DRINKS = 1;
        public static final int FAVORITE_TYPE_RECIPE = 2;

        @IntDef({FAVORITE_TYPE_FOOD, FAVORITE_TYPE_DRINKS, FAVORITE_TYPE_RECIPE})

        private @interface FavoriteType {

        }
    }

    @AllArgsConstructor
    @Data
    class Output implements BaseUseCase.Output {

        @GroceryDataListStatus
        int status;
        public static final int ERROR_NO_DATA = 0;
        public static final int ERROR_NETWORK_PROBLEM = 1;
        public static final int ERROR_UNKNOWN_PROBLEM = 2;
        public static final int SUCCESS = 3;

        List<FavoriteGroceryDomainModel> favoriteFoodsList;

        @IntDef({ERROR_NO_DATA, ERROR_NETWORK_PROBLEM, ERROR_UNKNOWN_PROBLEM, SUCCESS})

        private @interface GroceryDataListStatus {

        }
    }
}