package a;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class SignedLicenseToken {
   public static final String a = "VB2";
   private static final int g = 2;
   private static final int h = 22;
   private static final int i = 86;
   private static final int j = 1;
   public final String b;
   public final String c;
   public final String d;
   public final long e;
   public final long f;
   private static final Map<String, SignedLicenseToken> k = Collections.synchronizedMap(new LinkedHashMap<String, SignedLicenseToken>(8, 0.75F, true) {
      protected boolean removeEldestEntry(Map.Entry<String, SignedLicenseToken> e) {
         return this.size() > 8;
      }
   });
   private static final Set<String> l = Collections.synchronizedSet(Collections.newSetFromMap(new LinkedHashMap<String, Boolean>(8, 0.75F, true) {
      protected boolean removeEldestEntry(Map.Entry<String, Boolean> e) {
         return this.size() > 8;
      }
   }));

   private SignedLicenseToken(String text, String id, String hwid, long issuedAt, long expiresAt) {
      this.b = text;
      this.c = id;
      this.d = hwid;
      this.e = issuedAt;
      this.f = expiresAt;
   }

   public static String a(String raw) {
      if (raw == null) {
         return "";
      } else {
         String var1 = raw.toUpperCase(Locale.ROOT);
         StringBuilder var2 = new StringBuilder(var1.length());

         for(int var3 = 0; var3 < var1.length(); ++var3) {
            char var4 = var1.charAt(var3);
            if (var4 >= 'A' && var4 <= 'Z' || var4 >= '0' && var4 <= '9') {
               var2.append(var4);
            }
         }

         return var2.toString();
      }
   }

   public static SignedLicenseToken b(String raw) {
      String var1 = a(raw);
      if (var1.length() >= "VB2".length() + 8 && var1.startsWith("VB2")) {
         SignedLicenseToken var2 = (SignedLicenseToken)k.get(var1);
         if (var2 != null) {
            return var2;
         } else if (l.contains(var1)) {
            return null;
         } else {
            SignedLicenseToken var3 = f(var1);
            if (var3 == null) {
               l.add(var1);
            } else {
               k.put(var1, var3);
            }

            return var3;
         }
      } else {
         return null;
      }
   }

   private static SignedLicenseToken f(String s) {
      byte[] var1 = LicenseCryptoCodec.a(s.substring("VB2".length()));
      if (var1 != null && var1.length == 86) {
         byte[] var2 = new byte[22];
         byte[] var3 = new byte[64];
         System.arraycopy(var1, 0, var2, 0, 22);
         System.arraycopy(var1, 22, var3, 0, 64);
         if (!LicenseCryptoCodec.a(var2, var3)) {
            return null;
         } else if ((var2[0] & 255) != 2) {
            return null;
         } else {
            boolean var4 = (var2[1] & 1) != 0;
            long var5 = a(var2, 2);
            long var7 = a(var2, 6);
            long var9 = a(var2, 10);
            if (var5 > 0L && var7 > var5) {
               StringBuilder var11 = new StringBuilder(8);

               for(int var12 = 0; var12 < 4; ++var12) {
                  var11.append(String.format("%02X", var2[10 + var12]));
               }

               StringBuilder var14 = new StringBuilder(16);
               if (!var4) {
                  for(int var13 = 0; var13 < 8; ++var13) {
                     var14.append(String.format("%02X", var2[14 + var13]));
                  }
               }

               return new SignedLicenseToken(s, var11.toString(), var14.toString(), var5, var7);
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   public boolean a() {
      return !this.d.isEmpty();
   }

   public boolean a(long nowMs) {
      return nowMs >= this.f * 1000L;
   }

   public long a(long nowMs, long seenMs) {
      return Math.max(0L, this.f * 1000L - Math.max(nowMs, seenMs));
   }

   public int b() {
      long var1 = (this.f - this.e) / 86400L;
      return var1 < 1L ? 1 : (int)Math.min(2147483647L, var1);
   }

   public static String c(String raw) {
      String var1 = a(raw);
      StringBuilder var2 = new StringBuilder(var1.length() + var1.length() / 8 + 1);

      for(int var3 = 0; var3 < var1.length(); ++var3) {
         if (var3 > 0 && var3 % 8 == 0) {
            var2.append('-');
         }

         var2.append(var1.charAt(var3));
      }

      return var2.toString();
   }

   public static String d(String raw) {
      String var1 = a(raw);
      if (var1.length() <= 22) {
         return c(var1);
      } else {
         String var10000 = var1.substring(0, 8);
         return var10000 + "-****-****-" + var1.substring(var1.length() - 6);
      }
   }

   private static long a(byte[] b, int off) {
      return (long)(b[off] & 255) << 24 | (long)((b[off + 1] & 255) << 16) | (long)((b[off + 2] & 255) << 8) | (long)(b[off + 3] & 255);
   }

   static byte[] e(String s) {
      return s.getBytes(StandardCharsets.US_ASCII);
   }
}
