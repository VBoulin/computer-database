package com.excilys.formation.java.ui;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.excilys.formation.java.persistence.Manager;

public class UserConsole {

  public UserConsole() {
    super();
    // TODO Auto-generated constructor stub
  }

  public void showMenu() {
    System.out.println("<------------------------------>");
    System.out.println("<-------------Menu------------->");
    System.out.println("<------------------------------>");
    System.out.println("[1] : Display all computers");
    System.out.println("[2] : Display all companies");
    System.out.println("[3] : Display computer details");
    System.out.println("[4] : Create a computer");
    System.out.println("[5] : Update a computer");
    System.out.println("[6] : Delete a computer");
    chooseOption();
  }

  public void chooseOption() {
    Scanner scanner = new Scanner(System.in);
    String s = null;

    do {
      System.out.print("Choose an option [1-6]: ");
      s = scanner.next();
    } while (!checkOption(s));

    switch (s) {
      case "1":
        break;
      case "2":
        break;
      case "3":
        break;
      case "4":
        break;
      case "5":
        break;
      case "6":
        break;
    }
    scanner.close();
  }

  public boolean checkOption(String s) {
    int option = 0;
    try {
      option = Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      System.out.println("You must use a number.");
      return false;
    }
    if (option < 1 || option > 6) {
      System.out.println("The number needs to be between 1 and 6.");
      return false;
    }
    return true;
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    UserConsole console = new UserConsole();
    console.showMenu();
  }

}
