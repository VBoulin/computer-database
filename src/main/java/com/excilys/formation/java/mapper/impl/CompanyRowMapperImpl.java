package com.excilys.formation.java.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.model.Company;

public class CompanyRowMapperImpl implements RowMapper<Company> {

  private Logger logger = LoggerFactory.getLogger(CompanyRowMapperImpl.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
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

}
