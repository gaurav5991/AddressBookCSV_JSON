package com.bridgelabz.addressbook;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bridgelabz.addressbook.AddressBookService.IOService.DB_IO;

public class AddressBookMainTest {

    /*Test Case to check the number of retrieved contacts from database*/
    @Test
    public void givenAdddressBook_WhenRecordsRetrieved_ShouldReturnCount() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> contactList = addressBookService.readAddressBookData(DB_IO);
        Assert.assertEquals(6, contactList.size());
    }

    /*Test Case to check the contact details updated in database using JDBC*/
    @Test
    public void givenNewInformationOfContact_WhenUpdatedShouldMatch() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> contactList = addressBookService.readAddressBookData(DB_IO);
        addressBookService.updateContactInformation("Alex", "Carey");
        boolean result = addressBookService.checkEmployeePayrollInSyncWithDB("Alex");
        Assert.assertTrue(result);
    }

    /* TestCase to check the Contact count who added in given date range*/
    @Test
    public void givenDateRangeWhenRetrievedShouldMatchContactCount() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        LocalDate startDate = LocalDate.of(2019, 01, 01);
        LocalDate endDate = LocalDate.now();
        List<Contact> contactList = addressBookService.readContactDataForDateRange(DB_IO, startDate, endDate);
        Assert.assertEquals(5, contactList.size());
    }

    /*Test Case to check the number of retrieved contacts from database by state Name*/
    @Test
    public void givenStateNameWhenRetrievedShouldReturnCorrectMatch() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        Map<String, String> ContactByState = addressBookService.readContactByState(DB_IO);
        Assert.assertTrue(ContactByState.get("Wales").equals("Michael"));
    }

    /*Test Case to check the number of retrieved contacts from database by City Name*/
    @Test
    public void givenCityNameWhenRetrievedShouldReturnCorrectMatch() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        Map<String, String> ContactByCity = addressBookService.readContactByCity(DB_IO);
        Assert.assertTrue(ContactByCity.get("Bradford").equals("Michael"));
    }

    /*Test Case to check employee when Added to AddressBook DataBase*/
    @Test
    public void givenNewContactWhenAddedShouldSyncWithDB() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        addressBookService.addContactToAddressBook("Adams", "Baker", "Street 2", "Mexico", "California", "123456", "98675453421", "abc@gmail.com", LocalDate.now(), 110, 210, "friend");
        boolean result = addressBookService.checkEmployeePayrollInSyncWithDB("Adams");
        Assert.assertTrue(result);
    }

    /*Test Case to add multiple employee when Added to AddressBook DataBase using MultiThreading*/
    @Test
    public void given5NewContactEntries_WhenAddedUsingMultithreading_ShouldSyncWithDB() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        Contact[] arrayOfContacts = {
                new Contact("Saurav", "Raj", "X-908", "Dhanbad", "Jharkhand", "545454", "767346743", "saurav@xya.com", 124, 125, "Home", LocalDate.now()),
                new Contact("Prince", "Jha", "X-910", "Ranchi", "Karnataka", "54654", "8746723", "prince@xya.com", 134, 147, "Relatives", LocalDate.now()),
                new Contact("Kolhan", "Kotla", "Y-908", "Mumbai", "Maharashtra", "263253", "672547", "abc@xya.com", 134, 126, "College Friends", LocalDate.now())};
        Instant threadStart = Instant.now();
        addressBookService.addMultipleContactsToDB(Arrays.asList(arrayOfContacts));
        Instant threadEnd = Instant.now();
        System.out.println("Duration with thread: " + Duration.between(threadStart, threadEnd));
        Assert.assertEquals(6, addressBookService.countEntries(DB_IO));
    }
}
