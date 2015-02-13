package com.excilys.formation.java.controller;

import org.springframework.data.domain.Sort;

public enum SortBy {

    COMPUTER_NAME_ASC(new Sort(Sort.Direction.ASC, "name"), "name", "ASC"), 
    COMPUTER_NAME_DESC(new Sort(Sort.Direction.DESC, "name"), "name", "DESC"), 
    INTRODUCED_DATE_ASC(new Sort(Sort.Direction.ASC, "introduced"), "introduced", "ASC"), 
    INTRODUCED_DATE_DESC(new Sort(Sort.Direction.DESC, "introduced"), "introduced", "DESC"), 
    DISCONTINUED_DATE_ASC(new Sort(Sort.Direction.ASC, "discontinued"), "discontinued", "ASC"), 
    DISCONTINUED_DATE_DESC(new Sort(Sort.Direction.DESC, "discontinued"), "discontinued", "DESC"), 
    COMPANY_NAME_ASC(new Sort(Sort.Direction.ASC, "company.name"), "company.name", "ASC"), 
    COMPANY_NAME_DESC(new Sort(Sort.Direction.DESC, "company.name"), "company.name", "DESC");
    
    private Sort value;
    private String sort = "name";
    private String order = "ASC";

    
    private SortBy(Sort value, String sort, String order) {
      this.value = value;
      this.sort = sort;
      this.order = order;
    }

    public Sort getValue() {
      return value;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }
    
    public static SortBy getSortBy(Sort sort) {
        if (sort == null) {
            return null;
        }
        SortBy[] list = SortBy.values();
        for (SortBy sortBy : list) {
            if (sortBy.getValue().equals(sort)) {
                return sortBy;
            }
        }
        return null;
    }
}