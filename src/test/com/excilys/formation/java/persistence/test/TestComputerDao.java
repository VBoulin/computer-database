package com.excilys.formation.java.persistence.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.persistence.ComputerDao;

public class TestComputerDao {
  //Connections informations 
  private static final String URL      = "jdbc:mysql://localhost:3306/test-computer-database-db";
  private static final String USR      = "admintestcdb";
  private static final String PASSWORD = "qwerty12345";

  ComputerDao                 computerDao;
  List<Computer>              computers;
  Company                     company  = new Company(1L, "Apple Inc.");

  @Before
  public void setUp() throws SQLException {
    computerDao = new MockComputerDao();
    computers = new ArrayList<Computer>();
    computers.add(new Computer(1L, "MacBook Pro 15.4 inch", null, null, company));
    computers.add(new Computer(2L, "MacBook Pro", LocalDate.parse("2006-01-10"), null, company));
    MockDaoFactory dao = MockDaoFactory.getInstance();
    Connection conn = dao.getConnection(URL, USR, PASSWORD);

    Statement stmt = conn.createStatement();
    stmt.execute("Truncate computer");

    stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);");
    stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  2,'MacBook Pro','2006-01-10',null,1);");
    dao.closeConnection(conn, stmt, null);
  }

  @Test
  public void testGetOne() {
    Assert.assertEquals(computers.get(0), computerDao.getOne(1l));
  }

  @Test
  public void testCreate() {
    Computer computer = Computer.builder().name("test").introduced(LocalDate.parse("1993-01-10"))
        .company(company).build();

    computerDao.create(computer);
    computer.setId(3L);
    Assert.assertEquals(computer, computerDao.getOne(3L));
  }

  @Test
  public void testUpdate() {
    Computer computer1 = Computer.builder().name("test").introduced(LocalDate.parse("1993-01-10"))
        .company(company).build();
    computerDao.create(computer1);
    Computer computer2 = Computer.builder().id(3L).name("test")
        .introduced(LocalDate.parse("1993-01-12")).build();
    computerDao.update(computer2);
    Assert.assertEquals(computer2, computerDao.getOne(3L));
  }

  @Test
  public void testDelete() {
    Computer computer = Computer.builder().name("test").introduced(LocalDate.parse("1993-01-10"))
        .company(company).build();
    computerDao.create(computer);
    Assert.assertNotNull(computerDao.getOne(3L));
    computerDao.delete(3L);
    Assert.assertEquals(null, computerDao.getOne(3L));
  }
}
