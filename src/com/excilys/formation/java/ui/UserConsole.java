package com.excilys.formation.java.ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.service.ServiceFactory;
import com.excilys.formation.java.service.ServiceInterface;

public class UserConsole {

  private ServiceInterface service;

  
  public UserConsole() {
    service = ServiceFactory.getInstance().getService();
  }

  public void showMenu() {
    System.out.println("<---------------------------------->");
    System.out.println("<---------------Menu--------------->");
    System.out.println("<---------------------------------->");
    System.out.println("[1] : Display all computers");
    System.out.println("[2] : Display all companies");
    System.out.println("[3] : Show computer details");
    System.out.println("[4] : Create a computer");
    System.out.println("[5] : Update a computer");
    System.out.println("[6] : Delete a computer");
    System.out.println("[7] : Quit");
    chooseOption();
  }

  public void chooseOption() {
    Scanner scanner = new Scanner(System.in);
    String string;

    do {
      System.out.print("Choose an option [1-7]: ");
      string = scanner.next();
    } while (!checkOption(string));

    switch (string) {
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
        scanner.close();
        System.exit(0);
        break;
    }
  }
  /**
   * Check if the option chosen in the menu is correct
   * @param s String that need to be checked
   * @return True if the format is correct, false otherwise
   */
  public boolean checkOption(String s) {
    int option;
    try {
      option = Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      System.out.println("You must choose a number.");
      return false;
    }
    if (option < 1 || option > 7) {
      System.out.println("You must use a number between 1 and 7.");
      return false;
    }
    return true;
  }

  public void pageComputer(List<Computer> computers) {
    Scanner scanner = new Scanner(System.in);
    String input;
    int debutPage = 0;
    int finPage = 10;

    do {
      for (int i = debutPage; i < finPage; i++) {
        System.out.println(new StringBuilder().append(computers.get(i).getId()).append(" - ")
            .append(computers.get(i).getName()).toString());
      }
      do {
        System.out.print("<- Enter 1 | Enter 2 ->       Enter 0 to quit : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !input.equals("1") && !input.equals("2"));
      switch (input) {
        case "1":
          debutPage -= 10;
          if (debutPage < 0) {
            debutPage = 0;
            finPage = 10;
          } else {
            finPage -= 10;
          }
          break;
        case "2":
          finPage += 10;
          if (finPage > computers.size()) {
            debutPage = computers.size() - 10;
            finPage = computers.size();
          } else {
            debutPage += 10;
          }
          break;
      }
    } while (!input.equals("0"));
  }

  public void pageCompany(List<Company> companies) {
    Scanner scanner = new Scanner(System.in);
    String input;
    int debutPage = 0;
    int finPage = 10;

    do {
      for (int i = debutPage; i < finPage; i++) {
        System.out.println(new StringBuilder().append(companies.get(i).getId()).append(" - ")
            .append(companies.get(i).getName()).toString());
      }
      do {
        System.out.print("<- Enter 1 | Enter 2 ->       Enter 0 to quit : ");
        input = scanner.next().trim();
      } while (!input.equals("0") && !input.equals("1") && !input.equals("2"));
      switch (input) {
        case "1":
          debutPage -= 10;
          if (debutPage < 0) {
            debutPage = 0;
            finPage = 10;
          } else {
            finPage -= 10;
          }
          break;
        case "2":
          finPage += 10;
          if (finPage > companies.size()) {
            debutPage = companies.size() - 10;
            finPage = companies.size();
          } else {
            debutPage += 10;
          }
          break;
      }
    } while (!input.equals("0"));
  }

  /**
   * Retrieve a list containing all the computers and display them in a page.
   */
  public void displayAllComputers() {
    List<Computer> computers = service.getAllComputers();

    System.out.println("[1] : Display all computers :");
    System.out.println("Id - Name");

    pageComputer(computers);
  }

  /**
   * Retrieve a list containing all the companies and display them in a page.
   */
  public void displayAllCompanies() {
    List<Company> companies = service.getAllCompanies();

    System.out.println("[2] : Display all companies :");
    System.out.println("Id - Name");

    pageCompany(companies);
  }

  /**
   * Display all the information about one computer thanks to his id
   */
  public void showDetailsComputer() {
    System.out.println("[3] : Show computer details :");
    Scanner scanner = new Scanner(System.in);
    String input;
    
    do {
      System.out.print("Enter computer id : ");
      input = scanner.next().trim();
    } while (!checkID(input));

    Computer computer = service.getOneComputer(Long.parseLong(input));

    System.out.println(computer.toString());
  }

  /**
   * Check if the date is in the correct format
   * @param date
   * @return True if the format is correct, false otherwise
   */
  public boolean checkDate(String date) {
    try {
      LocalDate.parse(date);
    } catch (Exception e) {
      return false;
    }
    if (date.length() != 10) {
      return false;
    }
    return true;
  }

  /**
   * Check if the id is in the correct format
   * @param input
   * @return True if the format is correct, false otherwise
   */
  public boolean checkID(String input) {
    Long id = 0l;
    try {
      id = Long.parseLong(input);
    } catch (Exception e) {
      return false;
    }
    if (id < 1) {
      return false;
    }
    return true;
  }

  /**
   * 
   */
  public void createComputer() {
    System.out.println("[4] : Create a computer :");
    Scanner scanner = new Scanner(System.in);

    Computer computer = new Computer();
    String input;

    do {
      System.out.print("Enter computer name : ");
      input = scanner.next().trim();
    } while (input.isEmpty());

    computer.setName(input);

    do {
      System.out.print("Enter introduced date (yyyy-mm-dd) or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !checkDate(input));

    if (!input.equals("0")) {
      computer.setIntroduced(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter discontinued date (yyyy-mm-dd) or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !checkDate(input));

    if (!input.equals("0")) {
      computer.setDiscontinued(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter company id or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !checkID(input));

    if (!input.equals("0")) {
      computer.setCompany(service.getOneCompany(Long.parseLong(input)));
    }

    service.createComputer(computer);
    System.out.println("Computer created with success.");
  }

  public void updateComputer() {
    System.out.println("[5] : Update a computer :");
    Scanner scanner = new Scanner(System.in);

    Computer computer = new Computer();
    String input;

    do {
      System.out.print("Enter computer id : ");
      input = scanner.next().trim();
    } while (!checkID(input));

    computer.setId(Long.parseLong(input));

    do {
      System.out.print("Enter computer name : ");
      input = scanner.next().trim();
    } while (input.isEmpty());

    computer.setName(input);

    do {
      System.out.print("Enter introduced date (yyyy-mm-dd) or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !checkDate(input));

    if (!input.equals("0")) {
      computer.setIntroduced(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter discontinued date (yyyy-mm-dd) or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !checkDate(input));

    if (!input.equals("0")) {
      computer.setDiscontinued(LocalDate.parse(input));
    }

    do {
      System.out.print("Enter company id or enter 0 : ");
      input = scanner.next().trim();
    } while (!input.equals("0") && !checkID(input));

    if (!input.equals("0")) {
      computer.setCompany(service.getOneCompany(Long.parseLong(input)));
    }

    service.updateComputer(computer);
    System.out.println("Computer updated with success.");
  }

  /**
   * Delete a computer thanks to his id
   */
  public void deleteComputer() {
    System.out.println("[6] : Delete a computer :");
    Scanner scanner = new Scanner(System.in);
    Long id;

    System.out.print("Enter computer id : ");
    id = scanner.nextLong();

    service.deleteComputer(id);

    System.out.println("Computer deleted with success.");

  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    UserConsole console = new UserConsole();
    while (true) {
      console.showMenu();
    }
  }

}
