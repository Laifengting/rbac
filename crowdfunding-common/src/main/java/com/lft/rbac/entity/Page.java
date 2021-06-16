package com.lft.rbac.entity;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer startPage;
    private Integer currentPage;
    private Integer totalPages;
    private Integer sizeOfPage;
    private List<T> data;
    
    public Integer getStartPage() {
        return startPage;
    }
    
    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }
    
    public Integer getCurrentPage() {
        return currentPage;
    }
    
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    
    public Integer getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
    
    public Integer getSizeOfPage() {
        return sizeOfPage;
    }
    
    public void setSizeOfPage(Integer sizeOfPage) {
        this.sizeOfPage = sizeOfPage;
    }
    
    public List<T> getData() {
        return data;
    }
    
    public void setData(List<T> data) {
        this.data = data;
    }
    
}
