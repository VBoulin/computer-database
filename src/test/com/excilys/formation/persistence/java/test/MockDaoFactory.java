package com.excilys.formation.persistence.java.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.persistence.CompanyDao;
import com.excilys.formation.java.persistence.ComputerDao;

public class MockDaoFactory {

  private Logger logger = LoggerFactory.getLogger("com.excilys.formation.persistence.java.test.MockDaoFactory");

  //Singleton
  private final static MockDaoFactory factory = new MockDaoFactory();

  private static final String     DRIVER  = "com.mysql.jdbc.Driver";

  /**
   * Singleton : DAO
   */
  private MockDaoFactory() {
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException e) {
      throw new PersistenceException(e.getMessage(), e);
    }
  }

  public static MockDaoFactory getInstance() {
    return factory;
  }

  /**
   * Return the connection to the database
   * @param url url of the database
   * @param usr login to identify the usr
   * @param password password needed for the authentification
   * @return Return the connection to the database
   */
  public Connection getConnection(String url, String usr, String password) {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, usr, password);
    } catch (SQLException e) {
      logger.error("SQLError while creating connection");
      throw new PersistenceException(e.getMessage(), e);
    }
    return conn;
  }

  /**
   * Close the connection to the database
   */
  public void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (conn != null)
        conn.close();
      if (stmt != null)
        stmt.close();
      if (rs != null)
        rs.close();
    } catch (SQLException e) {
      logger.error("SQLError while closing connection");
      throw new PersistenceException(e.getMessage(), e);
    }
  }
}
