package com.excilys.formation.java.model;

import java.util.List;

public class PageWrapper<T> {

  private int     pageNumber;
  private List<T> list;
  private int     nbResultsPerPage;
  private int     nbResults;
  private int     nbTotalPage;
  private String  search;
  private OrderBy order;
  private SortBy  sort;

  //------------------------------
  //Constructors
  //------------------------------
  public PageWrapper() {
    pageNumber = 1;
    nbResultsPerPage = 10;
    sort=SortBy.ID;
    order=OrderBy.ASC;
    search="";
  }

  public PageWrapper(int pageNumber, List<T> list, int nbResultsPerPage, int nbResults) {
    this.pageNumber = pageNumber;
    this.list = list;
    this.nbResultsPerPage = nbResultsPerPage;
    this.nbResults = nbResults;
    sort=SortBy.ID;
    order=OrderBy.ASC;
    search="";
  }

  //------------------------------
  //Getters & setters
  //------------------------------

  public int getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public int getNbResultsPerPage() {
    return nbResultsPerPage;
  }

  public void setNbResultsPerPage(int nbResultsPerPage) {
    this.nbResultsPerPage = nbResultsPerPage;
  }

  public int getNbResults() {
    return nbResults;
  }

  public void setNbResults(int nbResults) {
    this.nbResults = nbResults;
  }

  public String getSearch() {
    return search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  public OrderBy getOrder() {
    return order;
  }

  public void setOrder(OrderBy order) {
    this.order = order;
  }

  public SortBy getSort() {
    return sort;
  }

  public void setSort(SortBy sort) {
    this.sort = sort;
  }

  //------------------------------
  //hashcode & equals & toString
  //------------------------------
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((list == null) ? 0 : list.hashCode());
    result = prime * result + nbResults;
    result = prime * result + nbResultsPerPage;
    result = prime * result + pageNumber;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PageWrapper other = (PageWrapper) obj;
    if (list == null) {
      if (other.list != null)
        return false;
    } else if (!list.equals(other.list))
      return false;
    if (nbResults != other.nbResults)
      return false;
    if (nbResultsPerPage != other.nbResultsPerPage)
      return false;
    if (pageNumber != other.pageNumber)
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Page [pageNumber=");
    builder.append(pageNumber);
    builder.append(", list=");
    builder.append(list);
    builder.append(", nbResultsPerPage=");
    builder.append(nbResultsPerPage);
    builder.append(", nbResults=");
    builder.append(nbResults);
    builder.append(", nbTotalPage=");
    builder.append(nbTotalPage);
    builder.append(", search=");
    builder.append(search);
    builder.append(", order=");
    builder.append(order);
    builder.append(", sort=");
    builder.append(sort);
    builder.append("]");
    return builder.toString();
  }
  
  //------------------------------
  //Other functions
  //------------------------------

  /**
   * Check if there is a next page
   * @return True : Page exist | False : otherwise
   */
  public boolean nextPage() {
    if (pageNumber * nbResultsPerPage < nbResults) {
      pageNumber++;
      return true;
    }
    return false;
  }

  /**
   * Check if there is a previous page
   * @return True : Page exist | False : otherwise
   */
  public boolean previousPageOrFirst() {
    if (pageNumber > 1) {
      pageNumber--;
      return true;
    }
    return false;
  }

  /**
   * Calculate the total number of pages
   * @return the total number of pages
   */
  public int getNbTotalPage() {
    if (nbResultsPerPage != 0) {
      nbTotalPage = nbResults / nbResultsPerPage;
      if (nbResults % nbResultsPerPage != 0) {
        nbTotalPage++;
      }
    }
    return nbTotalPage;
  }

}