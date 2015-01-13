package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.Service;

public class ComputerDao implements Dao<Computer> {

  private static final String URL      = "jdbc:mysql://localhost:3306/computer-database-db";
  private static final String USR      = "admincdb";
  private static final String PASSWORD = "qwerty1234";

  @Override
  public void create(Computer o) {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "insert into computer(name, introduced, discontinued, company_id)  value (?, ?, ?, ?);";

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
      e.printStackTrace();
    } finally {
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
  }

  @Override
  public Computer getOne(Long id) {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Computer computer = new Computer();

    String query = "select * from computer where id = ?;";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      while (rs.next()) {
        computer.setId(id);
        computer.setName(rs.getString("name"));
        if(rs.getDate("introduced")!=null){
          computer.setIntroduced(rs.getDate("introduced").toLocalDate());
        }
        if(rs.getDate("discontinued")!=null){
          computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
        }
        Company company = Service.getInstance().getOneCompany(rs.getLong("company_id"));
        computer.setCompany(company);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DaoFactory.getInstance().closeConnection(conn, stmt, rs);
    }
    return computer;
  }

  @Override
  public List<Computer> getAll() {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Computer computer;
    List<Computer> computers = new ArrayList<Computer>();

    String query = "select * from computer;";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      rs = stmt.executeQuery();
      while (rs.next()) {
        computer = new Computer();
        computer.setId(rs.getLong("id"));
        computer.setName(rs.getString("name"));
        computers.add(computer);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DaoFactory.getInstance().closeConnection(conn, stmt, rs);
    }
    return computers;
  }

  @Override
  public void update(Computer o) {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?;";

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
      e.printStackTrace();
    } finally {
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;

    String query = "delete from computer where id = ?;";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DaoFactory.getInstance().closeConnection(conn, stmt, null);
    }
  }

}
