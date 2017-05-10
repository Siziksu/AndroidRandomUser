package com.siziksu.ru.ui.main;

import android.util.Log;

import com.siziksu.ru.common.Constants;

import javax.inject.Inject;

public class MainPagination implements IMainPagination {

    private static final int FIRST_PAGE = 1;

    private int nextPage = FIRST_PAGE;
    private int currentPage = FIRST_PAGE;

    @Inject
    MainPagination() {}

    @Override
    public boolean shouldLoadMore() {
        return !(nextPage < FIRST_PAGE || nextPage < currentPage);
    }

    @Override
    public int getNextPage() {
        return nextPage;
    }

    @Override
    public void resetPage() {
        nextPage = FIRST_PAGE;
        currentPage = FIRST_PAGE;
    }

    @Override
    public void setPage(int page) {
        this.currentPage = page;
        nextPage++;
        Log.i(Constants.TAG, "Current page: " + currentPage);
        Log.i(Constants.TAG, "Next page: " + nextPage);
    }

    @Override
    public boolean isNotFirstPage() {
        return nextPage > FIRST_PAGE;
    }

    @Override
    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
