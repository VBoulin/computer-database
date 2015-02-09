package com.excilys.formation.java.persistence.impl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.formation.java.mapper.impl.ComputerRowMapperImpl;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;

@Repository
public class ComputerDaoImpl implements ComputerDao {

  private ComputerRowMapperImpl mapper = new ComputerRowMapperImpl();

  private Logger                logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

  private JdbcTemplate          jdbcTemplate;

  @Autowired
  public void setDataSource(final DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  private static final String CREATE_QUERY = "INSERT INTO computer(name, introduced, discontinued, company_id)  VALUE (?, ?, ?, ?);";

  /**
   * {@inheritDoc}
   */
  public void create(Computer c) {
      LocalDate introduced = c.getIntroduced();
      LocalDate discontinued = c.getDiscontinued();

      Timestamp introducedTimeStamp = null;
      Timestamp discontinuedTimeStamp = null;

      if (introduced != null) {
        introducedTimeStamp = Timestamp.valueOf(introduced.atStartOfDay());
      }
      if (discontinued != null) {
        discontinuedTimeStamp = Timestamp.valueOf(discontinued.atStartOfDay());
      }

      Long companyId = null;

      if (c.getCompany() != null) {
        companyId = c.getCompany().getId();
      }

      Object[] o = new Object[] { c.getName(), introducedTimeStamp, discontinuedTimeStamp,
          companyId };

      jdbcTemplate.update(CREATE_QUERY, o);
  }

  private static final String SELECT_ONE_COMPUTER_QUERY = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id WHERE c.id = ?";

  /**
   * {@inheritDoc}
   */
  public Computer getOne(Long id) {
      return jdbcTemplate.queryForObject(SELECT_ONE_COMPUTER_QUERY, mapper, id);
  }

  private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";

  /**
   * {@inheritDoc}
   */
  public void update(Computer c) {
      LocalDate introduced = c.getIntroduced();
      LocalDate discontinued = c.getDiscontinued();

      Timestamp introducedTimeStamp = null;
      Timestamp discontinuedTimeStamp = null;

      if (introduced != null) {
        introducedTimeStamp = Timestamp.valueOf(introduced.atStartOfDay());
      }
      if (discontinued != null) {
        discontinuedTimeStamp = Timestamp.valueOf(discontinued.atStartOfDay());
      }

      Long companyId = null;

      if (c.getCompany() != null) {
        companyId = c.getCompany().getId();
      }

      Object[] o = new Object[] { c.getName(), introducedTimeStamp, discontinuedTimeStamp,
          companyId, c.getId() };

      jdbcTemplate.update(UPDATE_QUERY, o);
  }

  private static final String DELETE_QUERY = "DELETE FROM computer WHERE id = ?;";

  /**
   * {@inheritDoc}
   */
  public void delete(Long id) {
      jdbcTemplate.update(DELETE_QUERY, id);
  }

  private static final String COUNT_QUERY = "SELECT COUNT(id) as total FROM computer";

  /**
   * {@inheritDoc}
   */
  public Page<Computer> createPage(Page<Computer> page) {

    page.setNbResults(jdbcTemplate.queryForObject(COUNT_QUERY, Integer.class));

    String query = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id WHERE c.name LIKE ? OR cp.name LIKE ? ORDER BY "
        + page.getSort().getColumn() + " " + page.getOrder().getOrder() + " LIMIT ? OFFSET ? ;";

    StringBuilder search = new StringBuilder("%").append(page.getSearch()).append("%");

    final Object[] o = new Object[] { search, search, page.getNbResultsPerPage(),
        (page.getPageNumber() - 1) * page.getNbResultsPerPage() };

    page.setList(jdbcTemplate.query(query, o, mapper));
    return page;
  }

  private static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id ;";

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Computer> getAll() {
    return jdbcTemplate.query(SELECT_ALL_COMPUTERS, mapper);
  }

  private static final String DELETE_COMPANY_QUERY = "DELETE computer FROM computer WHERE computer.company_id = ?";

  /**
   * {@inheritDoc}
   */
  public void deleteByCompany(Long id) {
      jdbcTemplate.update(DELETE_COMPANY_QUERY, id);
  }

}
