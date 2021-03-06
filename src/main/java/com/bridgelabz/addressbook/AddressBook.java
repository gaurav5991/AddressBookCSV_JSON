package com.bridgelabz.addressbook;

import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.Files.*;

public class AddressBook {

    static Scanner sc = new Scanner(System.in);

    ArrayList<Contact> contactList;
    public Map<String,ArrayList<Contact>> ContactByState;
    public Map<String,ArrayList<Contact>> ContactByCity;

    public AddressBook() {
        contactList = new ArrayList<>();
        ContactByState = new HashMap<>();
        ContactByCity = new HashMap<>();
    }

    /*Method to Add Contact in AddressBook*/
    public ArrayList<Contact> addContact(){

        System.out.println("Enter First Name: ");
        String firstname = sc.next();

        checkDuplicate();

        System.out.println("Enter last name: ");
        String lastname = sc.next();

        System.out.println("Enter Address: ");
        String address = sc.next();

        sc.nextLine();

        System.out.println("Enter City: ");
        String city = sc.next();

        System.out.println("Enter State: ");
        String state =sc.next();

        System.out.println("Enter Zip Code: ");
        String zip = sc.next();

        System.out.println("Enter Phone Number:");
        String phonenumber = sc.next();

        System.out.println("Enter Email: ");
        String email = sc.next();

        Contact contactObj = new Contact(firstname,lastname,address,city,state,zip,phonenumber,email);

        contactList.add(contactObj);

        if(!ContactByState.containsKey(state)){
            ContactByState.put(state,new ArrayList<Contact>());
        }
        ContactByState.get(state).add(contactObj);

        if(!ContactByCity.containsKey(city)){
            ContactByCity.put(city,new ArrayList<>());
        }
        ContactByCity.get(city).add(contactObj);

        return contactList;
    }
    /*Method to edit contact in Address Book*/
    public boolean editContact(String Name)
    {
        int flag = 0;
        for(Contact contact: contactList)
        {
            if(contact.getFirst_name().equals(Name))
            {
                System.out.println("Enter the detail which needs to be updated:");

                System.out.println("1.First Name");
                System.out.println("2.Last Name");
                System.out.println("3.Address");
                System.out.println("4.City");
                System.out.println("5.State");
                System.out.println("6.ZipCode");
                System.out.println("7.Phone Number");

                System.out.println("Choose Option");

                int choice = sc.nextInt();
                switch(choice)
                {
                    case 1:
                    {
                        System.out.println("Enter First Name: ");
                        String firstname = sc.next();
                        contact.setFirst_name(firstname);
                        break;
                    }
                    case 2:
                    {
                        System.out.println("Enter last name: ");
                        String lastname = sc.next();
                        contact.setLast_name(lastname);
                        break;
                    }
                    case 3:
                    {
                        System.out.println("Enter Address: ");
                        String address = sc.next();
                        contact.setAddress(address);
                        break;
                    }
                    case 4:
                    {
                        System.out.println("Enter City: ");
                        String city = sc.next();
                        contact.setCity(city);
                        break;
                    }
                    case 5:
                    {
                        System.out.println("Enter State: ");
                        String state =sc.next();
                        contact.setState(state);
                        break;
                    }
                    case 6:
                    {
                        System.out.println("Enter Zip Code: ");
                        String zip = sc.next();
                        contact.setZip_code(zip);
                        break;
                    }
                    case 7:
                    {
                        System.out.println("Enter Phone Number:");
                        String phonenumber = sc.next();
                        contact.setPhone_number(phonenumber);
                        break;
                    }
                }

                flag = 1;
                break;
            }
        }
        if(flag==1)
            return true;
        else
            return false;
    }
    /*Method to delete contact from Address Book*/
    public boolean deleteContact(String name) {
        int flag = 0;
        for(Contact contact: contactList)
        {
            if(contact.getFirst_name().equals(name))
            {
                contactList.remove(contact);
                flag = 1;
                break;
            }
        }
        if(flag==1)
            return true;
        else
            return false;
    }
    /*Method to check Duplicate Contact in Address Book*/
    public void checkDuplicate(){
        Set<String> ContactSet = new HashSet<>();
        Set<Contact> FilterSet = contactList.stream().filter( n -> !ContactSet.add(n.getFirst_name())).collect(Collectors.toSet());

        for(Contact contact:FilterSet){
            System.out.println("The Duplicate Contact is: "+contact.getFirst_name()+" "+contact.getLast_name());
        }
    }
    /*Search Person by State*/
    public void getPersonNameByState(String State) {
        List<Contact> list  = contactList.stream().filter(findState ->findState.getCity().equals(State)).collect(Collectors.toList());
        for(Contact contact: list){
            System.out.println("First Name: "+contact.getFirst_name());
            System.out.println("Last Name: "+contact.getLast_name());
        }

    }
    /*Search Person Bu City*/
    public void getPersonNameByCity(String cityName) {
        List<Contact> list  = contactList.stream().filter(city ->city.getCity().equals(cityName)).collect(Collectors.toList());
        for(Contact contact: list){
            System.out.println("First Name: "+contact.getFirst_name());
            System.out.println("Last Name: "+contact.getLast_name());

        }
    }
    /*Read File Using FILE IO*/
    public void readAddressBook(String AddressBookName) throws IOException {
        Path filePath = Paths.get("addressBook.txt");
        try {
            System.out.println("The contacts in the address book are : ");
            lines(filePath).map(line -> line.trim()).forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*Add contacts to File Using FILE IO*/
    public void writeAddressBook(String AddressBookName) throws IOException {
        Path filepath = Paths.get("addressBook.txt");
        if (notExists(filepath))
            createFile(filepath);
        StringBuffer ContactBuffer = new StringBuffer();
        contactList.forEach(book -> {
            String bookDataString = book.toString().concat("\n");
            ContactBuffer.append(bookDataString);
        });

        try {
            write(filepath, ContactBuffer.toString().getBytes());
            System.out.println("Details Successfully added to address book file");
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
    /*Read File Using OpenCSV*/
    public void readAddressBookUSingOpenCSV(String AddressBookName) throws IOException {
        Path filePath = Paths.get("C:\\Users\\Gaurav\\Downloads\\Contacts.csv");
        try (Reader reader = Files.newBufferedReader(filePath);) {
            CsvToBean csvToBean = new CsvToBeanBuilder(reader).withType(Contact.class).withIgnoreLeadingWhiteSpace(true).build();
            Iterator<Contact> AddressBookIterator = csvToBean.iterator();
            while (AddressBookIterator.hasNext()) {
                Contact contact = AddressBookIterator.next();
                System.out.println("Firstname : " + contact.first_name);
                System.out.println("Lastname : " + contact.last_name);
                System.out.println("Address : " + contact.address);
                System.out.println("City : " + contact.city);
                System.out.println("State : " + contact.state);
                System.out.println("Zip : " + contact.zip_code);
                System.out.println("Phone number : " + contact.phone_number);
                System.out.println("Email : " + contact.email);
                System.out.println("============================");
            }
        }
    }
    /*Write to CSV file*/
    public void writeAddressBookUsingOpenCSV(String AddressBookName)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        Path filePath = Paths.get("C:\\Users\\Gaurav\\Downloads\\Contacts.csv");;
        if (Files.notExists(filePath))
            Files.createFile(filePath);
        try (Writer writer = Files.newBufferedWriter(filePath);) {
            StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(contactList);
        }
    }
    /*Write File Using JSON*/
    public void writeToJsonFile(String AddressBookName) throws IOException {
        Path filePath = Paths.get("C:\\Users\\Gaurav\\Downloads\\Contacts.json");
        Gson gson = new Gson();
        String json = gson.toJson(contactList);
        FileWriter writer = new FileWriter(String.valueOf(filePath));
        writer.write(json);
        writer.close();
    }

    /*Read Data From JSON File*/
    public void readFromJsonFile(String AddressBookName) throws IOException {
        Path filePath = Paths.get("C:\\Users\\Gaurav\\Downloads\\Contacts.json");
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(String.valueOf(filePath)));
        Contact[] contact = gson.fromJson(br, Contact[].class);
        List<Contact> contactList = Arrays.asList(contact);
        for (Contact con : contactList) {
            System.out.println("Firstname : " + con.first_name);
            System.out.println("Lastname : " + con.last_name);
            System.out.println("Address : " + con.address);
            System.out.println("City : " + con.city);
            System.out.println("State : " + con.state);
            System.out.println("Zip : " + con.zip_code);
            System.out.println("Phone number : " + con.phone_number);
            System.out.println("Email : " + con.email);
            System.out.println("**********************************");
        }
    }
}

