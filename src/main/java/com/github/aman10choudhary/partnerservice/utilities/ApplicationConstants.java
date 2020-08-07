package com.github.aman10choudhary.partnerservice.utilities;

public class ApplicationConstants {

    public static class Errors {

        public static final String INVALID_SIZE = "Apologies for not being able to serve this request. Server cannot process results of negative size.";
        public static final String INVALID_FROM = "Apologies for not being able to serve this request. Server cannot process results of negative 'from' value.";
        public static final String NAME_NOT_NULL = "Company Name cannot be null";
        public static final String INVALID_LOCALE_FORMAT = "Locale format invalid, please use xx_XX format.";
        public static final String INVALID_DATE_FORMAT = "Date format invalid , please use yyyy-MM-dd'T'HH:mm:ssXXX format.";
        public static final String INTERNAL_SERVER_ERROR = "Ohhh shit!! Something went wrong";
        public static final String PARTNER_NOT_FOUND = "Partner with provided id Not Found";
        public static final String INVALID_FROM_SIZE_COMBINATION = "From should be either 0 or a multiple of size.";
    }
}
