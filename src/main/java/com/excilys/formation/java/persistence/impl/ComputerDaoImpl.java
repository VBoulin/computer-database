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
import com.excilys.formation.java.mapper.RowMapper;
import com.excilys.formation.java.mapper.impl.ComputerRowMapperImpl;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoFactory;

public enum ComputerDaoImpl implements ComputerDao {

  INSTANCE;

  private RowMapper<Computer> mapper = new ComputerRowMapperImpl();

  private Logger              logger = LoggerFactory.getLogger(ComputerDaoImpl.class);

  /**
   * Singleton : provide the access service to the database (company)
   */
  private ComputerDaoImpl() {}

  private static final String CREATE_QUERY = "INSERT INTO computer(name, introduced, discontinued, company_id)  VALUE (?, ?, ?, ?);";

  /**
   * {@inheritDoc}
   */
  public void create(Computer o) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
      conn = DaoFactory.INSTANCE.getConnection();
      stmt = conn.prepareStatement(CREATE_QUERY);
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
      logger.error("SQLError with create()");
      throw new PersistenceException(e.getMessage(), e);
    } catch (NullPointerException ne) {
      logger.error("NullError with create()");
      throw new PersistenceException(ne.getMessage(), ne);
    } finally {
      //Close the connection
      DaoFactory.INSTANCE.closeConnection(conn, stmt, null);
    }
  }

  private static final String SELECT_ONE_COMPUTER_QUERY = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id WHERE c.id = ?";

  /**
   * {@inheritDoc}
   */
  public Computer getOne(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Computer computer = null;

    try {
      conn = DaoFactory.INSTANCE.getConnection();
      stmt = conn.prepareStatement(SELECT_ONE_COMPUTER_QUERY);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();

      if (rs.next()) {
        computer = mapper.mapRow(rs);
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
    return computer;
  }

  private static final String UPDATE_QUERY = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";

  /**
   * {@inheritDoc}
   */
  public void update(Computer o) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
      conn = DaoFactory.INSTANCE.getConnection();
      stmt = conn.prepareStatement(UPDATE_QUERY);
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
    } catch (NullPointerException ne) {
      logger.error("NullError with update()");
      throw new PersistenceException(ne.getMessage(), ne);
    } finally {
      //Close the connection
      DaoFactory.INSTANCE.closeConnection(conn, stmt, null);
    }
  }

  private static final String DELETE_QUERY = "DELETE FROM computer WHERE id = ?;";

  /**
   * {@inheritDoc}
   */
  public void delete(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
      conn = DaoFactory.INSTANCE.getConnection();
      stmt = conn.prepareStatement(DELETE_QUERY);
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("SQLError with delete()");
      throw new PersistenceException(e.getMessage(), e);
    } catch (NullPointerException ne) {
      logger.error("NullError with delete()");
      throw new PersistenceException(ne.getMessage(), ne);
    } finally {
      //Close the connection
      DaoFactory.INSTANCE.closeConnection(conn, stmt, null);
    }
  }

  private static final String COUNT_QUERY = "SELECT COUNT(id) as total FROM computer";

  /**
   * {@inheritDoc}
   */
  public Page<Computer> createPage(Page<Computer> page) {
    Connection conn = null;
    PreparedStatement stmt = null;
    Statement countStmt = null;
    ResultSet rs = null;
    List<Computer> computers;

    String query = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id WHERE c.name LIKE ? OR cp.name LIKE ? ORDER BY "
        + page.getSort().getColumn() + " " + page.getOrder().getOrder() + " LIMIT ? OFFSET ? ;";

    StringBuilder search = new StringBuilder("%").append(page.getSearch()).append("%");
    try {
      conn = DaoFactory.INSTANCE.getConnection();

      countStmt = conn.createStatement();

      rs = countStmt.executeQuery(COUNT_QUERY);
      rs.next();
      page.setNbResults(rs.getInt("total"));

      stmt = conn.prepareStatement(query);
      stmt.setString(1, search.toString());
      stmt.setString(2, search.toString());
      stmt.setInt(3, page.getNbResultsPerPage());
      stmt.setInt(4, (page.getPageNumber() - 1) * page.getNbResultsPerPage());

      rs = stmt.executeQuery();

      computers = mapper.mapRowList(rs);

      page.setList(computers);

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

  private static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id ;";

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Computer> getAll() {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Computer> computers;

    try {
      conn = DaoFactory.INSTANCE.getConnection();

      stmt = conn.prepareStatement(SELECT_ALL_COMPUTERS);

      rs = stmt.executeQuery();

      computers = mapper.mapRowList(rs);

    } catch (SQLException e) {
      logger.error("SQLError with getAll()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      DaoFactory.INSTANCE.closeConnection(conn, stmt, null);
    }
    return computers;
  }

  private static final String DELETE_COMPANY_QUERY = "DELETE computer FROM computer WHERE computer.company_id = ?";

  /**
   * {@inheritDoc}
   */
  public void deleteByCompany(Long id, Connection conn) {
    PreparedStatement stmt = null;
    try {
      stmt = conn.prepareStatement(DELETE_COMPANY_QUERY);
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (final SQLException e) {
      logger.error("SQLError with deleteByCompany()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      DaoFactory.INSTANCE.closeConnection(null, stmt, null);
    }

  }

}
