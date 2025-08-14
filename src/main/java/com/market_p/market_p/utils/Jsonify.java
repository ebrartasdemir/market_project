package com.market_p.market_p.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Jsonify {
    public static String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj);
        }
    }
}
