package com.excilys.formation.java.persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.mapper.RowMapper;
import com.excilys.formation.java.mapper.impl.ComputerRowMapperImpl;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;
import com.excilys.formation.java.persistence.DaoManager;

@Repository
public class ComputerDaoImpl implements ComputerDao {

  private RowMapper<Computer> mapper = new ComputerRowMapperImpl();

  private Logger              logger = LoggerFactory.getLogger(ComputerDaoImpl.class);
  
  @Autowired
  private DataSource dataSource;

  /**
   * Singleton : provide the access service to the database (company)
   */
  public ComputerDaoImpl() {}

  private static final String CREATE_QUERY = "INSERT INTO computer(name, introduced, discontinued, company_id)  VALUE (?, ?, ?, ?);";

  /**
   * {@inheritDoc}
   */
  public void create(Computer o) {
    Connection conn = null;
    PreparedStatement stmt = null;

    if (o == null) {
      logger.warn("create : Param computer o cannot be null.");
    } else {
      try {
        conn = DataSourceUtils.getConnection(dataSource);
        
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
      }
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

    if (id == null || id < 0) {
      logger.warn("getOne : Param id cannot be null or negative.");
      return null;
    }

    try {
      conn = DataSourceUtils.getConnection(dataSource);
      
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

    if (o == null) {
      logger.warn("update : Param computer o cannot be null.");
    } else {
      try {
        conn = DataSourceUtils.getConnection(dataSource);
        
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
      } 
    }
  }

  private static final String DELETE_QUERY = "DELETE FROM computer WHERE id = ?;";

  /**
   * {@inheritDoc}
   */
  public void delete(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;

    if (id == null || id < 0) {
      logger.warn("getOne : Param id cannot be null or negative.");
    } else {
      try {
        conn = DataSourceUtils.getConnection(dataSource);
        
        stmt = conn.prepareStatement(DELETE_QUERY);
        stmt.setLong(1, id);
        stmt.executeUpdate();
      } catch (SQLException e) {
        logger.error("SQLError with delete()");
        throw new PersistenceException(e.getMessage(), e);
      } catch (NullPointerException ne) {
        logger.error("NullError with delete()");
        throw new PersistenceException(ne.getMessage(), ne);
      } 
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

    if (page == null) {
      logger.warn("createpage : Param page cannot be null.");
      return null;
    }

    String query = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id WHERE c.name LIKE ? OR cp.name LIKE ? ORDER BY "
        + page.getSort().getColumn() + " " + page.getOrder().getOrder() + " LIMIT ? OFFSET ? ;";

    StringBuilder search = new StringBuilder("%").append(page.getSearch()).append("%");
    
    try {
      conn = DataSourceUtils.getConnection(dataSource);

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
    } 
    return page;
  }

  private static final String SELECT_ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id ;";

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Computer> getAll() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Computer> computers;

    try {
      conn = DataSourceUtils.getConnection(dataSource);

      stmt = conn.prepareStatement(SELECT_ALL_COMPUTERS);

      rs = stmt.executeQuery();

      computers = mapper.mapRowList(rs);

    } catch (SQLException e) {
      logger.error("SQLError with getAll()");
      throw new PersistenceException(e.getMessage(), e);
    }
    return computers;
  }

  private static final String DELETE_COMPANY_QUERY = "DELETE computer FROM computer WHERE computer.company_id = ?";

  /**
   * {@inheritDoc}
   */
  public void deleteByCompany(Long id) {
    PreparedStatement stmt = null;
    Connection conn = null;
    if (id == null || id < 0) {
      logger.warn("deleteByCompany : Param id cannot be null or negative.");
    } else {
      conn = DataSourceUtils.getConnection(dataSource);
      
      try {
        stmt = conn.prepareStatement(DELETE_COMPANY_QUERY);
        stmt.setLong(1, id);
        stmt.executeUpdate();
      } catch (final SQLException e) {
        logger.error("SQLError with deleteByCompany()");
        throw new PersistenceException(e.getMessage(), e);
      } 
    }
  }

}
