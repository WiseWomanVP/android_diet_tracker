package de.karzek.diettracker.data.mapper;

import java.util.ArrayList;
import java.util.List;

import de.karzek.diettracker.data.cache.model.AllergenEntity;
import de.karzek.diettracker.data.model.AllergenDataModel;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by MarjanaKarzek on 27.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 27.05.2018
 */
public class AllergenDataMapper {

    public AllergenDataModel transform(AllergenEntity allergenEntity){
        AllergenDataModel allergenDataModel = null;
        if(allergenEntity != null){
            allergenDataModel = new AllergenDataModel(allergenEntity.getId(),
                    allergenEntity.getName()
            );
        }
        return allergenDataModel;
    }

    public ArrayList<AllergenDataModel> transformAll(List<AllergenEntity> allergenEntities){
        ArrayList<AllergenDataModel> allergenDataModelList = new ArrayList<>();
        for (AllergenEntity entity: allergenEntities){
            allergenDataModelList.add(transform(entity));
        }
        return allergenDataModelList;
    }

    private AllergenEntity transformToEntity(AllergenDataModel allergenDataModel) {
        AllergenEntity allergenEntity = null;
        if(allergenDataModel != null){
            allergenEntity = Realm.getDefaultInstance().createObject(AllergenEntity.class,allergenDataModel.getId());
            allergenEntity.setName(allergenDataModel.getName());
        }
        return allergenEntity;
    }

    public RealmList<AllergenEntity> transformAllToEntity(List<AllergenDataModel> allergenDataModels) {
        RealmList<AllergenEntity> allergenEntities = new RealmList<>();
        for (AllergenDataModel data: allergenDataModels){
            allergenEntities.add(transformToEntity(data));
        }
        return allergenEntities;
    }
}
