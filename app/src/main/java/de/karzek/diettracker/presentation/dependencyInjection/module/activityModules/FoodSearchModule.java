package de.karzek.diettracker.presentation.dependencyInjection.module.activityModules;

import dagger.Module;
import dagger.Provides;
import de.karzek.diettracker.data.cache.FavoriteGroceryCacheImpl;
import de.karzek.diettracker.data.cache.GroceryCacheImpl;
import de.karzek.diettracker.data.mapper.FavoriteGroceryDataMapper;
import de.karzek.diettracker.data.mapper.GroceryDataMapper;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.data.repository.repositoryInterface.FavoriteGroceryRepository;
import de.karzek.diettracker.domain.interactor.useCase.GetFavoriteFoodsUseCaseImpl;
import de.karzek.diettracker.domain.interactor.useCase.GetMatchingGroceriesUseCaseImpl;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import de.karzek.diettracker.domain.mapper.GroceryDomainMapper;
import de.karzek.diettracker.presentation.mapper.FavoriteGroceryUIMapper;
import de.karzek.diettracker.presentation.mapper.GroceryUIMapper;
import de.karzek.diettracker.presentation.search.food.FoodSearchContract;
import de.karzek.diettracker.presentation.search.food.FoodSearchPresenter;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
@Module
public class FoodSearchModule {

    //data

    @Provides
    FavoriteGroceryCacheImpl provideFavoriteGroceryCacheImpl(){
        return new FavoriteGroceryCacheImpl();
    }

    @Provides
    FavoriteGroceryDataMapper provideFavoriteGroceryDataMapper(){
        return new FavoriteGroceryDataMapper();
    }

    @Provides
    FavoriteGroceryRepositoryImpl provideFavoriteGroceryRepositoryImpl(FavoriteGroceryCacheImpl cache, FavoriteGroceryDataMapper mapper){
        return new FavoriteGroceryRepositoryImpl(cache, mapper);
    }

    //domain

    @Provides
    FavoriteGroceryDomainMapper provideFavoriteGroceryDomainMapper(){
        return new FavoriteGroceryDomainMapper();
    }

    @Provides
    GetFavoriteFoodsUseCaseImpl provideGetFavoriteFoodsUseCase(FavoriteGroceryRepositoryImpl repository, FavoriteGroceryDomainMapper mapper){
        return new GetFavoriteFoodsUseCaseImpl(repository, mapper);
    }

    @Provides
    GetMatchingGroceriesUseCaseImpl provideGetMatchingGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryDomainMapper mapper){
        return new GetMatchingGroceriesUseCaseImpl(repository, mapper);
    }

    //presentation

    @Provides
    FavoriteGroceryUIMapper provideFavoriteGroceryUIMapper(){
        return new FavoriteGroceryUIMapper();
    }

    @Provides
    FoodSearchContract.Presenter provideFoodSearchPresenter(GetFavoriteFoodsUseCaseImpl getFavoriteFoodsUseCase,
                                                            GetMatchingGroceriesUseCaseImpl getMatchingGroceriesUseCase,
                                                            FavoriteGroceryUIMapper favoriteGroceryMapper,
                                                            GroceryUIMapper groceryMapper) {
        return new FoodSearchPresenter(getFavoriteFoodsUseCase, getMatchingGroceriesUseCase, favoriteGroceryMapper, groceryMapper);
    }
}
