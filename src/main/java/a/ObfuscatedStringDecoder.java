package a;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

public final class ObfuscatedStringDecoder {
   private static final byte[] a = a();
   private static final ConcurrentHashMap<String, String> b = new ConcurrentHashMap();

   private ObfuscatedStringDecoder() {
   }

   public static String a(String encoded) {
      if (encoded == null) {
         return null;
      } else if (encoded.isEmpty()) {
         return encoded;
      } else {
         String var1 = (String)b.get(encoded);
         if (var1 != null) {
            return var1;
         } else {
            String var2;
            try {
               var2 = b(encoded);
            } catch (Exception var4) {
               var2 = encoded;
            }

            b.putIfAbsent(encoded, var2);
            return var2;
         }
      }
   }

   private static String b(String encoded) throws Exception {
      byte[] var1 = Base64.getDecoder().decode(encoded);
      if (var1.length < 5) {
         return encoded;
      } else {
         byte[] var2 = Arrays.copyOfRange(var1, 0, 4);
         byte[] var3 = Arrays.copyOfRange(var1, 4, var1.length);
         byte[] var4 = a(var2, var3.length);
         byte[] var5 = new byte[var3.length];

         for(int var6 = 0; var6 < var3.length; ++var6) {
            var5[var6] = (byte)(var3[var6] ^ var4[var6]);
         }

         return new String(var5, StandardCharsets.UTF_8);
      }
   }

   private static byte[] a(byte[] iv, int len) throws Exception {
      byte[] var2 = new byte[len];
      int var3 = 0;

      for(int var4 = 0; var3 < len; ++var4) {
         MessageDigest var5 = MessageDigest.getInstance("SHA-256");
         var5.update(a);
         var5.update(iv);
         var5.update(new byte[]{(byte)(var4 >>> 24), (byte)(var4 >>> 16), (byte)(var4 >>> 8), (byte)var4});
         byte[] var6 = var5.digest();
         int var7 = Math.min(var6.length, len - var3);
         System.arraycopy(var6, 0, var2, var3, var7);
         var3 += var7;
      }

      return var2;
   }

   private static byte[] a() {
      String var0 = "Vb7kQ2m9";
      String var1 = "Z4rT8sL1";
      return (var0 + "-" + var1).getBytes(StandardCharsets.UTF_8);
   }
}
