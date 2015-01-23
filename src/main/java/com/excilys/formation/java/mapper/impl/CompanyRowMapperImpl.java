package com.excilys.formation.java.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.mapper.RowMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;

public class CompanyRowMapperImpl implements RowMapper<Company> {

  private Logger logger = LoggerFactory.getLogger(CompanyRowMapperImpl.class);

  @Override
  public Company mapRow(ResultSet rs) {
    // TODO Auto-generated method stub
    Company company = null;
    if (rs == null) {
      return null;
    }
    try {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        Company.Builder b = Company.builder();
        company = b.id(id).name(name).build();
    } catch (SQLException e) {
      logger.error("SQLException while mapping a company");
      throw new PersistenceException(e.getMessage(), e);
    }

    return company;
  }

  /**
     * Maps a list of element
     * @param resultSet The list of element that need to be mapped
     * @return A mapped instance of List<Company>
     */
  @Override
  public List<Company> mapRowList(ResultSet rs) {
    Company company = null;
    List<Company> companies = new ArrayList<Company>();

    if (rs == null) {
      return null;
    }
    try {
      while (rs.next()) {
        company = mapRow(rs);

        companies.add(company);
      }
    } catch (SQLException e) {
      logger.error("SQLException while mapping a list of companies");
      throw new PersistenceException(e.getMessage(), e);
    }

    return companies;
  }

}
