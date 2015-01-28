package com.excilys.formation.java.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.model.Page;
import com.excilys.formation.java.service.CompanyDBService;
import com.excilys.formation.java.service.ComputerDBService;
import com.excilys.formation.java.validator.Validator;

@Component
public class UserConsole {

  @Autowired
  private ComputerDBService computerDBService;
  @Autowired
  private CompanyDBService  companyDBService;

  private boolean           stop = false;
  private Scanner           scanner;

  /**
   * Constructor
   * Instantiation of the scanner, the service and the validators
   */
  public UserConsole() {
    scanner = new Scanner(System.in);
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
  public void showComputerPage(List<Computer> computers) {
    for (int i = 0; i < computers.size(); i++) {
      System.out.println(new StringBuilder("Id : ").append(computers.get(i).getId())
          .append(" | Name : ").append(computers.get(i).getName()));
    }
  }

  /**
   * Retrieve a list containing all the computers and display them in a page.
   */
  public void displayAllComputers() {
    String input;

    //Retrieve the first Page
    Page<Computer> page = computerDBService.createPage(new Page<Computer>());

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
            page = computerDBService.createPage(page);
          }
          System.out.println("Total : " + page.getNbResults());
          showComputerPage(page.getList());
          break;
        //Show the next Page
        case "2":
          if (page.nextPage()) {
            page = computerDBService.createPage(page);
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
    Page<Company> page = companyDBService.createPage(new Page<Company>());

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
            page = companyDBService.createPage(page);
          }
          System.out.println("Total : " + page.getNbResults());
          showCompanyPage(page.getList());
          break;
        //Show the next Page
        case "2":
          if (page.nextPage()) {
            page = companyDBService.createPage(page);
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

    Computer computer = computerDBService.getOne(Long.parseLong(input));

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
    } while (!input.equals("0") && !Validator.isDate(input));

    if (!input.equals("0")) {
      b.introduced(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter discontinued date (yyyy-mm-dd) or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !Validator.isDate(input));

    if (!input.equals("0")) {
      b.discontinued(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter company id or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !Validator.isID(input));

    if (!input.equals("0")) {
      company = companyDBService.getOne(Long.parseLong(input));
      if (company != null)
        b.company(companyDBService.getOne(Long.parseLong(input)));
    }

    Computer computer = b.build();

    //Add the computer to the database
    computerDBService.create(computer);
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
    Computer computer = computerDBService.getOne(id);

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
      } while (!input.equals("0") && !Validator.isDate(input));

      if (!input.equals("0")) {
        b.discontinued(LocalDate.parse(input));
      }

      do {
        System.out.print("Enter the new company id or enter 0 : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !Validator.isID(input));

      if (!input.equals("0")) {
        company = companyDBService.getOne(Long.parseLong(input));
        if (company != null)
          b.company(companyDBService.getOne(Long.parseLong(input)));
      }

      computer = b.build();
      //Update the computer in the database
      computerDBService.update(computer);
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
    Computer computer = computerDBService.getOne(id);

    if (computer != null) {
      //Delete the computer in the database
      computerDBService.delete(id);
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
    Company company = companyDBService.getOne(id);

    if (company != null) {
      //Delete the computer in the database
      companyDBService.delete(id);
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
   */
  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext("com.excilys.formation.java");
    UserConsole console = context.getBean(UserConsole.class);
    console.showMenu();
  }

}
