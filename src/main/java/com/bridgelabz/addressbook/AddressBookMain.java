package com.bridgelabz.addressbook;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain {

    static Scanner scanner = new Scanner(System.in);
    static public Map<String, AddressBook> bookList = new HashMap<>();

    /*Main Method*/
    public static void main(String[] args) {

        System.out.println("Welcome to Address book Management Program");
        AddressBookMain addBookMain = new AddressBookMain();
        boolean flag = true;
        while (flag) {
            System.out.println("1.Add New Address Book");
            System.out.println("2.Find Duplicate Entry in Address Book");
            System.out.println("3.Search Contact from a city");
            System.out.println("4.Search Contact from a State");
            System.out.println("5.Search contact By State Using State and Person HashMap");
            System.out.println("6.Search Contact by city Using City and Person HashMap");
            System.out.println("7.Count Contact By State");
            System.out.println("8.Count Contact By City");
            System.out.println("9.Sort and Print in Alphabetically Order");
            System.out.println("10.Sort Contact By City");
            System.out.println("11.Sort Contact By State");
            System.out.println("12.Sort Contact By Zip Code");
            System.out.println("13.Write Address Book to file");
            System.out.println("14.Read Address Book from file");
            System.out.println("15.Write Address Book to CSV file USing OpenCSV");
            System.out.println("16.Read Address Book from  CSV file Using OpenCSV");
            System.out.println("17.Exit");
            System.out.println("Enter choice: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1: {
                    System.out.println("Enter the Name of Address Book: ");
                    String bookName = scanner.next();
                    if (bookList.containsKey(bookName)) {
                        System.out.println("The Address book Already Exists");
                        break;
                    } else {
                        addBookMain.addAddressBook(bookName);
                        break;
                    }
                }
                case 2: {
                    for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
                        AddressBook value = entry.getValue();
                        System.out.println("Address Book Name: " + entry.getKey());
                        value.checkDuplicate();
                    }
                }
                case 3: {
                    System.out.println("Enter Name of City: ");
                    String CityName = scanner.next();
                    addBookMain.searchPersonByCity(CityName);
                    break;
                }
                case 4: {
                    System.out.println("Enter Name of State: ");
                    String StateName = scanner.next();
                    addBookMain.searchPersonByState(StateName);
                    break;
                }
                case 5: {
                    System.out.println("Enter Name of State: ");
                    String StateName = scanner.next();
                    addBookMain.searchPersonByCityUsinghashmap(StateName);
                    break;
                }
                case 6: {
                    System.out.println("Enter Name of City: ");
                    String CityName = scanner.next();
                    addBookMain.searchPersonByStateUsingHashMap(CityName);
                    break;
                }
                case 7: {
                    System.out.println("Enter Name of State: ");
                    String StateName = scanner.next();
                    addBookMain.CountByState(StateName);
                    break;
                }
                case 8: {
                    System.out.println("Enter Name of City: ");
                    String CityName = scanner.next();
                    addBookMain.CountByCity(CityName);
                    break;
                }
                case 9: {
                    addBookMain.sortContactByName();
                    break;
                }
                case 10: {
                    addBookMain.sortContactByCity();
                    break;
                }
                case 11: {
                    addBookMain.sortContactByState();
                    break;
                }
                case 12: {
                    addBookMain.sortContactByZipCode();
                    break;
                }
                case 13: {
                    System.out.println("Enter the name of address Book to write");
                    String BookName = scanner.next();
                    AddressBook book = bookList.get(BookName);
                    System.out.println("Writing to file");
                    try {
                        book.writeAddressBook(BookName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 14: {
                    System.out.println("Enter the name of address Book to read");
                    String addressBookName = scanner.next();
                    AddressBook book = bookList.get(addressBookName);
                    System.out.println("Reading from file");
                    try {
                        book.readAddressBook(addressBookName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 15: {
                    System.out.println("Enter the name of address Book to write");
                    String BookName = scanner.next();
                    AddressBook book = bookList.get(BookName);
                    System.out.println("Writing to file");
                    try{
                        book.writeAddressBookUsingOpenCSV(BookName);
                    }catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e){
                        e.printStackTrace();
                    }
                    break;
                }
                case 16:{
                    System.out.println("Enter the name of address Book to read");
                    String addressBookName = scanner.next();
                    AddressBook book = bookList.get(addressBookName);
                    System.out.println("Reading from file");
                    try{
                        book.readAddressBookUSingOpenCSV(addressBookName);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                }
                case 17:{
                    flag = false;
                    break;
                }
            }
        }
    }

    /*Adding Address Book to Main Book*/
    public void addAddressBook(String bookName) {
        boolean flag = true;
        AddressBook addBookObj = new AddressBook();
        while (flag) {
            System.out.println("1.Add Contact");

            System.out.println("2.Edit Contact");

            System.out.println("3.Delete Contact");

            System.out.println("4.Exit");

            System.out.println("Enter Choice: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1: {
                    addBookObj.addContact();
                    break;
                }
                case 2: {
                    System.out.println("Enter the Person First name to edit details: ");
                    String person_name = scanner.next();
                    boolean b = addBookObj.editContact(person_name);
                    if (b) {
                        System.out.println("Details Updated");
                    } else {
                        System.out.println("Contact Not Found");
                    }
                    break;
                }
                case 3: {
                    System.out.println("Enter the Contact to be deleted:");
                    String Name = scanner.next();
                    boolean b1 = addBookObj.deleteContact(Name);
                    if (b1) {
                        System.out.println("Details Deleted");
                    } else {
                        System.out.println("Contact Not Found");
                    }
                    break;
                }
                case 4: {
                    flag = false;
                    break;
                }
            }
        }
        bookList.put(bookName, addBookObj);
        System.out.println("Address Book Added Successfully");
    }

    /*Search Contact By State*/
    private void searchPersonByState(String stateName) {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            System.out.println("The Address Book: " + entry.getKey());
            value.getPersonNameByState(stateName);
        }
    }

    /*Search Contact by City*/
    private void searchPersonByCity(String cityName) {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            System.out.println("The Address Book: " + entry.getKey());
            value.getPersonNameByCity(cityName);
        }
    }

    /*Search contact by City Using HashMap*/
    private void searchPersonByCityUsinghashmap(String stateName) {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            ArrayList<Contact> contacts = value.ContactByState.entrySet().stream().filter(findState -> findState.getKey().equals(stateName)).map(Map.Entry::getValue).findFirst().orElse(null);
            for (Contact contact : contacts) {
                System.out.println("First Name: " + contact.getFirst_name());
                System.out.println("Last Name: " + contact.getLast_name());
            }
        }
    }

    /*Search Contact by StateUsing hashMap*/
    private void searchPersonByStateUsingHashMap(String cityName) {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            ArrayList<Contact> contacts = value.ContactByCity.entrySet().stream().filter(findCity -> findCity.getKey().equals(cityName)).map(Map.Entry::getValue).findFirst().orElse(null);
            for (Contact contact : contacts) {
                System.out.println("First Name: " + contact.getFirst_name());
                System.out.println("Last Name: " + contact.getLast_name());
            }
        }
    }

    /*Count contact by state*/
    public void CountByState(String state) {
        int count = 0;
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            for (int i = 0; i < (entry.getValue()).contactList.size(); i++) {
                Contact contact = entry.getValue().contactList.get(i);

                if (state.equals(contact.getState())) {
                    count++;
                }

            }
        }
        System.out.println("Total Person Count in state " + state + ": " + count);
    }

    /*Count Contact by City*/
    public void CountByCity(String city) {
        int count = 0;
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            for (int i = 0; i < (entry.getValue()).contactList.size(); i++) {
                Contact d = (Contact) entry.getValue().contactList.get(i);

                if (city.equals(d.getCity())) {
                    count++;
                }

            }
        }
        System.out.println("Total number of people in this city " + city + ": " + count);
    }

    /*Sort Contact By First name*/
    private void sortContactByName() {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.contactList.stream().sorted(Comparator.comparing(Contact::getFirst_name)).collect(Collectors.toList());

            for (Contact contact : sortedList) {
                System.out.println("First Name: " + contact.getFirst_name());
                System.out.println("Last Name: " + contact.getLast_name());
            }
        }
    }

    /*Sort Contact By Zipcode*/
    private void sortContactByZipCode() {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.contactList.stream().sorted(Comparator.comparing(Contact::getZip_code)).collect(Collectors.toList());

            for (Contact contact : sortedList) {
                System.out.println("First Name: " + contact.getFirst_name());
                System.out.println("Last Name: " + contact.getLast_name());
            }
        }
    }

    /*Sort Contact By State*/
    private void sortContactByState() {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.contactList.stream().sorted(Comparator.comparing(Contact::getState)).collect(Collectors.toList());

            for (Contact contact : sortedList) {
                System.out.println("First Name: " + contact.getFirst_name());
                System.out.println("Last Name: " + contact.getLast_name());
            }
        }
    }

    /*Sort contact by City*/
    private void sortContactByCity() {
        for (Map.Entry<String, AddressBook> entry : bookList.entrySet()) {
            AddressBook value = entry.getValue();
            List<Contact> sortedList = value.contactList.stream().sorted(Comparator.comparing(Contact::getCity)).collect(Collectors.toList());

            for (Contact contact : sortedList) {
                System.out.println("First Name: " + contact.getFirst_name());
                System.out.println("Last Name: " + contact.getLast_name());
            }
        }
    }
}

