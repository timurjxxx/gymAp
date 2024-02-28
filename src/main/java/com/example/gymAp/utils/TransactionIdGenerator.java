package com.example.gymAp.utils;


import java.util.UUID;

public class TransactionIdGenerator {

    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}