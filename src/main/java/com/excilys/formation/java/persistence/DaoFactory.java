package com.excilys.formation.java.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.persistence.impl.CompanyDaoImpl;
import com.excilys.formation.java.persistence.impl.ComputerDaoImpl;

public enum DaoFactory {

  INSTANCE;

  private static final String URL;
  private static final String USR;
  private static final String PASSWORD;

  private static Logger       logger = LoggerFactory.getLogger(DaoFactory.class);

  private static CompanyDao   companyDao;
  private static ComputerDao  computerDao;

  static {
    Properties prop = new Properties();
    InputStream stream = null;
    try {
      stream = DaoFactory.class.getClassLoader().getResourceAsStream("db.properties");
      prop.load(stream);

      //Load the Driver class
      Class.forName(prop.getProperty("db.driver"));
      URL = prop.getProperty("db.url");
      USR = prop.getProperty("db.usr");
      PASSWORD = prop.getProperty("db.password");
      logger.info("Properties loaded with success!");
    } catch (final IOException e) {
      logger.error("Couldn't load db.properties");
      throw new PersistenceException(e.getMessage(), e);
    } catch (ClassNotFoundException e) {
      throw new PersistenceException(e.getMessage(), e);
    }
    companyDao = CompanyDaoImpl.INSTANCE;
    computerDao = ComputerDaoImpl.INSTANCE;
  }

  /**
   * Singleton : DAO
   */
  private DaoFactory() {}

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

  /**
   * RollBack
   * @param conn
   */
  public void doRollback(Connection conn) {
    if (conn != null) {
      try {
        conn.rollback();
      } catch (SQLException e) {
        logger.error("Couldn't Rollback");
        throw new PersistenceException(e.getMessage(), e);
      }
    }
  }
}
