package com.excilys.formation.java.dto;

public class ComputerDto {

  private long   idComputer;
  private String computerName;
  private String introduced;
  private String discontinued;
  private long   idCompany;
  private String companyName;

  //------------------------------
  //Constructors
  //------------------------------

  public ComputerDto() {

  }

  //------------------------------
  //Getters & setters
  //------------------------------

  public long getIdComputer() {
    return idComputer;
  }

  public void setIdComputer(final long id) {
    this.idComputer = id;
  }

  public String getComputerName() {
    return computerName;
  }

  public void setComputerName(final String name) {
    this.computerName = name;
  }

  public String getIntroduced() {
    return introduced;
  }

  public void setIntroduced(final String introduced) {
    this.introduced = introduced;
  }

  public String getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(final String discontinued) {
    this.discontinued = discontinued;
  }

  public long getIdCompany() {
    return idCompany;
  }

  public void setIdCompany(final long company) {
    this.idCompany = company;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(final String companyName) {
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
    result = prime * result + ((computerName == null) ? 0 : computerName.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + (int) (idCompany ^ (idCompany >>> 32));
    result = prime * result + (int) (idComputer ^ (idComputer >>> 32));
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
    if (computerName == null) {
      if (other.computerName != null)
        return false;
    } else if (!computerName.equals(other.computerName))
      return false;
    if (discontinued == null) {
      if (other.discontinued != null)
        return false;
    } else if (!discontinued.equals(other.discontinued))
      return false;
    if (idCompany != other.idCompany)
      return false;
    if (idComputer != other.idComputer)
      return false;
    if (introduced == null) {
      if (other.introduced != null)
        return false;
    } else if (!introduced.equals(other.introduced))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return new StringBuilder("ComputerDto [idComputer=").append(idComputer)
        .append(", computerName=").append(computerName).append(", introduced=").append(introduced)
        .append(", discontinued=").append(discontinued).append(", idCompany=").append(idCompany)
        .append(", companyName=").append(companyName).append("]").toString();
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
      computerDTO.idComputer = id;
      return this;
    }

    public Builder name(String name) {
      computerDTO.computerName = name;
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