package com.bridgelabz.addressbook;

import java.util.List;

public class AddressBookService {
    private List<Contact> contactList;

    enum IOService {
        DB_IO
    }

    /**
     * Method to read Contact From AddressBook
     *
     * @param ioService
     * @return
     * @throws AddressBookException
     */
    public List<Contact> readAddressBookData(IOService ioService) throws AddressBookException {
        if (ioService.equals(IOService.DB_IO)) {
            this.contactList = new AddressBookDBService().readData();
        }
        return this.contactList;
    }
}
