package com.excilys.formation.java.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.mapper.impl.CompanyRowMapperImpl;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.DaoFactory;

public enum CompanyDaoImpl implements CompanyDao {

  INSTANCE;

  private Logger               logger = LoggerFactory.getLogger(CompanyDaoImpl.class);

  private CompanyRowMapperImpl mapper = new CompanyRowMapperImpl();

  /**
   * Singleton : provide the access service to the database (company)
   */
  private CompanyDaoImpl() {}

  private static final String SELECT_ONE_COMPANY_QUERY = "SELECT * FROM company WHERE id = ?;";

  /**
   * {@inheritDoc}
   */
  public Company getOne(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Company company = null;

    try {
      conn = DaoFactory.INSTANCE.getConnection();
      stmt = conn.prepareStatement(SELECT_ONE_COMPANY_QUERY);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();

      if (rs.next()) {
        company = mapper.mapRow(rs);
      }

    } catch (SQLException e) {
      logger.error("SQLError with getOne()");
      throw new PersistenceException(e.getMessage(), e);
    } catch (NullPointerException ne) {
      logger.error("NullError with getOne()");
      throw new PersistenceException(ne.getMessage(), ne);
    } finally {
      //Close the connection
      DaoFactory.INSTANCE.closeConnection(conn, stmt, rs);
    }
    return company;
  }

  private static final String COUNT_QUERY     = "SELECT COUNT(id) AS total FROM company";
  private static final String PAGE_LIST_QUERY = "SELECT * FROM company LIMIT ? OFFSET ? ;";

  /**
   * {@inheritDoc}
   */
  public Page<Company> createPage(Page<Company> page) {

    Connection conn = null;
    PreparedStatement stmt = null;
    Statement countStmt = null;
    ResultSet rs = null;
    List<Company> companies;

    try {
      conn = DaoFactory.INSTANCE.getConnection();

      countStmt = conn.createStatement();

      rs = countStmt.executeQuery(COUNT_QUERY);
      rs.next();
      page.setNbResults(rs.getInt("total"));

      stmt = conn.prepareStatement(PAGE_LIST_QUERY);
      stmt.setInt(1, page.getNbResultsPerPage());
      stmt.setInt(2, (page.getPageNumber() - 1) * page.getNbResultsPerPage());

      rs = stmt.executeQuery();

      companies = mapper.mapRowList(rs);

      page.setList(companies);

    } catch (SQLException e) {
      logger.error("SQLError with createPage()");
      throw new PersistenceException(e.getMessage(), e);
    } catch (NullPointerException ne) {
      logger.error("NullError with createPage()");
      throw new PersistenceException(ne.getMessage(), ne);
    } finally {
      //Close the connection
      DaoFactory.INSTANCE.closeConnection(conn, stmt, null);
    }
    return page;
  }

  private static final String SELECT_ALL_COMPANIES_QUERY = "SELECT * FROM company;";

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Company> getAll() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<Company> companies;

    try {
      conn = DaoFactory.INSTANCE.getConnection();

      stmt = conn.createStatement();

      rs = stmt.executeQuery(SELECT_ALL_COMPANIES_QUERY);

      companies = mapper.mapRowList(rs);

    } catch (SQLException e) {
      logger.error("SQLError with getAll()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.INSTANCE.closeConnection(conn, stmt, null);
    }
    return companies;
  }

  private static final String DELETE_QUERY = "DELETE company FROM company WHERE id = ?";

  /**
   * {@inheritDoc}
   */
  public void delete(Long id, Connection connection) {
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(DELETE_QUERY);
      statement.setLong(1, id);
      statement.executeUpdate();
    } catch (final SQLException e) {
      logger.error("SQLError with delete()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      DaoFactory.INSTANCE.closeConnection(null, statement, null);
    }
  }

}
