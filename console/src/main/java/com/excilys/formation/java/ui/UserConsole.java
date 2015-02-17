package com.excilys.formation.java.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.excilys.formation.java.dto.ComputerDto;
import com.excilys.formation.java.dto.ComputerDtoMapper;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.PageWrapper;
import com.excilys.formation.java.util.Validator;
import com.excilys.formation.java.webservice.CompanyWebService;
import com.excilys.formation.java.webservice.ComputerWebService;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class UserConsole {

//  @Autowired
//  private ComputerDBService computerDBService;
//  @Autowired
//  private CompanyDBService  companyDBService;
  
  private ComputerWebService computerWebService;
  
  private CompanyWebService companyWebService;
  
  private ComputerDtoMapper computerDtoMapper;

  private boolean           stop = false;
  private Scanner           scanner;

  /**
   * Constructor
   * Instantiation of the scanner, the service and the validators
   */
  public UserConsole() {
    scanner = new Scanner(System.in);
  }

  public void setComputerWebService(ComputerWebService computerWebService) {
    this.computerWebService = computerWebService;
  }

  public void setCompanyWebService(CompanyWebService companyWebService) {
    this.companyWebService = companyWebService;
  }

  /**
   * Main menu of the console
   */
  public void showMenu() {
    while (!stop) {
      System.out.println("<---------------------------------->");
      System.out.println("<---------------Menu--------------->");
      System.out.println("<---------------------------------->");
      System.out.println("[1] : Display all computers");
      System.out.println("[2] : Display all companies");
      System.out.println("[3] : Show computer details");
      System.out.println("[4] : Create a computer");
      System.out.println("[5] : Update a computer");
      System.out.println("[6] : Delete a computer");
      System.out.println("[7] : Delete a company");
      System.out.println("[8] : Quit");
      chooseOption();
    }
  }

  /**
   * Check the input and send to the corresponding function.
   */
  public void chooseOption() {
    String input;

    //Check the input and send to the corresponding function.
    Pattern regex = Pattern.compile("^[1-8]$");

    do {
      System.out.print("Choose an option [1-8]: ");
      input = scanner.next();
    } while (!regex.matcher(input).find());

    switch (input) {
      case "1":
        displayAllComputers();
        break;
      case "2":
        displayAllCompanies();
        break;
      case "3":
        showDetailsComputer();
        break;
      case "4":
        createComputer();
        break;
      case "5":
        updateComputer();
        break;
      case "6":
        deleteComputer();
        break;
      case "7":
        deleteCompany();
        break;
      case "8":
        //close the scanner and stop the loop
        scanner.close();
        stop = true;
        break;
    }
  }

  /**
   * Display the content of the list
   * @param companies : list of company
   */
  public void showComputerPage(List<ComputerDto> list) {
    for (int i = 0; i < list.size(); i++) {
      System.out.println(new StringBuilder("Id : ").append(list.get(i).getId())
          .append(" | Name : ").append(list.get(i).getName()));
    }
  }

  /**
   * Retrieve a list containing all the computers and display them in a page.
   */
  public void displayAllComputers() {
    String input;

    //Retrieve the first Page
    PageWrapper<ComputerDto> page = computerWebService.createPage(0, 10);

    //Show the content of the page
    System.out.println("Number of results found : " + page.getNbResults());
    showComputerPage(page.getList());

    do {
      do {
        System.out.print("<- Enter 1 | Enter 2 ->       Enter 0 to quit : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !input.equals("1") && !input.equals("2"));
      switch (input) {
      //Show the previous Page
        case "1":
          if (page.previousPageOrFirst()) {
            page = computerWebService.createPage(page.getPageNumber(), 10);
          }
          System.out.println("Total : " + page.getNbResults());
          showComputerPage(page.getList());
          break;
        //Show the next Page
        case "2":
          if (page.nextPage()) {
            page = computerWebService.createPage(page.getPageNumber(), 10);
          }
          System.out.println("Total : " + page.getNbResults());
          showComputerPage(page.getList());
          break;
      }
    } while (!input.equals("0"));
  }

  /**
   * Display the content of the list
   * @param companies : list of company
   */
  public void showCompanyPage(List<Company> companies) {
    for (int i = 0; i < companies.size(); i++) {
      System.out.println(new StringBuilder("Id : ").append(companies.get(i).getId())
          .append(" | Name : ").append(companies.get(i).getName()));
    }
  }

  /**
   * Retrieve a list containing all the companies and display them in a page.
   */
  public void displayAllCompanies() {
    String input;

    //Retrieve the first Page
    PageWrapper<Company> page = companyWebService.createPage(0,10);

    //Show the content of the page
    System.out.println("Number of results found : " + page.getNbResults());
    showCompanyPage(page.getList());

    do {
      do {
        System.out.print("<- Enter 1 | Enter 2 ->       Enter 0 to quit : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !input.equals("1") && !input.equals("2"));
      switch (input) {
      //Show the previous Page
        case "1":
          if (page.previousPageOrFirst()) {
            page = companyWebService.createPage(page.getPageNumber(), 10);
          }
          System.out.println("Total : " + page.getNbResults());
          showCompanyPage(page.getList());
          break;
        //Show the next Page
        case "2":
          if (page.nextPage()) {
            page = companyWebService.createPage(page.getPageNumber(), 10);
          }
          System.out.println("Total : " + page.getNbResults());
          showCompanyPage(page.getList());
          break;
      }
    } while (!input.equals("0"));
  }

  /**
   * Display all the information about one computer
   */
  public void showDetailsComputer() {
    System.out.println("[3] : Show computer details :");
    String input;

    do {
      System.out.print("Enter computer id : ");
      input = scanner.next().trim();
    } while (!Validator.isID(input));

    ComputerDto computer = computerWebService.getOne(Long.parseLong(input));

    if(computer != null)
      System.out.println(computer.toString());
    else
      System.out.println("Computer not found !");
  }

  /**
   * Create one computer
   */
  public void createComputer() {
    System.out.println("[4] : Create a computer :");
    String input;
    Company company = null;

    do {
      System.out.print("Enter computer name : ");
      input = scanner.next().trim();
    } while (!Validator.isName(input));

    //computer builder
    Computer.Builder b = Computer.builder(input);

    do {
      System.out.print("Enter introduced date (yyyy-mm-dd) or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !Validator.isDate(input,"yyyy-MM-dd"));

    if (!input.equals("0")) {
      b.introduced(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter discontinued date (yyyy-mm-dd) or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !Validator.isDate(input,"yyyy-MM-dd"));

    if (!input.equals("0")) {
      b.discontinued(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter company id or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !Validator.isID(input));

    if (!input.equals("0")) {
      company = companyWebService.getOne(Long.parseLong(input));
      if (company != null)
        b.company(companyWebService.getOne(Long.parseLong(input)));
    }

    Computer computer = b.build();

    //Add the computer to the database
    computerWebService.create(computerDtoMapper.toDto(computer, "yyyy-MM-dd"));
    System.out.println("Computer created with success.");
  }

  /**
   * Update a computer
   */
  public void updateComputer() {
    System.out.println("[5] : Update a computer :");
    String input;
    Long id;
    Company company = null;

    do {
      System.out.print("Enter computer id : ");
      input = scanner.next().trim();
    } while (!Validator.isID(input));

    id = Long.parseLong(input);
    Computer computer = computerDtoMapper.fromDto(computerWebService.getOne(id),"yyyy-MM-dd");

    //Check if the computer exist in the database
    if (computer == null) {
      System.out.println("Computer not found.");
    } else {
      //computer builder
      Computer.Builder b = Computer.builder();
      b.id(id);

      System.out.print("Enter the new computer name : ");
      input = scanner.next().trim();

      b.name(input);

      do {
        System.out.print("Enter the new introduced date (yyyy-mm-dd) or enter 0 : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !Validator.isID(input));

      if (!input.equals("0")) {
        b.introduced(LocalDate.parse(input));
      }

      do {
        System.out.print("Enter the new discontinued date (yyyy-mm-dd) or enter 0 : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !Validator.isDate(input,"yyyy-MM-dd"));

      if (!input.equals("0")) {
        b.discontinued(LocalDate.parse(input));
      }

      do {
        System.out.print("Enter the new company id or enter 0 : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !Validator.isID(input));

      if (!input.equals("0")) {
        company = companyWebService.getOne(Long.parseLong(input));
        if (company != null)
          b.company(companyWebService.getOne(Long.parseLong(input)));
      }

      computer = b.build();
      //Update the computer in the database
      computerWebService.update(computerDtoMapper.toDto(computer, "yyyy-MM-dd"));
      System.out.println("Computer updated with success.");
    }

  }

  /**
   * Delete a computer
   */
  public void deleteComputer() {
    System.out.println("[6] : Delete a computer :");
    String input;
    Long id;

    do {
      System.out.print("Enter computer id : ");
      input = scanner.next().trim();
    } while (!Validator.isID(input));

    id = Long.parseLong(input);
    ComputerDto computer = computerWebService.getOne(id);

    if (computer != null) {
      //Delete the computer in the database
      computerWebService.delete(id);
      System.out.println("Computer deleted with success.");
    } else {
      System.out.println("Computer not found.");
    }
  }

  /**
   * Delete a computer
   */
  public void deleteCompany() {
    System.out.println("[7] : Delete a company :");
    String input;
    Long id;

    do {
      System.out.print("Enter company id : ");
      input = scanner.next().trim();
    } while (!Validator.isID(input));

    id = Long.parseLong(input);
    Company company = companyWebService.getOne(id);

    if (company != null) {
      //Delete the computer in the database
      companyWebService.delete(id);
      System.out
          .println("Company deleted with success. Computers associated with that company deleted with success.");
    } else {
      System.out.println("Company not found.");
    }
  }

  /**
   * Main function
   * Instantiation of the console
   * @param args
   * @throws MalformedURLException 
   */
  public static void main(String[] args) throws MalformedURLException {
    UserConsole console = new UserConsole();
    
    URL computerUrl = new URL("http://localhost:9999/computer-database/webservice/computer?wsdl");
    URL companyUrl = new URL("http://localhost:9999/computer-database/webservice/company?wsdl");
    
    QName qname = new QName("http://impl.webservice.java.formation.excilys.com/", "CompanyWebServiceImplService");
    Service service = Service.create(companyUrl, qname);
    
    CompanyWebService companyWebService = service.getPort(CompanyWebService.class);
    
    qname = new QName("http://impl.webservice.java.formation.excilys.com/", "ComputerWebServiceImplService");
    service = Service.create(computerUrl, qname);
    
    ComputerWebService computerWebService = service.getPort(ComputerWebService.class);
    
    console.setCompanyWebService(companyWebService);
    console.setComputerWebService(computerWebService);
    
    console.showMenu();
  }

}
