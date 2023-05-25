package com.dizertatie.reactiveinvestigations.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final String[] DOMAINS = { "gmail.com", "yahoo.com", "hotmail.com", "outlook.com"};
    public static final String[] FIRST_NAMES = {"Andrei", "Ioana", "Mihai", "Ana"}; //, "Alexandru", "Maria", "Cristina", "Gabriel", "Elena", "Adrian", "Raluca", "Vlad", "Carmen", "Irina", "Ștefan", "Georgiana", "Florin", "Alina", "Dan", "Simona", "Radu", "Denisa", "Emil", "Madalina", "Vasile", "Laura", "Constantin", "Mihaela", "Lucian", "Diana"};
    public static final String[] LAST_NAMES = {"Popescu", "Ionescu", "Popa", "Georgescu"}; //, "Radu", "Mihai", "Nistor", "Dumitru", "Pop", "Stoica", "Stan", "Dobre", "Diaconu", "Dinu", "Costache", "Munteanu", "Florea", "Gheorghe", "Marin", "Apostol"};
    public static final String[] PRODUCT_TYPES = {"EMAIL", "CALL", "SMS", "FAX"};
    public static final List<String> PRODUCT_TYPES_LIST = Arrays.asList("EMAIL", "CALL", "SMS", "FAX");
    public static final String[] PRODUCT_DIRECTION = {"INCOMING", "OUTGOING"};
    public static final String EMAIL = "EMAIL";
    public static final String CALL = "CALL";
    public static final String SMS = "SMS";
    public static final String FAX = "FAX";
    public static final String INCOMING = "INCOMING";
    public static final String OUTGOING = "OUTGOING";
    public static final String NO_DURATION = "00:00";
    public static final int PRODUCTS_TO_GENERATE = 10;
}
