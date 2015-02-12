package com.excilys.formation.java.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.formation.java.model.Company;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-persistenceContext.xml"})
public class TestCompanyDao {

    @Autowired
    CompanyDao companyDao;
    
    List<Company> companies;
    Company company1 = new Company(1L, "Apple Inc.");
    Company company2 = new Company(2L, "Thinking Machines");
    
    @Autowired
    DataSource dataSource;
    
    @Before
    public void init() throws SQLException {
        companies = new ArrayList<Company>();
        companies.add(company1);
        companies.add(company2);

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
        connection.close();
    }
    
    
    /*
     * Tests of the findAll function
     */
    @Test
    public void findAll() {
        assertEquals(companies, companyDao.findAll());
    }
    
    
    /*
     * Tests of the findOne function
     */
    @Test
    public void findOne() {
        assertEquals(new Company(1L, "Apple Inc."), companyDao.findOne(1L));
    }
    
    @Test
    public void FindOneInvalid() {
        assertNull(companyDao.findOne(-1L));
    }
    
    
}