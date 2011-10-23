package ar.edu.unq.tpi.commons.configuration.common;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.tpi.commons.configuration.jfig.JFigConfiguration;

public class PaginatedList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 1L;

    private int pageIndex;

    private int totalRegistries;

    protected static int pageSize = JFigConfiguration.getInstance().getInt("pagination", "MAX_RESULT_PAGE");

    public static int getFromIndex(final int page) {
        return pageSize * (page - 1);
    }

    public static int getToIndex(final int page) {
        return pageSize * page - 1;
    }

    public PaginatedList(final List list, final int page, final int totalRegistries) {
        this.pageIndex = page;
        this.totalRegistries = totalRegistries;
        this.addAll(list);
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageIndex() {
        return this.pageIndex;
    }

    public void setPageIndex(final int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTotalRegistries() {
        return this.totalRegistries;
    }

    public boolean hasNextPage() {
        return pageSize * pageIndex < totalRegistries;
    }

    public boolean hasPreviousPage() {
        return pageIndex > 1;
    }

    public int getNextPageIndex() {
        return this.pageIndex + 1;
    }

    public int getPreviousPageIndex() {
        return this.pageIndex - 1;
    }

    public int getPagesCount() {
        float pageCount = (float) totalRegistries / pageSize;
        return (int) Math.ceil(pageCount);
    }
}
