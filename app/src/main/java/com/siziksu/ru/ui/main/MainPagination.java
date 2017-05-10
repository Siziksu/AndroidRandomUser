package com.siziksu.ru.ui.main;

import android.util.Log;

import com.siziksu.ru.common.Constants;

public class MainPagination {

    private static final int FIRST_PAGE = 1;

    private int nextPage = FIRST_PAGE;
    private int currentPage = FIRST_PAGE;

    public MainPagination() {}

    boolean shouldLoadMore() {
        return !(nextPage < FIRST_PAGE || nextPage < currentPage);
    }

    int getNextPage() {
        return nextPage;
    }

    void resetPage() {
        nextPage = FIRST_PAGE;
        currentPage = FIRST_PAGE;
    }

    void setPage(int page) {
        this.currentPage = page;
        nextPage++;
        Log.i(Constants.TAG, "Current page: " + currentPage);
        Log.i(Constants.TAG, "Next page: " + nextPage);
    }

    boolean isNotFirstPage() {
        return nextPage > FIRST_PAGE;
    }

    int getCurrentPage() {
        return currentPage;
    }

    void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
