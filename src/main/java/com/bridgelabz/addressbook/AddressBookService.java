package com.bridgelabz.addressbook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddressBookService {
    public List<Contact> contactList;

    public AddressBookService() {
    }

    public AddressBookService(List<Contact> contactList) {
        contactList = new ArrayList<>();
    }

    enum IOService {
        DB_IO,REST_IO
    }

    /* Method to read Contact From AddressBook*/
    public List<Contact> readAddressBookData(IOService ioService) throws AddressBookException {
        if (ioService.equals(IOService.DB_IO)) {
            this.contactList = new AddressBookDBService().readData();
        }
        return this.contactList;
    }

    /*method to update contact information in Database*/
    public void updateContactInformation(String first_name, String last_name,IOService ioService) throws AddressBookException {
       if(ioService.equals(IOService.DB_IO)){
           int result = new AddressBookDBService().updateContactData(first_name, last_name);
           if (result == 0) {
               try {
                   throw new AddressBookException(AddressBookException.ExceptionType.UNABLE_TO_UPDATE);
               } catch (AddressBookException e) {
                   e.printStackTrace();
               }
           }
           Contact contact = this.getContactData(first_name);
           if (contact != null)
               contact.setLast_name(last_name);
       }
    }

    public void addContactToAddressBook(Contact contact, IOService ioService) throws AddressBookException {
        if(ioService.equals(IOService.DB_IO))
            this.addContactToAddressBook(contact.getFirst_name(),contact.getLast_name(),contact.getAddress(),contact.getCity(),contact.getState(),contact.getZip_code(),contact.getPhone_number(),contact.getEmail(),contact.getStartDate(),contact.getUser_id(),contact.getType_id(),contact.getContact_type());
        else
            contactList.add(contact);
    }
    /*Method to add Contact to AddressBook*/
    public void addContactToAddressBook(String firstName, String lastName, String address,
                                        String city, String state, String zip, String phone,
                                        String email, LocalDate date, int user_id,
                                        int type_id, String type_of_contact) throws AddressBookException {
        contactList.add(new AddressBookDBService().
                addContact(firstName, lastName, address, city, state, zip, phone, email,
                        date, user_id, type_id, type_of_contact));
    }

    /*Method to add Multiple Contact to AddressBook*/
    public void addMultipleContactsToDB(List<Contact> contactList) {
        new AddressBookDBService().addMultipleContactsToDB(contactList);
    }

    public long countEntries(IOService ioService) {
        long entries = 0;
        if(ioService.equals(IOService.DB_IO)){
            entries =  contactList.size();
        }else  if(ioService.equals(IOService.REST_IO)){
            entries = contactList.size();
        }
        return entries;
    }


    /*Method to get Contact using first name*/
    public Contact getContactData(String first_name) {
        return this.contactList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.getFirst_name().equals(first_name))
                .findFirst()
                .orElse(null);
    }

    /*Method to check database in sync with Address book service*/
    public boolean checkEmployeePayrollInSyncWithDB(String first_name) throws AddressBookException {
        List<Contact> contactList = new AddressBookDBService().getContactData(first_name);
        return contactList.get(0).equals(getContactData(first_name));
    }

    /*Method to read Contact Added within given date range*/
    public List<Contact> readContactDataForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) throws AddressBookException {
        if (ioService.equals(IOService.DB_IO))
            this.contactList = new AddressBookDBService().readContactDataForDateRange(startDate, endDate);
        return this.contactList;
    }

    /*Method to read Contact by State*/
    public Map<String, String> readContactByState(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return new AddressBookDBService().readContactByState();
        return null;
    }

    /*Method to read Contact by City*/
    public Map<String, String> readContactByCity(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return new AddressBookDBService().readContactByCity();
        return null;
    }
}
