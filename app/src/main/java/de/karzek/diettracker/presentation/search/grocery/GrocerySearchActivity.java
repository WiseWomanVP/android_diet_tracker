package de.karzek.diettracker.presentation.search.grocery;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseActivity;
import de.karzek.diettracker.presentation.model.GroceryDisplayModel;
import de.karzek.diettracker.presentation.search.grocery.adapter.GrocerySearchResultListAdapter;
import de.karzek.diettracker.presentation.search.grocery.adapter.itemWrapper.GrocerySearchResultItemWrapper;
import de.karzek.diettracker.presentation.search.grocery.groceryDetail.GroceryDetailsActivity;

import static de.karzek.diettracker.data.cache.model.GroceryEntity.TYPE_FOOD;

/**
 * Created by MarjanaKarzek on 29.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 29.05.2018
 */
public class GrocerySearchActivity extends BaseActivity implements GrocerySearchContract.View {

    @Inject
    GrocerySearchContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.food_search_placeholder) TextView placeholder;
    @BindView(R.id.loading_view) FrameLayout loadingView;

    private int groceryType;
    private String noResultsPlaceholer;
    private String noFavoritesPlaceholder;
    private String selectedDate;
    private int selectedMeal;

    public static Intent newIntent(Context context, int groceryType, String selectedDate, int selectedMeal) {
        Intent intent = new Intent(context, GrocerySearchActivity.class);
        intent.putExtra("groceryType", groceryType);
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("selectedMeal", selectedMeal);

        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getFavoriteGroceries(groceryType);
    }

    @Override
    protected void setupActivityComponents() {
        TrackerApplication.get(this).getAppComponent().inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.grocery_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.equals(""))
                    presenter.getGroceriesMatchingQuery(query, groceryType);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(!query.equals(""))
                    presenter.getGroceriesMatchingQuery(query, groceryType);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_search);
        ButterKnife.bind(this);

        groceryType = getIntent().getExtras().getInt("groceryType", 0);
        selectedDate = getIntent().getExtras().getString("selectedDate", "");
        selectedMeal = getIntent().getExtras().getInt("selectedMeal", 0);

        if(groceryType == TYPE_FOOD){
            noResultsPlaceholer = getString(R.string.food_search_query_without_result_placeholder);
            noFavoritesPlaceholder = getString(R.string.food_search_placeholder);
        } else {
            noResultsPlaceholer = getString(R.string.drink_search_query_without_result_placeholder);
            noFavoritesPlaceholder = getString(R.string.drink_search_placeholder);
        }

        setupSupportActionBar();
        setupRecyclerView();

        presenter.setView(this);
        setPresenter(presenter);
        presenter.start();
        presenter.getFavoriteGroceries(groceryType);
    }

    @Override
    protected void onDestroy() {
        presenter.finish();
        super.onDestroy();
    }

    @Override
    public void setPresenter(GrocerySearchContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showGroceryDetails(int id){
        startActivity(GroceryDetailsActivity.newIntent(this, id, selectedDate, selectedMeal));
    }

    @Override
    public void updateFoodSearchResultList(ArrayList<GroceryDisplayModel> foods) {
        ArrayList<GrocerySearchResultItemWrapper> wrappedFoods = new ArrayList<>();

        for(GroceryDisplayModel food: foods){
            wrappedFoods.add(new GrocerySearchResultItemWrapper(food.getType(),food));
        }

        ((GrocerySearchResultListAdapter) recyclerView.getAdapter()).setList(wrappedFoods);
    }

    @Override
    public void showPlaceholder() {
        placeholder.setText(noFavoritesPlaceholder);
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showQueryWithoutResultPlaceholder() {
        placeholder.setText(noResultsPlaceholer);
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideQueryWithoutResultPlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public String getSelectedDate() {
        return selectedDate;
    }

    private void setupSupportActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arrow_white,null));
        if(groceryType == TYPE_FOOD){
            getSupportActionBar().setTitle(getString(R.string.food_search_title));
        } else {
            getSupportActionBar().setTitle(getString(R.string.drink_search_title));
        }
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GrocerySearchResultListAdapter(presenter,presenter,presenter));
    }
}