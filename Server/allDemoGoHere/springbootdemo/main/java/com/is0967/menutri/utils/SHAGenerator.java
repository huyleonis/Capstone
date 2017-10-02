package com.is0967.menutri.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHAGenerator {
   public static String generateSHA256(String string) {
      return Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
   }
}
