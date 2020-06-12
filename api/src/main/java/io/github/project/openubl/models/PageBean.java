package io.github.project.openubl.models;

import java.util.Objects;

public class PageBean {

    private final int offset;
    private final int limit;

    public PageBean(Integer offset, Integer limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageBean pageBean = (PageBean) o;
        return Objects.equals(offset, pageBean.offset) &&
                Objects.equals(limit, pageBean.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, limit);
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "offset=" + offset +
                ", limit=" + limit +
                '}';
    }

}
