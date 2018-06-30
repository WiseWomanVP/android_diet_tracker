package de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.itemWrapper.RecipeDetailsViewItemWrapper;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder.RecipeDetailsIngredientViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder.RecipeDetailsIngredientsAndPortionsTitleViewHolder;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.adapter.viewHolder.RecipeDetailsPhotoViewHolder;

/**
 * Created by MarjanaKarzek on 30.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 30.05.2018
 */
public class RecipeDetailsViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<RecipeDetailsViewItemWrapper> list;

    private RecipeDetailsIngredientsAndPortionsTitleViewHolder.OnExpandNutritionDetailsViewClickListener onExpandNutritionDetailsViewClickListener;

    public RecipeDetailsViewListAdapter(RecipeDetailsIngredientsAndPortionsTitleViewHolder.OnExpandNutritionDetailsViewClickListener onExpandNutritionDetailsViewClickListener){
        list = new ArrayList<>();

        this.onExpandNutritionDetailsViewClickListener = onExpandNutritionDetailsViewClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case RecipeDetailsViewItemWrapper.ItemType.PHOTO_VIEW:
                return new RecipeDetailsPhotoViewHolder(parent);
            case RecipeDetailsViewItemWrapper.ItemType.INGREDIENTS_TITLE_VIEW:
                return new RecipeDetailsIngredientsAndPortionsTitleViewHolder(parent, onExpandNutritionDetailsViewClickListener);
            case RecipeDetailsViewItemWrapper.ItemType.INGREDIENT_VIEW:
                return new RecipeDetailsIngredientViewHolder(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecipeDetailsPhotoViewHolder) {
            ((RecipeDetailsPhotoViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeDetailsIngredientsAndPortionsTitleViewHolder) {
            ((RecipeDetailsIngredientsAndPortionsTitleViewHolder) holder).bind(list.get(position));
        } else if (holder instanceof RecipeDetailsIngredientViewHolder) {
            ((RecipeDetailsIngredientViewHolder) holder).bind(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<RecipeDetailsViewItemWrapper> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) {
        return list.get(position).getType();
    }

}
