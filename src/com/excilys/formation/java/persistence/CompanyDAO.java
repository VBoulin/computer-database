package com.excilys.formation.java.persistence;

import com.excilys.formation.java.model.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CompanyDAO implements DAO<Company>{

	@Override
	public void create(Company object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Company retrieveOne(Long id) {
		// TODO Auto-generated method stub
	  Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      Company company = null;
      
      String query="select * from company where id = ?;";

      try {
          conn = Manager.getConnection("jdbc:mysql://localhost:3306/computer-database-db","admincdb","qwerty1234");
          stmt = conn.prepareStatement(query);
          stmt.setLong(1, id);
          rs = stmt.executeQuery(query);
          while(rs.next()){
            company.setId(id);
            String name = rs.getString("name");
            company.setName(name);;
          }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
          Manager.closeConnection(conn, stmt, rs);
      }
      return company;
	}

	@Override
	public List<Company> retrieveAll() {
		// TODO Auto-generated method stub
	  Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      List<Company> companies = null;
      Company company = null;

      String query="select * from company;";

      try {
          conn = Manager.getConnection("jdbc:mysql://localhost:3306/computer-database-db","admincdb","qwerty1234");
          stmt = conn.prepareStatement(query);
          rs = stmt.executeQuery(query);
          while(rs.next()){
            Long id = rs.getLong("id");
            company.setId(id);
            String name = rs.getString("name");
            company.setName(name);;
            companies.add(company);
          }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
          Manager.closeConnection(conn, stmt, rs);
      }
      
      return companies;
	}

	@Override
	public void update(Company object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(List<Long> list) {
		// TODO Auto-generated method stub
		
	}

}
