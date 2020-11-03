package com.bridgelabz.addressbook;

import java.util.List;

public class AddressBookService {
    private List<Contact> contactList;

    enum IOService {
        DB_IO
    }

    /* Method to read Contact From AddressBook*/
    public List<Contact> readAddressBookData(IOService ioService) throws AddressBookException {
        if (ioService.equals(IOService.DB_IO)) {
            this.contactList = new AddressBookDBService().readData();
        }
        return this.contactList;
    }

    /*method to update contact information in Database*/
    public void updateContactInformation(String first_name, String last_name) throws AddressBookException {
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

    /*Method to get Contact using first name*/
    private Contact getContactData(String first_name) {
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
}
