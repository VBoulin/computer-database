package com.excilys.formation.java.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="computer")
public class Computer{
  @Id
  @GeneratedValue
  @Column(name="id")
  private Long      id;
  @Column(name="name")
  private String    name;
  @Column(name="introduced")
  @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
  private LocalDate introduced;
  @Column(name="discontinued")
  @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
  private LocalDate discontinued;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="company_id", referencedColumnName="id")
  private Company   company;

  //------------------------------
  //Constructors
  //------------------------------
  public Computer() {
    super();
  }

  public Computer(String name) {
    super();
    this.name = name;
  }

  public Computer(long id, String name, LocalDate introduced, LocalDate discontinued,
      Company company) {
    super();
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = company;
  }

  //------------------------------
  //Getters & setters
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

  public LocalDate getIntroduced() {
    return introduced;
  }

  public void setIntroduced(LocalDate introduced) {
    this.introduced = introduced;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(LocalDate discontinued) {
    this.discontinued = discontinued;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  //------------------------------
  //hashcode & equals & toString
  //------------------------------
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
    Computer other = (Computer) obj;
    if (company == null) {
      if (other.company != null)
        return false;
    } else if (!company.equals(other.company))
      return false;
    if (discontinued == null) {
      if (other.discontinued != null)
        return false;
    } else if (!discontinued.equals(other.discontinued))
      return false;
    if (id != other.id)
      return false;
    if (introduced == null) {
      if (other.introduced != null)
        return false;
    } else if (!introduced.equals(other.introduced))
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
    StringBuilder sb = new StringBuilder("Computer [id=");
    sb.append(id).append(", name=").append(name).append(", introduced=").append(introduced);
    sb.append(", discontinued=").append(discontinued).append(", company=").append(company)
        .append("]");
    return sb.toString();
  }

  //------------------------------
  //Builder
  //------------------------------
  public static Builder builder(String name) {
    return new Builder(name);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private Computer c;

    private Builder() {
      c = new Computer();
    }

    private Builder(String name) {
      c = new Computer(name);
    }

    public Builder id(Long id) {
      c.id = id;
      return this;
    }

    public Builder name(String name) {
      c.name = name;
      return this;
    }

    public Builder introduced(LocalDate introduced) {
      c.introduced = introduced;
      return this;
    }

    public Builder discontinued(LocalDate discontinued) {
      c.discontinued = discontinued;
      return this;
    }

    public Builder company(Company company) {
      c.company = company;
      return this;
    }

    public Computer build() {
      return c;
    }
  }
}
