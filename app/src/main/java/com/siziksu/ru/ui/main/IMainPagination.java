package com.siziksu.ru.ui.main;

public interface IMainPagination {

    boolean shouldLoadMore();

    int getNextPage();

    void resetPage();

    void setPage(int page);

    boolean isNotFirstPage();

    int getCurrentPage();

    void setNextPage(int nextPage);

    void setCurrentPage(int currentPage);
}
