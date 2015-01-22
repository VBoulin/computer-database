package com.excilys.formation.java.model;

public enum SortBy {
  ID("id"),
  COMPUTER_NAME("name"),
  INTRODUCED("introduced"),
  DISCONTINUED("discontinued"),
  COMPANY_NAME("cpName");
  

  private String column;
  
  private SortBy(String column) {
      this.column = column;
  }
  
  public String getColumn() {
      return column;
  }
  
  public static SortBy getInstance(String columnName) {
      if (columnName != null) {
          switch(columnName) {
          case "id":
              return ID;
          case "name":
              return COMPUTER_NAME;
          case "introduced":
              return INTRODUCED;
          case "discontinued":
              return DISCONTINUED;
          case "company_name":
              return COMPANY_NAME;
          default:
              break;
          }
      }
      return null;
  }
}
