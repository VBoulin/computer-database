package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Component
public class DaoManager {

  private static Logger           logger = LoggerFactory.getLogger(DaoManager.class);

  /**
   * Close the connection to the database
   */
  public void closeConnection(Connection conn) {
    try {
      if (conn != null)
        conn.close();
    } catch (SQLException e) {
      logger.error("SQLError while closing connection");
      throw new PersistenceException(e.getMessage(), e);
    }
  }
  /**
   * Close the connection to the database
   */
  public void closeConnection(Statement stmt) {
    try {
      if (stmt != null)
        stmt.close();
    } catch (SQLException e) {
      logger.error("SQLError while closing connection");
      throw new PersistenceException(e.getMessage(), e);
    }
  }
  /**
   * Close the connection to the database
   */
  public void closeConnection(Connection conn, Statement stmt) {
    try {
      if (conn != null)
        conn.close();
      if (stmt != null)
        stmt.close();
    } catch (SQLException e) {
      logger.error("SQLError while closing connection");
      throw new PersistenceException(e.getMessage(), e);
    }
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
