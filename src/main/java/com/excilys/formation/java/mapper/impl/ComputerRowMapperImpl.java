package com.excilys.formation.java.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.java.exceptions.PersistenceException;
import com.excilys.formation.java.mapper.RowMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.persistence.DaoManager;

public class ComputerRowMapperImpl implements RowMapper<Computer> {

  private Logger logger = LoggerFactory.getLogger(ComputerRowMapperImpl.class);

  /**
     * Maps the element
     * @param resultSet The element that need to be mapped
     * @return A mapped instance of Computer
     */
  @Override
  public Computer mapRow(ResultSet rs) {
    Computer computer = null;
    Company company = null;
    if (rs == null) {
      return null;
    }
    try {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        Timestamp introduced = null;
        LocalDate introducedDate = null;
        if (rs.getTimestamp("introduced") != null) {
          introduced = rs.getTimestamp("introduced");
          introducedDate = introduced.toLocalDateTime().toLocalDate();
        }
        Timestamp discontinued = null;
        LocalDate discontinuedDate = null;
        if (rs.getTimestamp("discontinued") != null) {
          discontinued = rs.getTimestamp("discontinued");
          discontinuedDate = discontinued.toLocalDateTime().toLocalDate();
        }
        Long companyId = rs.getLong("cpId");
        String companyName = rs.getString("cpName");

        if (companyId > 0) {
          Company.Builder bcp = Company.builder();
          company = bcp.id(companyId).name(companyName).build();
        }

        Computer.Builder b = Computer.builder();
        computer = b.id(id).name(name).discontinued(discontinuedDate).introduced(introducedDate)
            .company(company).build();
    } catch (SQLException e) {
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

    if (rs == null) {
      return null;
    }
    try {
      while (rs.next()) {
        computer = mapRow(rs);

        computers.add(computer);
      }
    } catch (SQLException e) {
      logger.error("SQLException while mapping a list of computers");
      throw new PersistenceException(e.getMessage(), e);
    }

    return computers;
  }

}
