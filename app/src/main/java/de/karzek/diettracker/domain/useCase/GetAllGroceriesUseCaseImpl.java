package de.karzek.diettracker.domain.useCase;

import java.util.List;

import de.karzek.diettracker.data.repository.GroceryRepositoryImpl;
import de.karzek.diettracker.domain.mapper.GroceryUIMapper;
import de.karzek.diettracker.domain.model.GroceryData;
import de.karzek.diettracker.domain.useCase.useCaseInterface.GetAllGroceriesUseCase;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class GetAllGroceriesUseCaseImpl implements GetAllGroceriesUseCase {

    private final GroceryRepositoryImpl repository;
    private final GroceryUIMapper mapper;

    public GetAllGroceriesUseCaseImpl(GroceryRepositoryImpl repository, GroceryUIMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Observable<Output> execute(Input input) {
        return repository.getAllGroceries().map(new Function<List<GroceryData>, Output>() {
            @Override
            public Output apply(List<GroceryData> groceryDataList) {
                return new Output(Output.SUCCESS, groceryDataList);
            }
        });
    }

}