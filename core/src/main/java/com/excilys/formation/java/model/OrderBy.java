package com.excilys.formation.java.model;

public enum OrderBy {
  ASC("ASC"),
  DESC("DESC");

  private String order;
  
  private OrderBy(String order) {
      this.order = order;
  }
  
  public String getOrder() {
      return order;
  }
  
  public static OrderBy getInstance(String orderName) {
      if (orderName != null) {
          switch(orderName) {
          case "ASC":
              return ASC;
          case "DESC":
              return DESC;
          default:
              break;
          }
      }
      return null;
  }
}
