package com.qs.common.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;

import java.util.*;

public class ConnectionUtils {

    /**
     * 将idList集合进行分页，每页的页容量为subCnt个记录
     *
     * @param idList list集合
     * @param subCnt 页容量
     * @param <T>    泛型限定
     * @return 返回分组后的list集合
     */
    public static <T> List<List<T>> groupIdList(List<T> idList, int subCnt) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>(0);
        }

        if (subCnt <= 0) {
            subCnt = 1000;
        }

        int totalPage = idList.size() % subCnt == 0 ? idList.size() / subCnt : (idList.size() / subCnt) + 1;
        List<List<T>> listList = new ArrayList<>(totalPage);
        for (int pageNo = 0; pageNo < totalPage; pageNo++) {
            int firstIndex = pageNo * subCnt;
            int toIndex = (pageNo + 1) * subCnt;
            if (toIndex > idList.size()) {
                toIndex = idList.size();
            }

            listList.add(idList.subList(firstIndex, toIndex));
        }

        return listList;
    }


    /**
     * 1️将source集合中包含的remove集合的元素移除，返回和remove集合中元素没有重复的部分；
     *
     * @param source 源集合
     * @param remove 比较的集合
     * @param <E>    泛型限定
     * @return 返回存在于source集合中，不存在remove集合的元素集合
     */
    public static <E> List<E> difference(Collection<E> source, Collection<E> remove) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<>(0);
        }

        if (CollectionUtils.isEmpty(remove)) {
            return new ArrayList<>(source);
        }

        final Map<E, Boolean> elementMap = new HashMap<>(remove.size());
        for (E element : remove) {
            elementMap.put(element, true);
        }

        List<E> result = new ArrayList<>(source);
        result.removeIf(next -> BooleanUtils.isTrue(elementMap.get(next)));
        return result;
    }

    /**
     * 取两个集合的交集
     *
     * @param source 第一个集合
     * @param remove 第二个集合
     * @param <E>    对象
     * @return 交集
     */
    public static <E> List<E> intersection(Collection<E> source, Collection<E> remove) {
        if (CollectionUtils.isEmpty(source)) {
            return new ArrayList<E>(0);
        }

        if (CollectionUtils.isEmpty(remove)) {
            return new ArrayList<E>(0);
        }

        Map<E, Integer> elementMap = new HashMap<E, Integer>(remove.size());
        for (E element : remove) {
            Integer cnt = elementMap.get(element);
            if (cnt == null) {
                cnt = 0;
            }

            elementMap.put(element, ++cnt);
        }

        List<E> result = new ArrayList<E>(source);
        Iterator<E> iterator = result.iterator();
        while (iterator.hasNext()) {
            E next = iterator.next();
            Integer cnt = elementMap.get(next);
            if (cnt != null && cnt != 0) {
                elementMap.put(next, --cnt);
            } else {
                iterator.remove();
            }
        }

        return result;
    }

}
