package com.invify.utils;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {

    public static String wildcardWrapper(String str) {
        return "%" + str + "%";
    }
}
