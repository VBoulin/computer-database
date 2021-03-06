package com.excilys.formation.java.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model for a company
 * @author Vincent
 *
 */
@Entity
@Table(name="company")
public class Company {
  @Id
  @Column(name="id")
  private Long   id;
  @Column(name="name")
  private String name;

  //------------------------------
  //Constructors
  //------------------------------
  public Company() {
    super();
  }

  public Company(Long id, String name) {
    super();
    this.id = id;
    this.name = name;
  }

  //------------------------------
  //Getters and setters
  //------------------------------
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  //------------------------------
  //hashcode & equals & toString
  //------------------------------
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Company other = (Company) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Company [id=");
    sb.append(id).append(", name=").append(name).append("]");
    return sb.toString();
  }
  
  //------------------------------
  //Builder
  //------------------------------

  public static Builder builder() {
	    return new Builder();
  }
  
  public static class Builder {
	    private Company c;

	    private Builder() {
	      c = new Company();
	    }

	    public Builder id(Long id) {
	      c.id = id;
	      return this;
	    }

	    public Builder name(String name) {
	      c.name = name;
	      return this;
	    }

	    public Company build() {
	      return c;
	    }
	  }
}
