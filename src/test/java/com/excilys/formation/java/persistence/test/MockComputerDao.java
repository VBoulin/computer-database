package com.excilys.formation.java.persistence.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;

public class MockComputerDao implements ComputerDao {

  private Logger                      logger  = LoggerFactory.getLogger(MockComputerDao.class);

  private final static MockDaoFactory mockDao = MockDaoFactory.getInstance();

  @Override
  public Computer getOne(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Computer computer = null;
    Company company = null;

    String query = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cpId, cp.name AS cpName FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id WHERE c.id = ?";

    try {
      conn = MockDaoFactory.getInstance().getConnection();
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();

      if (rs == null) {
        return null;
      }
      while (rs.next()) {
        String name = rs.getString("name");
        LocalDate introduced = null;
        if (rs.getDate("introduced") != null)
          introduced = rs.getDate("introduced").toLocalDate();
        LocalDate discontinued = null;
        if (rs.getDate("discontinued") != null)
          discontinued = rs.getDate("discontinued").toLocalDate();
        Long companyId = rs.getLong("cpId");
        String companyName = rs.getString("cpName");

        if (companyId > 0) {
          Company.Builder bcp = Company.builder();
          company = bcp.id(companyId).name(companyName).build();
        }

        Computer.Builder b = Computer.builder();
        computer = b.id(id).name(name).discontinued(discontinued).introduced(introduced)
            .company(company).build();
      }

    } catch (SQLException e) {
      logger.error("SQLError with getOne()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      mockDao.closeConnection(conn, stmt, rs);
    }
    return computer;
  }

  @Override
  public void create(Computer o) {
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "INSERT INTO computer(name, introduced, discontinued, company_id)  VALUE (?, ?, ?, ?);";

    try {
      conn = MockDaoFactory.getInstance().getConnection();
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
      logger.error("SQLError with create()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      mockDao.closeConnection(conn, stmt, null);
    }
  }

  @Override
  public void update(Computer o) {
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";

    try {
      conn = MockDaoFactory.getInstance().getConnection();
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
      mockDao.closeConnection(conn, stmt, null);
    }
  }

  @Override
  public void delete(Long id) {
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "DELETE FROM computer WHERE id = ?;";

    try {
      conn = MockDaoFactory.getInstance().getConnection();
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      logger.error("SQLError with delete()");
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      //Close the connection
      mockDao.closeConnection(conn, stmt, null);
    }
  }

  @Override
  public Page<Computer> createPage(Page<Computer> page) {
    Computer computer;
    Connection conn = null;
    PreparedStatement stmt = null;
    Statement countStmt = null;
    ResultSet rs = null;
    List<Computer> computers = new ArrayList<Computer>();

    String countQuery = "SELECT COUNT(id) as total FROM computer";
    String query = "SELECT * FROM computer LIMIT ? OFFSET ? ;";

    try {
      conn = MockDaoFactory.getInstance().getConnection();

      countStmt = conn.createStatement();

      rs = countStmt.executeQuery(countQuery);
      rs.next();
      page.setNbResults(rs.getInt("total"));

      stmt = conn.prepareStatement(query);
      stmt.setInt(1, page.getNbResultsPerPage());
      stmt.setInt(2, (page.getPageNumber() - 1) * page.getNbResultsPerPage());

      rs = stmt.executeQuery();

      while (rs.next()) {
        computer = new Computer();
        computer.setId(rs.getLong("id"));
        computer.setName(rs.getString("name"));
        computers.add(computer);
      }

      page.setList(computers);

    } catch (SQLException e) {
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      logger.error("SQLError with createPage()");
      //Close the connection
      mockDao.closeConnection(conn, stmt, null);
    }
    return page;
  }

  @Override
  public List<Computer> getAll() {
    Computer computer;
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Computer> computers = new ArrayList<Computer>();

    String query = "SELECT * FROM computer;";

    try {
      conn = MockDaoFactory.getInstance().getConnection();

      stmt = conn.prepareStatement(query);

      rs = stmt.executeQuery();

      while (rs.next()) {
        computer = new Computer();
        computer.setId(rs.getLong("id"));
        computer.setName(rs.getString("name"));
        computers.add(computer);
      }

    } catch (SQLException e) {
      throw new PersistenceException(e.getMessage(), e);
    } finally {
      logger.error("SQLError with createPage()");
      //Close the connection
      mockDao.closeConnection(conn, stmt, null);
    }
    return computers;
  }

}
