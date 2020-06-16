package io.github.project.openubl.utils;

import io.github.project.openubl.models.PageBean;
import io.github.project.openubl.models.SortBean;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceUtils {

    public static List<NameValuePair> buildNameValuePairs(Integer offset, Integer limit, List<SortBean> sortBeans) {
        List<NameValuePair> queryParameters = new ArrayList<>();
        if (offset != null) {
            queryParameters.add(new BasicNameValuePair("offset", String.valueOf(offset)));
        }
        if (limit != null) {
            queryParameters.add(new BasicNameValuePair("limit", String.valueOf(limit)));
        }
        queryParameters.addAll(sortBeans.stream()
                .map(f -> new BasicNameValuePair("sort_by", f.getQuery()))
                .collect(Collectors.toList())
        );
        return queryParameters;
    }

    public static PageBean getPageBean(Integer offset, Integer limit) {
        if (offset == null || offset < 0) {
            offset = 0;
        }

        if (limit == null || limit > 1000) {
            limit = 1000;
        }
        if (limit < 0) {
            limit = 10;
        }

        return new PageBean(offset, limit);
    }

    public static List<SortBean> getSortBeans(List<String> sortBy, String... validFieldNames) {
        if (sortBy == null) {
            return Collections.emptyList();
        }
        List<String> validFieldNamesList = validFieldNames != null ? Arrays.asList(validFieldNames) : Collections.emptyList();
        return sortBy.stream()
                .flatMap(f -> Stream.of(f.split(",")))
                .map(f -> {
                    String[] split = f.trim().split(":");
                    String fieldName = !split[0].isEmpty() ? split[0] : null;
                    boolean isAsc = split.length <= 1 || split[1].equalsIgnoreCase("asc");
                    return new SortBean(fieldName, isAsc);
                })
                .filter(f -> validFieldNamesList.contains(f.getFieldName()))
                .collect(Collectors.toList());
    }
}
