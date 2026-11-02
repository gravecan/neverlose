package a;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;

public final class LicenseActivationState {
   private static final int e = 3;
   private static final int f = 16;
   private static final int g = 8;
   private static final byte[] h = c();
   private static final SecureRandom i = new SecureRandom();
   public final String a;
   public final long b;
   public final long c;
   public final String d;

   private LicenseActivationState(String lic, long since, long seen, String hwid) {
      this.a = lic;
      this.b = since;
      this.c = seen;
      this.d = hwid;
   }

   public static LicenseActivationState a(String licence, long now, String hwid) {
      SignedLicenseToken var4 = SignedLicenseToken.b(licence);
      if (var4 == null) {
         throw new IllegalArgumentException("invalid licence");
      } else {
         String var5 = hwid == null ? "" : hwid.trim().toUpperCase(Locale.ROOT);
         if (var5.length() != 16) {
            throw new IllegalArgumentException("invalid hardware id");
         } else {
            return new LicenseActivationState(var4.b, now, now, var5);
         }
      }
   }

   public static LicenseActivationState a(String licence, long since, long seen, String hwid) {
      SignedLicenseToken var6 = SignedLicenseToken.b(licence);
      if (var6 == null) {
         return null;
      } else if (since > 0L && seen >= since) {
         String var7 = hwid == null ? "" : hwid.trim().toUpperCase(Locale.ROOT);
         return var7.length() != 16 ? null : new LicenseActivationState(var6.b, since, seen, var7);
      } else {
         return null;
      }
   }

   public LicenseActivationState a(long now) {
      return now <= this.c ? this : new LicenseActivationState(this.a, this.b, now, this.d);
   }

   public long a(long now, long expiresAtMs) {
      return Math.max(0L, expiresAtMs - Math.max(now, this.c));
   }

   public String a() {
      byte[] var1 = new byte[8];
      i.nextBytes(var1);
      byte[] var2 = this.b();
      byte[] var3 = new byte[var2.length];
      byte[] var4 = a(var1, var2.length);

      for(int var5 = 0; var5 < var2.length; ++var5) {
         var3[var5] = (byte)(var2[var5] ^ var4[var5]);
      }

      byte[] var7 = a(a(var1, var3));
      byte[] var6 = new byte[8 + var3.length + 16];
      System.arraycopy(var1, 0, var6, 0, 8);
      System.arraycopy(var3, 0, var6, 8, var3.length);
      System.arraycopy(var7, 0, var6, 8 + var3.length, 16);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(var6);
   }

   public static LicenseActivationState a(String blob) {
      if (blob != null && !blob.isEmpty()) {
         try {
            byte[] var1 = Base64.getUrlDecoder().decode(blob.trim());
            if (var1.length <= 24) {
               return null;
            } else {
               int var2 = var1.length - 8 - 16;
               byte[] var3 = a(var1, 0, 8);
               byte[] var4 = a(var1, 8, 8 + var2);
               byte[] var5 = a(var1, 8 + var2, var1.length);
               byte[] var6 = a(a(var3, var4));
               if (!b(var5, a(var6, 0, 16))) {
                  return null;
               } else {
                  byte[] var7 = a(var3, var2);
                  byte[] var8 = new byte[var2];

                  for(int var9 = 0; var9 < var2; ++var9) {
                     var8[var9] = (byte)(var4[var9] ^ var7[var9]);
                  }

                  ByteBuffer var19 = ByteBuffer.wrap(var8);
                  if (var19.remaining() < 20) {
                     return null;
                  } else if (var19.get() != 3) {
                     return null;
                  } else {
                     long var10 = var19.getLong();
                     long var12 = var19.getLong();
                     int var14 = var19.get() & 255;
                     if (var14 != 16) {
                        return null;
                     } else {
                        int var15 = var19.getShort() & '\uffff';
                        if (var15 > 0 && var15 <= 2048) {
                           if (var19.remaining() != var14 + var15) {
                              return null;
                           } else {
                              byte[] var16 = new byte[var14];
                              var19.get(var16);
                              byte[] var17 = new byte[var15];
                              var19.get(var17);
                              return var19.hasRemaining() ? null : a(new String(var17, StandardCharsets.US_ASCII), var10, var12, new String(var16, StandardCharsets.US_ASCII));
                           }
                        } else {
                           return null;
                        }
                     }
                  }
               }
            }
         } catch (Exception var18) {
            return null;
         }
      } else {
         return null;
      }
   }

