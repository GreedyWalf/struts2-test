package com.qs.common.utils;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    protected String id;
    protected int pageNo = 1;
    protected int pageSize = 10;
    protected boolean autoCount = true;
    protected boolean autoPaging = true;
    private String sortName;
    private String sortOrder;
    protected List<T> rows = new ArrayList();
    protected long total = 0L;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Page() {
    }

    public Page(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        if (pageNo < 1) {
            this.pageNo = 1;
        }

    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirst() {
        return (this.pageNo - 1) * this.pageSize + 1;
    }

    public boolean isAutoCount() {
        return this.autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page<T> autoCount(boolean theAutoCount) {
        this.setAutoCount(theAutoCount);
        return this;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> result) {
        this.rows = result;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long totalCount) {
        this.total = totalCount;
    }

    public long getTotalPages() {
        long count = this.total / (long) this.pageSize;
        if (this.total % (long) this.pageSize > 0L) {
            ++count;
        }

        return count;
    }

    public boolean isHasNext() {
        return (long) (this.pageNo + 1) <= this.getTotalPages();
    }

    public int getNextPage() {
        return this.isHasNext() ? this.pageNo + 1 : this.pageNo;
    }

    public boolean isHasPre() {
        return this.pageNo - 1 >= 1;
    }

    public int getPrePage() {
        return this.isHasPre() ? this.pageNo - 1 : this.pageNo;
    }

    public String getSortName() {
        return this.sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public boolean isAutoPaging() {
        return this.autoPaging;
    }

    public void setAutoPaging(boolean autoPaging) {
        this.autoPaging = autoPaging;
    }

    public String toString() {
        return "Page{total=" + this.total + ", pageNo=" + this.pageNo + ", pageSize=" + this.pageSize + ", autoCount=" + this.autoCount + ", sortName='" + this.sortName + '\'' + ", sortOrder='" + this.sortOrder + '\'' + '}';
    }
}
