package com.bridgelabz.addressbook;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.bridgelabz.addressbook.AddressBookService.IOService.DB_IO;

public class AddressBookMainTest {

    /*Test Case to check the number of retrieved contacts from database*/
    @Test
    public void givenAdddressBook_WhenRecordsRetrieved_ShouldReturnCount() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> contactList = addressBookService.readAddressBookData(DB_IO);
        Assert.assertEquals(3,contactList.size());
    }
    /*Test Case to check the contcat details updated in database using JDBC*/
    @Test
    public void givenNewInformationOfContact_WhenUpdatedShouldMatch() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        List<Contact> contactList = addressBookService.readAddressBookData(DB_IO);
        addressBookService.updateContactInformation("Alex","Carey");
        boolean result = addressBookService.checkEmployeePayrollInSyncWithDB("Alex");
        Assert.assertTrue(result);
    }

    /* TestCase to check the Contact count who added in given date range*/
    @Test
    public void givenDateRangeWhenRetrievedShouldMatchContactCount() throws AddressBookException {
        AddressBookService addressBookService = new AddressBookService();
        addressBookService.readAddressBookData(DB_IO);
        LocalDate startDate = LocalDate.of(2019,01,01);
        LocalDate endDate = LocalDate.now();
        List<Contact> contactList = addressBookService.readContactDataForDateRange(DB_IO, startDate, endDate);
        Assert.assertEquals(2,contactList.size());
    }
}