   private byte[] b() {
      byte[] var1 = this.a.getBytes(StandardCharsets.US_ASCII);
      byte[] var2 = this.d.getBytes(StandardCharsets.US_ASCII);
      ByteBuffer var3 = ByteBuffer.allocate(20 + var2.length + var1.length);
      var3.put((byte)3);
      var3.putLong(this.b);
      var3.putLong(this.c);
      var3.put((byte)var2.length);
      var3.putShort((short)var1.length);
      var3.put(var2);
      var3.put(var1);
      return var3.array();
   }

   public static String b(String text) {
      byte[] var1 = a(text.getBytes(StandardCharsets.UTF_8));
      return Base64.getUrlEncoder().withoutPadding().encodeToString(a(var1, 0, 16));
   }

   public static boolean a(String text, String signature) {
      if (text != null && signature != null && !signature.isEmpty()) {
         try {
            return b(Base64.getUrlDecoder().decode(signature.trim()), a(a(text.getBytes(StandardCharsets.UTF_8)), 0, 16));
         } catch (Exception var3) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static byte[] a(byte[] nonce, int len) {
      byte[] var2 = new byte[len];
      int var3 = 0;

      int var7;
      for(int var4 = 0; var3 < len; var3 += var7) {
         ByteBuffer var5 = ByteBuffer.allocate(h.length + nonce.length + 4);
         var5.put(h);
         var5.put(nonce);
         var5.putInt(var4++);
         byte[] var6 = b(var5.array());
         var7 = Math.min(var6.length, len - var3);
         System.arraycopy(var6, 0, var2, var3, var7);
      }

      return var2;
   }

   private static byte[] a(byte[] data) {
      byte[] var1 = h.length > 64 ? b(h) : h;
      byte[] var2 = new byte[64];
      System.arraycopy(var1, 0, var2, 0, var1.length);
      byte[] var3 = new byte[64];
      byte[] var4 = new byte[64];

      for(int var5 = 0; var5 < 64; ++var5) {
         var3[var5] = (byte)(var2[var5] ^ 54);
         var4[var5] = (byte)(var2[var5] ^ 92);
      }

      return b(a(var4, b(a(var3, data))));
   }

   private static byte[] b(byte[] d) {
      try {
         return MessageDigest.getInstance("SHA-256").digest(d);
      } catch (Exception var2) {
         throw new IllegalStateException(var2);
      }
   }

   private static byte[] c() {
      return Base64.getDecoder().decode("TnYyLWxpY2Vuc2UvOGYxYzQ3YTlkMzViNmUwMi1zdGF0ZS1zZWFs");
   }

   private static byte[] a(byte[] a, byte[] b) {
      byte[] var2 = new byte[a.length + b.length];
      System.arraycopy(a, 0, var2, 0, a.length);
      System.arraycopy(b, 0, var2, a.length, b.length);
      return var2;
   }

   private static byte[] a(byte[] a, int from, int to) {
      byte[] var3 = new byte[to - from];
      System.arraycopy(a, from, var3, 0, var3.length);
      return var3;
   }

   private static boolean b(byte[] a, byte[] b) {
      if (a.length != b.length) {
         return false;
      } else {
         int var2 = 0;

         for(int var3 = 0; var3 < a.length; ++var3) {
            var2 |= a[var3] ^ b[var3];
         }

         return var2 == 0;
      }
   }
}
