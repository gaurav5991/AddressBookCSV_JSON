package com.bridgelabz.addressbook;

public class AddressBookException extends Exception{
    enum ExceptionType{
        SQL_EXCEPTION, UNABLE_TO_UPDATE
    }
    ExceptionType type;

    public AddressBookException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public AddressBookException(ExceptionType type) {
        this.type = type;
    }
}
