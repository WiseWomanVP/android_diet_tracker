package de.karzek.diettracker.domain.interactor.useCase.favoriteGrocery;

import java.util.List;

import de.karzek.diettracker.data.model.FavoriteGroceryDataModel;
import de.karzek.diettracker.data.repository.FavoriteGroceryRepositoryImpl;
import de.karzek.diettracker.domain.interactor.useCase.useCaseInterface.favoriteGrocery.GetFavoriteFoodsUseCase;
import de.karzek.diettracker.domain.mapper.FavoriteGroceryDomainMapper;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 31.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 31.05.2018
 */
public class GetFavoriteFoodsUseCaseImpl implements GetFavoriteFoodsUseCase {

    private final FavoriteGroceryRepositoryImpl repository;
    private final FavoriteGroceryDomainMapper mapper;

    public GetFavoriteFoodsUseCaseImpl(FavoriteGroceryRepositoryImpl repository, FavoriteGroceryDomainMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllFavoritesByType(input.getType())
                .map(new Function<List<FavoriteGroceryDataModel>, Output>() {
                    @Override
                    public Output apply(List<FavoriteGroceryDataModel> favoriteGroceryDataModels) {
                        return new Output(Output.SUCCESS, mapper.transformAll(favoriteGroceryDataModels));
                    }
                });
    }
}