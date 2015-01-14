package com.excilys.formation.java.model;

import java.util.List;

public class Page<T> {

  private int     pageNumber;

  private List<T> list;

  private int     nbResultsPerPage;

  private int     nbResults;

  //------------------------------
  //Constructors
  //------------------------------
  public Page() {
    pageNumber = 1;
    nbResultsPerPage = 10;
  }

  public Page(int pageNumber, List<T> list, int nbResultsPerPage, int nbResults) {
    this.pageNumber = pageNumber;
    this.list = list;
    this.nbResultsPerPage = nbResultsPerPage;
    this.nbResults = nbResults;
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
    Page other = (Page) obj;
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
    return "Page [pageNumber=" + pageNumber + ", list=" + list + ", nbResultsPerPage="
        + nbResultsPerPage + ", nbResults=" + nbResults + "]";
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
  public boolean previousPage() {
    if (pageNumber > 1) {
      pageNumber--;
      return true;
    }
    return false;
  }
}