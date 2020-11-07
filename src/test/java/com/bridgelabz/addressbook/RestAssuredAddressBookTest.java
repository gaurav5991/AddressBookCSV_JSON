package com.bridgelabz.addressbook;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static com.bridgelabz.addressbook.AddressBookService.IOService.REST_IO;

public class RestAssuredAddressBookTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public Contact[] getContactList() {
        Response response = RestAssured.get("//contacts");
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

    private Response addContactToJsonServer(Contact contact) {
        String contactJson = new Gson().toJson(contact);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        request.body(contactJson);
        return request.post("/contacts");
    }
    @Test
    public void givenNewContact_whenAdded_shouldMatch201ResponseAndCount() throws AddressBookException {
        Contact[] arrayOfContacts = getContactList();
        AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
        Contact contact = new Contact("Saurav", "Raj", "X-908", "Dhanbad", "Jharkhand", "545454", "767346743", "saurav@xya.com", 124, 125, "Home", LocalDate.now());
        Response response = addContactToJsonServer(contact);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(201,statusCode);
        contact = new Gson().fromJson(response.asString(),Contact.class);
        addressBookService.addContactToAddressBook(contact,REST_IO);
        long entries = addressBookService.countEntries(REST_IO);
        Assert.assertEquals(4,entries);
    }

    @Test
    public void givenListOfNewContacts_whenAdded_shouldMatch201ResponseAndCount() throws AddressBookException {
        Contact[] arrayOfContacts = getContactList();
        AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
        Contact[] arrayOfNewContacts = {
                new Contact("Saurav", "Raj", "X-908", "Dhanbad", "Jharkhand", "545454", "767346743", "saurav@xya.com", 124, 125, "Home", LocalDate.now()),
                new Contact("Gaurav", "Gaj", "X-408", "Ajmer", "Rajasthan", "575454", "7677556743", "gaurav@xya.com", 144, 301, "Home", LocalDate.now())
        };
        for(Contact contact : arrayOfNewContacts){
            Response response = addContactToJsonServer(contact);
            int statusCode = response.getStatusCode();
            Assert.assertEquals(201,statusCode);
            contact = new Gson().fromJson(response.asString(),Contact.class);
            addressBookService.addContactToAddressBook(contact,REST_IO);
        }
        long entries = addressBookService.countEntries(REST_IO);
        Assert.assertEquals(6,entries);
    }

    @Test
    public void givenNewAddress_whenUpdated_shouldMatch200Response() throws AddressBookException {
        Contact[] arrayOfContacts = getContactList();
        AddressBookService addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
        addressBookService.updateContactInformation("Priyal","RK Apartments",REST_IO);
        Contact contact = addressBookService.getContactData("Priyal");

        String contactJson = new Gson().toJson(contact);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        request.body(contactJson);
        Response response = request.put("/contacts/"+contact.getUser_id());
        int statusCode = response.getStatusCode();
        Assert.assertEquals(200,statusCode);
    }
}
