package com.excilys.formation.java.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import com.excilys.formation.java.validator.DateValidator;

public class ComputerDto {

  @Min(value = 0, message = "Incorrect Computer id")
  private long   id;
  @NotNull(message = "{error.name}")
  @NotBlank(message = "{error.name}")
  private String name;
  @DateValidator(message = "{error.introduced}")
  private String introduced;
  @DateValidator(message = "{error.discontinued}")
  private String discontinued;
  @Min(value = 0, message = "{error.companyId}")
  private long idCompany;
  private String companyName;

  //------------------------------
  //Constructors
  //------------------------------

  public ComputerDto() {

  }

  //------------------------------
  //Getters & setters
  //------------------------------

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIntroduced() {
    return introduced;
  }

  public void setIntroduced(String introduced) {
    this.introduced = introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(String discontinued) {
    this.discontinued = discontinued;
  }

  public long getIdCompany() {
    return idCompany;
  }

  public void setIdCompany(long company) {
    this.idCompany = company;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  //------------------------------
  //hashcode & equals & toString
  //------------------------------

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (id ^ (id >>> 32));
    result = prime * result + (int) (idCompany ^ (idCompany >>> 32));
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
    ComputerDto other = (ComputerDto) obj;
    if (companyName == null) {
      if (other.companyName != null)
        return false;
    } else if (!companyName.equals(other.companyName))
      return false;
    if (discontinued == null) {
      if (other.discontinued != null)
        return false;
    } else if (!discontinued.equals(other.discontinued))
      return false;
    if (id != other.id)
      return false;
    if (idCompany != other.idCompany)
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
    return new StringBuilder("ComputerDto [idComputer=").append(id).append(", computerName=")
        .append(name).append(", introduced=").append(introduced).append(", discontinued=")
        .append(discontinued).append(", idCompany=").append(idCompany).append(", companyName=")
        .append(companyName).append("]").toString();
  }

  //------------------------------
  //Builder
  //------------------------------

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ComputerDto computerDTO;

    private Builder() {
      computerDTO = new ComputerDto();
    }

    public Builder id(long id) {
      computerDTO.id = id;
      return this;
    }

    public Builder name(String name) {
      computerDTO.name = name;
      return this;
    }

    public Builder introduced(String introduced) {
      computerDTO.introduced = introduced;
      return this;
    }

    public Builder discontinued(String discontinued) {
      computerDTO.discontinued = discontinued;
      return this;
    }

    public Builder idCompany(long idCompany) {
      computerDTO.idCompany = idCompany;
      return this;
    }

    public Builder companyName(String companyName) {
      computerDTO.companyName = companyName;
      return this;
    }

    public ComputerDto build() {
      return computerDTO;
    }
  }
}