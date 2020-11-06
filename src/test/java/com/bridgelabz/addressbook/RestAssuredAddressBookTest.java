package com.bridgelabz.addressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.bridgelabz.addressbook.AddressBookService.IOService.REST_IO;

public class RestAssuredAddressBookTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public Contact[] getContactList() {
        Response response = RestAssured.get("/contacts");
        System.out.println("AddressBook Contact Details " + response.asString());
        Contact[] arrayOfContacts = new Gson().fromJson(response.asString(), Contact[].class);
        return arrayOfContacts;
    }

    @Test
    public void givenContactDataInJSONServer_whenRetrieved_shouldMatchTheCount() {
        Contact[] arrayOfContacts = getContactList();
        AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
        long entries = addressBookService.countEntries(REST_IO);
        Assert.assertEquals(2,entries);
    }
}
