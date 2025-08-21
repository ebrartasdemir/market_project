package com.market_p.market_p.utils;

import java.util.Random;

public class OrderUtils {
    private static final String ALPHAPET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String orderCodeGenerator(){
        return "ON-"
                +generateNumericPart(3)+"-"
                +generateCharecterPart(4)+"-"+
                generateNumericPart(3)+"-"
                +generateCharecterPart(2);

    }
    private static String generateCharecterPart(int length){
        Random rng = new Random();
        String word = "";
        for(int i = 0; i < length; i++){
            int randomIndex=rng.nextInt(ALPHAPET.length());
            word+=ALPHAPET.charAt(randomIndex);
        }
        return word;
    }
    private static String generateNumericPart(int length){
        Random rng = new Random();
        String word = "";
        for(int i = 0; i < length; i++){
            int numeric=rng.nextInt(10);
            word+=numeric;
        }
        return word;
    }
}
