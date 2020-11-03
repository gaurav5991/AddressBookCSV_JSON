package com.bridgelabz.addressbook;

import org.junit.Assert;
import org.junit.Test;

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
}
