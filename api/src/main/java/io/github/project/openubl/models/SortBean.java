package io.github.project.openubl.models;

import java.util.Objects;

public class SortBean {

    private final String fieldName;
    private final boolean asc;

    public SortBean(String fieldName, boolean asc) {
        this.fieldName = fieldName;
        this.asc = asc;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isAsc() {
        return asc;
    }

    public String getQuery() {
        return fieldName + ":" + (isAsc() ? "asc" : "desc");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortBean sortBean = (SortBean) o;
        return Objects.equals(fieldName, sortBean.fieldName) &&
                Objects.equals(asc, sortBean.asc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, asc);
    }

    @Override
    public String toString() {
        return "SortBean{" +
                "fieldName='" + fieldName + '\'' +
                ", asc=" + asc +
                '}';
    }
}
