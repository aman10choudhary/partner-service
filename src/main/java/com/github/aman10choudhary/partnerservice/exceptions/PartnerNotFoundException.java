package com.github.aman10choudhary.partnerservice.exceptions;

public class PartnerNotFoundException extends RuntimeException {

    public PartnerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}