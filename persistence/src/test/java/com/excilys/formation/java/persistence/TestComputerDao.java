package com.excilys.formation.java.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-persistenceContext.xml"})
public class TestComputerDao {
    
    
    @Autowired
    ComputerDao computerDao;
    
    @Autowired
    DataSource dataSource;
    
    List<Computer> list;
    Company company1 = new Company(1L, "Apple Inc.");
    Company company2 = new Company(2L, "Thinking Machines");
    
    @Before
    public void init() throws SQLException {
        list = new ArrayList<Computer>();
        list.add(new Computer(1L, "MacBook Pro 15.4 inch", null, null, company1));
        list.add(new Computer(2L, "MacBook Pro", LocalDate.parse("2006-01-10"), null, company1));
        list.add(new Computer(3L, "CM-2a", null, null, company2));
        list.add(new Computer(4L, "CM-200", null, null, company2));
        
        Connection connection = DataSourceUtils.getConnection(dataSource);
        
        Statement stmt = connection.createStatement();
        stmt.execute("drop table if exists computer;");  
        stmt.execute("drop table if exists company;");
        stmt.execute("create table company (id bigint not null auto_increment, name varchar(255), "
                + "constraint pk_company primary key (id));");
        stmt.execute("create table computer (id bigint not null auto_increment,name varchar(255), "
                + "introduced timestamp NULL, discontinued timestamp NULL,"
                + "company_id bigint default NULL,"
                + "constraint pk_computer primary key (id));");
        stmt.execute("alter table computer add constraint fk_computer_company_1 foreign key (company_id)"
                + " references company (id) on delete restrict on update restrict;");
        stmt.execute("create index ix_computer_company_1 on computer (company_id);");
        
        stmt.execute("insert into company (id,name) values (  1,'Apple Inc.');");
        stmt.execute("insert into company (id,name) values (  2,'Thinking Machines');");
        
        stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  1,'MacBook Pro 15.4 inch',null,null,1);");
        stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  2,'MacBook Pro','2006-01-10',null,1);");
        stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  3,'CM-2a',null,null,2);");
        stmt.execute("insert into computer (id,name,introduced,discontinued,company_id) values (  4,'CM-200',null,null,2);");
        connection.close();
        
    }
    
    /*
     * Tests of the findAll function
     */
    @Test
    public void findAll() {
        assertEquals(list, computerDao.findAll());
    }
    
    
    
    /*
     * Tests of the findOne function
     */
    @Test
    public void findOneValid() {
        assertEquals(list.get(0), computerDao.findOne(1L));
    }
}