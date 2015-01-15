package com.excilys.formation.java.persistence.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.persistence.DaoFactory;
import com.excilys.formation.java.persistence.mapper.RowMapper;

public class ComputerRowMapperImpl implements RowMapper<Computer>{


    private Logger logger = LoggerFactory.getLogger("com.excilys.formation.java.persistence.mapper.impl.ComputerRowMapperImpl");
      
    /**
       * Maps the element
       * @param resultSet The element that need to be mapped
       * @return A mapped instance of Computer
       */
    @Override
    public Computer mapRow(ResultSet rs) {
        Computer computer = null;
        Company company = null;
        if(rs == null) {
          return null;
        }
        try {
            while (rs.next()) {
          Long id = rs.getLong("id");
          String name = rs.getString("name");
          LocalDate introduced=null;
          if(rs.getDate("introduced")!=null)
              introduced = rs.getDate("introduced").toLocalDate();
          LocalDate discontinued=null;
          if(rs.getDate("discontinued")!=null)
              discontinued = rs.getDate("discontinued").toLocalDate();
          Long companyId = rs.getLong("cpId");
          String companyName = rs.getString("cpName");
          
          if(companyId > 0) {
            Company.Builder bcp = Company.builder();
            company = bcp.id(companyId).name(companyName).build();
          }
          
        
          Computer.Builder b = Computer.builder();
          computer = b.id(id).name(name).discontinued(discontinued).introduced(introduced).company(company).build();
            }
        } catch(SQLException e) {
          logger.error("SQLException while mapping a computer");
          throw new PersistenceException(e.getMessage(), e);
        }
        
        return computer;
    }

    /**
       * Maps a list of element
       * @param resultSet The list of element that need to be mapped
       * @return A mapped instance of List<Computers>
       */
    @Override
    public List<Computer> mapRowList(ResultSet rs) {
        // TODO Auto-generated method stub
        Computer computer = null;
        List<Computer> computers = new ArrayList<Computer>();
        
        if(rs == null) {
          return null;
        }
        try {
            while (rs.next()) {
              Long id = rs.getLong("id");
              String name = rs.getString("name");
              
              Computer.Builder b = Computer.builder();
              computer = b.id(id).name(name).build();
              
              computers.add(computer);
            }
        } catch(SQLException e) {
          logger.error("SQLException while mapping a list of computers");
          throw new PersistenceException(e.getMessage(), e);
        }
        
        return computers;
    }

}
