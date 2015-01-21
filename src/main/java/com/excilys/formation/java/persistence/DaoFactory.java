package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.persistence.impl.CompanyDaoImpl;
import com.excilys.formation.java.persistence.impl.ComputerDaoImpl;

public class DaoFactory {

  private static final String     URL      = "jdbc:mysql://localhost:3306/computer-database-db";
  private static final String     USR      = "admincdb";
  private static final String     PASSWORD = "qwerty1234";

  private static final String     DRIVER   = "com.mysql.jdbc.Driver";

  private Logger                  logger   = LoggerFactory.getLogger(DaoFactory.class);

  //Singleton
  private final static DaoFactory factory  = new DaoFactory();

  private CompanyDao              companyDao;
  private ComputerDao             computerDao;

  /**
   * Singleton : DAO
   */
  private DaoFactory() {
    try {
      Class.forName(DRIVER);
    } catch (ClassNotFoundException e) {
      throw new PersistenceException(e.getMessage(), e);
    }

    companyDao = CompanyDaoImpl.getInstance();
    computerDao = ComputerDaoImpl.getInstance();
  }

  public static DaoFactory getInstance() {
    return factory;
  }

  public CompanyDao getCompanyDao() {
    return companyDao;
  }

  public ComputerDao getComputerDao() {
    return computerDao;
  }

  /**
   * Return the connection to the database
   * @param url url of the database
   * @param usr login to identify the usr
   * @param password password needed for the authentification
   * @return Return the connection to the database
   */
  public Connection getConnection() {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(URL, USR, PASSWORD);
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
