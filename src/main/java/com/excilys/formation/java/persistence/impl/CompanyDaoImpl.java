package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.model.Company;

public class CompanyDao implements Dao<Company> {

  private static final String URL      = "jdbc:mysql://localhost:3306/computer-database-db";
  private static final String USR      = "admincdb";
  private static final String PASSWORD = "qwerty1234";

  @Override
  public void create(Company o) {
    // TODO Auto-generated method stub

  }

  @Override
  public Company getOne(Long id) {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Company company = new Company();

    String query = "select * from company where id = ?;";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      stmt.setLong(1, id);
      rs = stmt.executeQuery();
      while (rs.next()) {
        company.setId(id);
        company.setName(rs.getString("name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DaoFactory.getInstance().closeConnection(conn, stmt, rs);
    }
    return company;
  }

  @Override
  public List<Company> getAll() {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Company company;
    List<Company> companies = new ArrayList<Company>();

    String query = "select * from company;";

    try {
      conn = DaoFactory.getInstance().getConnection(URL, USR, PASSWORD);
      stmt = conn.prepareStatement(query);
      rs = stmt.executeQuery();
      while (rs.next()) {
        company = new Company();
        company.setId(rs.getLong("id"));
        company.setName(rs.getString("name"));
        companies.add(company);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      DaoFactory.getInstance().closeConnection(conn, stmt, rs);
    }
    return companies;
  }

  @Override
  public void update(Company o) {
    // TODO Auto-generated method stub

  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub

  }

}
