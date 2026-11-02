package a;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public final class LicenseCryptoCodec {
   private static final String a = "MCowBQYDK2VwAyEAyzP2oGpixEd0MvDcVITZKhKc9nzg+36r02gCvgYVCx0=";
   private static final char[] b = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567".toCharArray();
   private static final int[] c = new int[128];
   private static volatile PublicKey d;

   private LicenseCryptoCodec() {
   }

   public static boolean a(byte[] data, byte[] signature) {
      if (data != null && signature != null && signature.length == 64) {
         try {
            Signature var2 = Signature.getInstance("Ed25519");
            var2.initVerify(a());
            var2.update(data);
            return var2.verify(signature);
         } catch (Exception var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static PublicKey a() throws Exception {
      PublicKey var0 = d;
      if (var0 != null) {
         return var0;
      } else {
         synchronized(LicenseCryptoCodec.class) {
            if (d == null) {
               byte[] var2 = Base64.getDecoder().decode("MCowBQYDK2VwAyEAyzP2oGpixEd0MvDcVITZKhKc9nzg+36r02gCvgYVCx0=");
               d = KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(var2));
            }

            return d;
         }
      }
   }

   public static String a(byte[] in) {
      StringBuilder var1 = new StringBuilder((in.length * 8 + 4) / 5);
      int var2 = 0;
      int var3 = 0;

      for(byte var7 : in) {
         var2 = var2 << 8 | var7 & 255;

         for(var3 += 8; var3 >= 5; var3 -= 5) {
            var1.append(b[var2 >>> var3 - 5 & 31]);
         }
      }

      if (var3 > 0) {
         var1.append(b[var2 << 5 - var3 & 31]);
      }

      return var1.toString();
   }

   public static byte[] a(String text) {
      if (text != null && !text.isEmpty()) {
         int var1 = text.length();
         byte[] var2 = new byte[var1 * 5 / 8];
         int var3 = 0;
         int var4 = 0;
         int var5 = 0;

         for(int var6 = 0; var6 < var1; ++var6) {
            char var7 = Character.toUpperCase(text.charAt(var6));
            int var8 = var7 < 128 ? c[var7] : -1;
            if (var8 < 0) {
               return null;
            }

            var3 = var3 << 5 | var8;
            var4 += 5;
            if (var4 >= 8) {
               if (var5 >= var2.length) {
                  return null;
               }

               var2[var5++] = (byte)(var3 >>> var4 - 8 & 255);
               var4 -= 8;
            }
         }

         return var5 == var2.length ? var2 : null;
      } else {
         return null;
      }
   }

   public static byte[] b(String s) {
      return s.getBytes(StandardCharsets.UTF_8);
   }

   static {
      Arrays.fill(c, -1);

      for(int var0 = 0; var0 < b.length; c[b[var0]] = var0++) {
      }

      c[48] = 14;
      c[49] = 8;
      c[56] = 11;
   }
}
