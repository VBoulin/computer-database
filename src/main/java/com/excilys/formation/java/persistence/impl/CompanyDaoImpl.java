package com.excilys.formation.java.persistence.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.java.mapper.impl.CompanyRowMapperImpl;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;

@Repository
public class CompanyDaoImpl implements CompanyDao {

  private Logger               logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

  private CompanyRowMapperImpl mapper = new CompanyRowMapperImpl();

  private JdbcTemplate         jdbcTemplate;

  @Autowired
  public void setDataSource(final DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  private static final String SELECT_ONE_COMPANY_QUERY = "SELECT * FROM company WHERE id = ?;";

  /**
   * {@inheritDoc}
   */
  public Company getOne(Long id) {

    if (id == null || id < 0) {
      logger.warn("getOne : Param id cannot be null or negative.");
      return null;
    } else {
      return jdbcTemplate.queryForObject(SELECT_ONE_COMPANY_QUERY, mapper, id);
    }
  }

  private static final String COUNT_QUERY     = "SELECT COUNT(id) AS total FROM company";
  private static final String PAGE_LIST_QUERY = "SELECT * FROM company LIMIT ? OFFSET ? ;";

  /**
   * {@inheritDoc}
   */
  public Page<Company> createPage(Page<Company> page) {

    if (page == null) {
      logger.warn("createPage : Param page cannot be null.");
      return null;
    }
    page.setNbResults(jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class));

    final Object[] o = new Object[] { page.getNbResultsPerPage(),
        (page.getPageNumber() - 1) * page.getNbResultsPerPage() };

    page.setList(jdbcTemplate.query(PAGE_LIST_QUERY, o, mapper));
    return page;
  }

  private static final String SELECT_ALL_COMPANIES_QUERY = "SELECT * FROM company;";

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Company> getAll() {
    return jdbcTemplate.query(SELECT_ALL_COMPANIES_QUERY, mapper);
  }

  private static final String DELETE_QUERY = "DELETE company FROM company WHERE id = ?";

  /**
   * {@inheritDoc}
   */
  public void delete(Long id) {
    if (id == null || id < 0) {
      logger.warn("delete : Param id cannot be null or negative.");
    } else {
      jdbcTemplate.update(DELETE_QUERY, id);
    }
  }

}
