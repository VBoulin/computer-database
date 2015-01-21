package com.excilys.formation.java.persistence.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.mapper.impl.CompanyRowMapperImpl;

public class MockCompanyDao implements CompanyDao {

  private Logger                      logger  = LoggerFactory.getLogger(MockCompanyDao.class);

  private final static MockDaoFactory mockDao = MockDaoFactory.getInstance();

  /**
   * Singleton : provide the access service to the database (company)
   */
  public MockCompanyDao() {}

  /**
   * retrieve one company from the database
   * @param id Id of the company
   * @return The company requested or null
   */
  public Company getOne(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Company company = null;

    String query = "SELECT * FROM company WHERE id = ?;";

    try {
      conn = MockDaoFactory.getInstance().getConnection();
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();

      while (rs.next()) {
        company = Company.builder().id(rs.getLong("id")).name(rs.getString("name")).build();
      }

    } catch (SQLException e) {
      logger.error("SQLError with getOne()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      mockDao.closeConnection(conn, stmt, rs);
    }
    return company;
  }

  /**
   * Create one page by requesting the necessary informations
   * @param page Previous page
   * @return page Next page requested containing all the necessary informations
   */
  public Page<Company> createPage(Page<Company> page) {

    Connection conn = null;
    PreparedStatement stmt = null;
    Statement countStmt = null;
    ResultSet rs = null;
    List<Company> companies = new ArrayList<Company>();

    String countQuery = "SELECT COUNT(id) AS total FROM company";
    String query = "SELECT * FROM company LIMIT ? OFFSET ? ;";

    try {
      conn = MockDaoFactory.getInstance().getConnection();

      countStmt = conn.createStatement();

      rs = countStmt.executeQuery(countQuery);
      rs.next();
      page.setNbResults(rs.getInt("total"));

      stmt = conn.prepareStatement(query);
      stmt.setInt(1, page.getNbResultsPerPage());
      stmt.setInt(2, (page.getPageNumber() - 1) * page.getNbResultsPerPage());

      rs = stmt.executeQuery();

      while (rs.next()) {
        companies.add(Company.builder().id(rs.getLong("id")).name(rs.getString("name")).build());
      }
      page.setList(companies);

    } catch (SQLException e) {
      logger.error("SQLError with getPagedList()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      mockDao.closeConnection(conn, stmt, null);
    }
    return page;
  }

  @Override
  public List<Company> getAll() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Company> companies = new ArrayList<Company>();

    String query = "SELECT * FROM company;";

    try {
      conn = MockDaoFactory.getInstance().getConnection();

      stmt = conn.prepareStatement(query);

      rs = stmt.executeQuery();

      while (rs.next()) {
        companies.add(Company.builder().id(rs.getLong("id")).name(rs.getString("name")).build());
      }

    } catch (SQLException e) {
      logger.error("SQLError with getPagedList()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      mockDao.closeConnection(conn, stmt, null);
    }
    return companies;
  }

}
