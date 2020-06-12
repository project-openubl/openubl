package io.github.project.openubl.models;

import java.util.List;

public class PageModel<T> {
    private final int offset;
    private final int limit;
    private final long totalElements;
    private final List<T> pageElements;

    public PageModel(PageBean pageBean, long totalElements, List<T> pageElements) {
        this.offset = pageBean.getOffset();
        this.limit = pageBean.getLimit();
        this.totalElements = totalElements;
        this.pageElements = pageElements;
    }

    public PageModel(int offset, int limit, long totalElements, List<T> pageElements) {
        this.offset = offset;
        this.limit = limit;
        this.totalElements = totalElements;
        this.pageElements = pageElements;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public List<T> getPageElements() {
        return pageElements;
    }
}
