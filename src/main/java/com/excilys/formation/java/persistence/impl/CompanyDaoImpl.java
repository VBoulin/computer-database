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
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.persistence.mapper.impl.CompanyRowMapperImpl;

public enum CompanyDaoImpl implements CompanyDao {

  INSTANCE;

  private Logger logger = LoggerFactory.getLogger(CompanyDaoImpl.class);
  
  private CompanyRowMapperImpl mapper = new CompanyRowMapperImpl();

  /**
   * Singleton : provide the access service to the database (company)
   */
  private CompanyDaoImpl() {}

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
      conn = DaoFactory.INSTANCE.getConnection();
      stmt = conn.prepareStatement(query);
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
    List<Company> companies;

    String countQuery = "SELECT COUNT(id) AS total FROM company";
    String query = "SELECT * FROM company LIMIT ? OFFSET ? ;";

    try {
      conn = DaoFactory.INSTANCE.getConnection();

      countStmt = conn.createStatement();

      rs = countStmt.executeQuery(countQuery);
      rs.next();
      page.setNbResults(rs.getInt("total"));

      stmt = conn.prepareStatement(query);
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

  @Override
  public List<Company> getAll() {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    List<Company> companies;

    String query = "SELECT * FROM company;";

    try {
      conn = DaoFactory.INSTANCE.getConnection();

      stmt = conn.createStatement();

      rs = stmt.executeQuery(query);

      CompanyRowMapperImpl mapper = new CompanyRowMapperImpl();
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

}
