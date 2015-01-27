package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

@Component
public class DaoManager {

  private static Logger           logger = LoggerFactory.getLogger(DaoManager.class);

  private static BoneCP           boneCpPool;

  private ThreadLocal<Connection> ThreadLocalConnection;

  @Value("${db.driver}")
  private String driver;
  
  @Value("${db.url}")
  private String url;
  
  @Value("${db.usr}")
  private String usr;
  
  @Value("${db.password}")
  private String password;
  
  @PostConstruct
  public void config() {
    try {

      //Load the Driver class
      Class.forName(driver);

      BoneCPConfig connectionConfig = new BoneCPConfig();

      connectionConfig.setJdbcUrl(url);
      connectionConfig.setUser(usr);
      connectionConfig.setPassword(password);

      connectionConfig.setMinConnectionsPerPartition(5);
      connectionConfig.setMaxConnectionsPerPartition(15);

      boneCpPool = new BoneCP(connectionConfig);

      logger.info("Properties loaded with success!");
    } catch (ClassNotFoundException e) {
      logger.error("Driver problems");
      throw new PersistenceException(e.getMessage(), e);
    } catch (SQLException e) {
      logger.error("Connection problems");
      throw new PersistenceException(e.getMessage(), e);
    }
  }
  /**
   * Singleton : DAO
   */
  public DaoManager() {}

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
      conn = boneCpPool.getConnection();
    } catch (SQLException e) {
      logger.error("SQLError while getting connection");
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

  /**
   * Start a transaction
   */
  public void startTransactionalConnection() {
    try {
      Connection conn = boneCpPool.getConnection();
      conn.setAutoCommit(false);
      ThreadLocalConnection = new ThreadLocal<Connection>();
      ThreadLocalConnection.set(conn);
    } catch (SQLException e) {
      logger.error("Couldn't connect to the database");
      throw new PersistenceException(e.getMessage(), e);
    }
  }

  /**
   * Return the connection to the database for a transaction
   * @return A connection
   */
  public Connection getTransactionnalConnection() {
    if (ThreadLocalConnection != null) {
      return ThreadLocalConnection.get();
    } else {
      return null;
    }
  }

  /**
   * Commit
   */
  public void commitTransactionalConnection() {
    if (ThreadLocalConnection != null) {
      try {
        ThreadLocalConnection.get().commit();
      } catch (final SQLException e) {
        logger.warn("Couldn't Commit the connection");
        throw new PersistenceException(e.getMessage(), e);
      }
    }
  }

  /**
   * Close the connection
   */
  public void closeTransactionalConnection() {
    if (ThreadLocalConnection != null) {
      try {
        ThreadLocalConnection.get().close();
      } catch (final SQLException e) {
        logger.warn("Couldn't Commit the connection");
        throw new PersistenceException(e.getMessage(), e);
      }
    }
  }
}
