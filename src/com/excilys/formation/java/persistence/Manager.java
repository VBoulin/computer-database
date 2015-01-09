package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager {

  public Manager() {
    super();
    // TODO Auto-generated constructor stub
  }

  public static Connection getConnection(String url, String usr, String password) {
    Connection conn = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(url, usr, password);
    } catch (ClassNotFoundException | SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return conn;
  }

  public static void closeConnection(Connection conn, Statement stmt) {
    closeConnection(conn, stmt, null);
}
  
  public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
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
