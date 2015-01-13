package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoFactory {

  private final static DaoFactory factory = new DaoFactory();

  private CompanyDao              companyDao;
  private ComputerDao             computerDao;

  private DaoFactory() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    companyDao = new CompanyDao();
    computerDao = new ComputerDao();
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

  public Connection getConnection(String url, String usr, String password) {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url, usr, password);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return conn;
  }

  public void closeConnection(Connection conn, Statement stmt) {
    closeConnection(conn, stmt, null);
  }

  public void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
    try {
      if (conn != null)
        conn.close();
      if (stmt != null)
        stmt.close();
      if (rs != null)
        rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
