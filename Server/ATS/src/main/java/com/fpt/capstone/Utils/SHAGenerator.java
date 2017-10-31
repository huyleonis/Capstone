package com.fpt.capstone.utils;


import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHAGenerator {
    public static String generateSHA256(String string){
        return Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
    }
}
