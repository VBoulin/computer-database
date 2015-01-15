package com.excilys.formation.java.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.persistence.mapper.impl.ComputerRowMapperImpl;

public class ComputerDaoImpl implements ComputerDao {

  private static final String          URL             = "jdbc:mysql://localhost:3306/computer-database-db";
  private static final String          USR             = "admincdb";
  private static final String          PASSWORD        = "qwerty1234";

  private Logger                       logger          = LoggerFactory
                                                           .getLogger("com.excilys.formation.java.persistence.impl.ComputerDaoImpl");

  private final static ComputerDaoImpl computerDaoImpl = new ComputerDaoImpl();

  /**
   * Singleton : provide the access service to the database (company)
   */
  private ComputerDaoImpl() {}

  public static ComputerDaoImpl getInstance() {
    return computerDaoImpl;
  }

  /**
   * Add one computer in the database
   * @param o : computer that needs to be added to the database
   */
  public void create(Computer o) {
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "INSERT INTO computer(name, introduced, discontinued, company_id)  VALUE (?, ?, ?, ?);";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setString(1, o.getName());
      if (o.getIntroduced() == null) {
        stmt.setTimestamp(2, null);
      } else {
        stmt.setTimestamp(2, Timestamp.valueOf(o.getIntroduced().atStartOfDay()));
      }
      if (o.getDiscontinued() == null) {
        stmt.setTimestamp(3, null);
      } else {
        stmt.setTimestamp(3, Timestamp.valueOf(o.getDiscontinued().atStartOfDay()));
      }
      if (o.getCompany() == null) {
        stmt.setString(4, null);
      } else {
        stmt.setLong(4, o.getCompany().getId());
      }
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("SQLError with creation");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
  }

  /**
   * Retrieve one computer from the database
   * @param id Id of the computer
   * @return the computer asked or null
   */
  public Computer getOne(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Computer computer = null;

    String query = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id WHERE c.id = ?";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();

      ComputerRowMapperImpl mapper = new ComputerRowMapperImpl();
      computer = mapper.mapRow(rs);

    } catch (SQLException e) {
      logger.error("SQLError with getOne()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.getInstance().closeConnection(conn, stmt, rs);
    }
    return computer;
  }

  /**
   * Update one computer
   * @param o computer that needs to be updated in the database
   */
  public void update(Computer o) {
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setString(1, o.getName());
      if (o.getIntroduced() == null) {
        stmt.setTimestamp(2, null);
      } else {
        stmt.setTimestamp(2, Timestamp.valueOf(o.getIntroduced().atStartOfDay()));
      }
      if (o.getDiscontinued() == null) {
        stmt.setTimestamp(3, null);
      } else {
        stmt.setTimestamp(3, Timestamp.valueOf(o.getDiscontinued().atStartOfDay()));
      }
      if (o.getCompany() == null) {
        stmt.setString(4, null);
      } else {
        stmt.setLong(4, o.getCompany().getId());
      }
      stmt.setLong(5, o.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("SQLError with update()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
  }

  /**
   * Delete one computer
   * @param id Id of the computer that needs to be deleted
   */
  public void delete(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "DELETE FROM computer WHERE id = ?;";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("SQLError with delete()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
  }

  /**
   * Create one page by requesting the necessary informations
   * @param page Previous page
   * @return page Next page requested containing all the necessary informations
   */
  public Page<Computer> createPage(Page<Computer> page) {
    Computer computer;
    Connection conn = null;
    PreparedStatement stmt = null;
    Statement countStmt = null;
    ResultSet rs = null;
    List<Computer> computers;

    String countQuery = "SELECT COUNT(id) as total FROM computer";
    String query = "SELECT * FROM computer LIMIT ? OFFSET ? ;";

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

      ComputerRowMapperImpl mapper = new ComputerRowMapperImpl();
      computers = mapper.mapRowList(rs);

      page.setList(computers);

    } catch (SQLException e) {
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      logger.error("SQLError with getPagedList()");
      //Close the connection
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
    return page;
  }

}
