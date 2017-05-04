package com.siziksu.ru.ui.main;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.siziksu.ru.App;
import com.siziksu.ru.R;
import com.siziksu.ru.common.Constants;
import com.siziksu.ru.common.functions.Consumer;
import com.siziksu.ru.common.model.response.users.User;
import com.siziksu.ru.presenter.main.IMainPresenter;
import com.siziksu.ru.presenter.main.IMainView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class MainActivity extends AppCompatActivity implements IMainView {

    @Inject
    IMainPresenter mainPresenter;
    @Inject
    IUsersAdapter adapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.users_recycler_view)
    RecyclerView users;
    @BindView(R.id.users_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.loading)
    ProgressBar loading;

    private static final int FIRST_PAGE = 1;

    private SearchView searchView;
    private int nextPage = FIRST_PAGE;
    private int currentPage = FIRST_PAGE;
    private boolean firstTime = true;
    private String filter;
    private boolean refreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.get(getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initRecyclerView();
    }

    private void initRecyclerView() {
        users.setHasFixedSize(true);
        adapter.init(
                (v, position) -> {
                    User user = adapter.getItem(position);
                    if (user != null) {
                        switch (v.getId()) {
                            case R.id.user_card_picture:
                                mainPresenter.showUserInfo(user);
                                break;
                            case R.id.user_card_delete:
                                mainPresenter.showDeleteUserConfirmation(
                                        Constants.DIALOG_TYPE_DELETE_USER,
                                        () -> adapter.deleteUser(position)
                                );
                                break;
                            default:
                                break;
                        }
                    }
                },
                () -> {
                    if (nextPage < FIRST_PAGE || nextPage < currentPage) {
                        return;
                    }
                    if (!adapter.isFiltered()) {
                        mainPresenter.getUsers(nextPage);
                    }
                },
                string -> {
                    String text;
                    switch (string) {
                        case R.string.no_results_for_filter:
                            text = String.format(getString(string), filter);
                            break;
                        default:
                            text = getString(string);
                            break;
                    }
                    noData(true, text);
                }
        );
        users.addOnScrollListener(adapter.getOnScrollListener());
        users.setAdapter(adapter.getAdapter());
        users.setItemAnimator(new DefaultItemAnimator());
        users.setLayoutManager(adapter.getLayoutManager());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            doAfterClearingAdapter(() -> mainPresenter.getUsersFromSwipeRefresh());
            if (!searchView.isIconified()) {
                refreshing = true;
                searchView.onActionViewCollapsed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (searchView != null) {
            if (!searchView.isIconified()) {
                searchView.onActionViewCollapsed();
            } else if (searchView.isIconified() && adapter.isFiltered()) {
                filter = "";
                adapter.clearFilter();
                noData(adapter.isEmpty(), getString(R.string.no_data_available));
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(Constants.EXTRAS_NEXT_PAGE, nextPage);
        savedInstanceState.putInt(Constants.EXTRAS_TOTAL_PAGES, currentPage);
        savedInstanceState.putString(Constants.EXTRAS_FILTER, filter);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        nextPage = savedInstanceState.getInt(Constants.EXTRAS_NEXT_PAGE);
        currentPage = savedInstanceState.getInt(Constants.EXTRAS_TOTAL_PAGES);
        filter = savedInstanceState.getString(Constants.EXTRAS_FILTER);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.register(this);
        if (firstTime) {
            mainPresenter.getUsers();
            firstTime = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mainPresenter != null) {
            mainPresenter.unregister();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(onQueryTextListener);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String text) {
            searchView.onActionViewCollapsed();
            search(text);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String text) {
            return false;
        }

        private void search(String text) {
            if (!refreshing) {
                filter = text;
                if (!TextUtils.isEmpty(text)) {
                    adapter.filterUsers(text);
                } else {
                    requestFirstPage();
                }
            }
            refreshing = false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_restore:
                mainPresenter.showDeleteUserConfirmation(
                        Constants.DIALOG_TYPE_RESTORE_BLOCKED_USERS,
                        () -> {
                            adapter.restoreBlockedUsers();
                            Snackbar.make(toolbar, getString(R.string.blocked_users_restored), Snackbar.LENGTH_SHORT).show();
                        }
                );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showProgress(boolean value) {
        loading.setVisibility(value ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showUsers(List<User> users, int page) {
        this.currentPage = page;
        nextPage++;
        Log.i(Constants.TAG, "Current page: " + currentPage);
        Log.i(Constants.TAG, "Next page: " + nextPage);
        adapter.showUsers(users, nextPage > FIRST_PAGE);
        noData(adapter.isEmpty(), getString(R.string.no_data_available));
    }

    @Override
    public void showConnected(boolean value) {
        noData(!value, getString(R.string.connection_error));
        if (!value) {
            Snackbar.make(toolbar, getString(R.string.connection_error), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void requestFirstPage() {
        doAfterClearingAdapter(() -> mainPresenter.getUsers());
    }

    private void doAfterClearingAdapter(Consumer consumer) {
        filter = "";
        adapter.clearLists();
        nextPage = FIRST_PAGE;
        currentPage = FIRST_PAGE;
        consumer.consume();
    }

    private void noData(boolean value, String string) {
        if (value) {
            noData.setText(string);
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }
    }
}
