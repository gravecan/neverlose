package a;

import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

public final class HardwareIdProvider {
   private static volatile String a;

   private HardwareIdProvider() {
   }

   public static String a() {
      String var0 = a;
      if (var0 != null) {
         return var0;
      } else {
         synchronized(HardwareIdProvider.class) {
            if (a == null) {
               a = c();
            }

            return a;
         }
      }
   }

   public static String b() {
      String var0 = a();
      String var10000 = var0.substring(0, 4);
      return var10000 + "-" + var0.substring(4, 8) + "-" + var0.substring(8, 12) + "-" + var0.substring(12, 16);
   }

   private static String c() {
      String var10000 = System.getProperty("os.name", "");
      String var0 = var10000 + "|" + System.getProperty("os.arch", "");
      String var1 = d();
      if (var1 == null) {
         var1 = e();
      }

      if (var1 == null || var1.isEmpty()) {
         var10000 = System.getProperty("user.home", "");
         var1 = var10000 + "|" + System.getProperty("user.name", "");
      }

      return a(var0 + "|" + var1);
   }

   private static String d() {
      if (!System.getProperty("os.name", "").toLowerCase().contains("win")) {
         return null;
      } else {
         try {
            Process var0 = (new ProcessBuilder(new String[]{"reg", "query", "HKLM\\SOFTWARE\\Microsoft\\Cryptography", "/v", "MachineGuid"})).redirectErrorStream(true).start();
            String var1 = new String(var0.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (!var0.waitFor(2L, TimeUnit.SECONDS)) {
               var0.destroyForcibly();
               return null;
            } else {
               int var2 = var1.indexOf("REG_SZ");
               if (var2 < 0) {
                  return null;
               } else {
                  String var3 = var1.substring(var2 + 6).trim();
                  return var3.isEmpty() ? null : var3.toLowerCase();
               }
            }
         } catch (Exception var4) {
            return null;
         }
      }
   }

   private static String e() {
      try {
         Enumeration var0 = NetworkInterface.getNetworkInterfaces();
         ArrayList var1 = new ArrayList();

         while(var0 != null && var0.hasMoreElements()) {
            NetworkInterface var2 = (NetworkInterface)var0.nextElement();

            try {
               if (!var2.isLoopback() && !var2.isVirtual() && var2.isUp()) {
                  byte[] var3 = var2.getHardwareAddress();
                  if (var3 != null && var3.length == 6) {
                     StringBuilder var4 = new StringBuilder();

                     for(byte var8 : var3) {
                        var4.append(String.format("%02x", var8));
                     }

                     var1.add(var4.toString());
                  }
               }
            } catch (Exception var9) {
            }
         }

         if (var1.isEmpty()) {
            return null;
         } else {
            Collections.sort(var1);
            return String.join(",", var1);
         }
      } catch (Exception var10) {
         return null;
      }
   }

   private static String a(String s) {
      try {
         byte[] var1 = MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8));
         StringBuilder var2 = new StringBuilder();

         for(int var3 = 0; var3 < 8; ++var3) {
            var2.append(String.format("%02X", var1[var3]));
         }

         return var2.toString();
      } catch (Exception var4) {
         return "0000000000000000";
      }
   }
}
