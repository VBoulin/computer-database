package com.excilys.formation.persistence.java.test;

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

public class MockCompanyDao implements CompanyDao {
  
  //Connections informations 
  private static final String URL      = "jdbc:mysql://localhost:3306/test-computer-database-db";
  private static final String USR      = "admintcdb";
  private static final String PASSWORD = "qwerty12345";

  private Logger logger = LoggerFactory.getLogger("com.excilys.formation.persistence.java.test.MockCompanyDao");

  private final static MockCompanyDao companyDao = new MockCompanyDao();
  
  private CompanyRowMapperImpl mapper;

  /**
   * Singleton : provide the access service to the database (company)
   */
  private MockCompanyDao() {}

  public static MockCompanyDao getInstance() {
    return companyDao;
  }
  
  public void setMapper(CompanyRowMapperImpl mapper){
    this.mapper=mapper;
  }

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
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      
      company = mapper.mapRow(rs);
      
    } catch (SQLException e) {
      logger.error("SQLError with getOne()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.getInstance().closeConnection(conn, stmt, rs);
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
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);

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
      logger.error("SQLError with getPagedList()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
    return page;
  }

}
