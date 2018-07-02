package de.karzek.diettracker.presentation.main.cookbook;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.karzek.diettracker.R;
import de.karzek.diettracker.presentation.TrackerApplication;
import de.karzek.diettracker.presentation.common.BaseFragment;
import de.karzek.diettracker.presentation.main.cookbook.adapter.RecipeSearchResultListAdapter;
import de.karzek.diettracker.presentation.main.cookbook.dialog.filterOptionsDialog.RecipeFilterOptionsDialog;
import de.karzek.diettracker.presentation.main.cookbook.dialog.sortOptionsDialog.RecipeSortOptionsDialog;
import de.karzek.diettracker.presentation.main.cookbook.recipeDetails.RecipeDetailsActivity;
import de.karzek.diettracker.presentation.main.cookbook.recipeManipulation.RecipeManipulationActivity;
import de.karzek.diettracker.presentation.model.RecipeDisplayModel;

/**
 * Created by MarjanaKarzek on 12.05.2018.
 *
 * @author Marjana Karzek
 * @version 1.0
 * @date 12.05.2018
 */
public class CookbookFragment extends BaseFragment implements CookbookContract.View {

    @Inject
    CookbookContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cookbook_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loading_view)
    FrameLayout loadingView;
    @BindView(R.id.cookbook_placeholder)
    TextView placeholder;

    private String noRecipesPlaceholder;
    private String noResultsPlaceholder;

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_cookbook, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cookbook, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.cookbook_search).getActionView();
        searchView.setQueryHint(getString(R.string.recipe_search_hint));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconified(true);
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals(""))
                    presenter.getRecipesMatchingQuery(query);
                else
                    presenter.start();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (!query.equals(""))
                    presenter.getRecipesMatchingQuery(query);
                else
                    presenter.start();
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        setupRecyclerView();
        noResultsPlaceholder = getString(R.string.recipe_search_query_without_result_placeholder);
        noRecipesPlaceholder = getString(R.string.cookbook_placeholder);

        presenter.setView(this);
        presenter.start();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new RecipeSearchResultListAdapter(presenter, presenter, presenter, presenter));
    }

    @Override
    protected void setupFragmentComponent() {
        TrackerApplication.get(getContext()).createCookbookComponent().inject(this);
    }

    @Override
    public void setPresenter(CookbookContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.finish();
        TrackerApplication.get(getContext()).releaseCookbookComponent();
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlaceholder() {
        placeholder.setText(noRecipesPlaceholder);
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateRecyclerView(ArrayList<RecipeDisplayModel> recipes) {
        ((RecipeSearchResultListAdapter) recyclerView.getAdapter()).setList(recipes);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hidePlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void startRecipeDetailsActivity(int id) {
        startActivity(RecipeDetailsActivity.newIntent(getContext(), id));
        hideLoading();
    }

    @Override
    public void startEditRecipe(int id) {
        startActivity(RecipeManipulationActivity.newEditRecipeIntent(getContext(), id));
    }

    @Override
    public void showConfirmRecipeDeletionDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(getString(R.string.dialog_message_confirm_recipe_deletion));
        builder.setPositiveButton(getString(R.string.dialog_action_delete_recipe), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.onDeleteRecipeConfirmed(id);
            }
        });
        builder.create().show();
    }

    @Override
    public void hideQueryWithoutResultPlaceholder() {
        placeholder.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showQueryWithoutResultPlaceholder() {
        placeholder.setText(noResultsPlaceholder);
        placeholder.setVisibility(View.VISIBLE);
    }

    @Override
    public void openFilterOptionsDialog(ArrayList<String> filterOptions) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment previous = getChildFragmentManager().findFragmentByTag("dialog");
        if (previous != null)
            fragmentTransaction.remove(previous);
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new RecipeFilterOptionsDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("filterOptions", filterOptions);
        dialogFragment.setArguments(bundle);

        dialogFragment.show(fragmentTransaction, "dialog");
    }

    @Override
    public void openSortOptionsDialog(String sortOption, boolean asc) {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment previous = getChildFragmentManager().findFragmentByTag("dialog");
        if (previous != null)
            fragmentTransaction.remove(previous);
        fragmentTransaction.addToBackStack(null);

        AppCompatDialogFragment dialogFragment = new RecipeSortOptionsDialog();
        Bundle bundle = new Bundle();
        bundle.putString("sortOption", sortOption);
        bundle.putBoolean("asc", asc);
        dialogFragment.setArguments(bundle);

        dialogFragment.show(fragmentTransaction, "dialog");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cookbook_search_filter) {
            presenter.onFilterOptionSelected();
        } else if (item.getItemId() == R.id.cookbook_search_sort) {
            presenter.onSortOptionSelected();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_recipe)
    public void onAddRecipeClicked() {
        startActivity(RecipeManipulationActivity.newIntent(getContext()));
    }

    @Override
    public void filterOptionsSelected(ArrayList<String> filterOptions) {
        presenter.filterRecipesBy(filterOptions);
    }

    @Override
    public void sortOptionSelected(String sortOption, boolean asc) {
        presenter.sortRecipesBy(sortOption, asc);
    }
}
