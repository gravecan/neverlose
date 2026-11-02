package a;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ClassIntegrityValidator {
   private static final String a = "assets/chatpaches/seal.txt";
   private static final int b = 10;
   private static volatile Boolean c;

   private ClassIntegrityValidator() {
   }

   public static boolean a() {
      Boolean var0 = c;
      if (var0 != null) {
         return var0;
      } else {
         synchronized(ClassIntegrityValidator.class) {
            if (c == null) {
               c = b();
            }

            return c;
         }
      }
   }

   private static boolean b() {
      try {
         File var0 = c();
         if (var0 != null) {
            ZipFile var1 = new ZipFile(var0);

            boolean var12;
            label60: {
               boolean var13;
               try {
                  ZipEntry var2 = var1.getEntry("assets/chatpaches/seal.txt");
                  if (var2 == null) {
                     var12 = false;
                     break label60;
                  }

                  byte[] sealBytes;
                  InputStream var4 = var1.getInputStream(var2);

                  try {
                     sealBytes = var4.readAllBytes();
                  } catch (Throwable var9) {
                     if (var4 != null) {
                        try {
                           var4.close();
                        } catch (Throwable var8) {
                           var9.addSuppressed(var8);
                        }
                     }

                     throw var9;
                  }

                  if (var4 != null) {
                     var4.close();
                  }

                  var13 = a(sealBytes, (Reader)((path) -> {
                     ZipEntry entry = var1.getEntry(path);
                     if (entry == null) {
                        return null;
                     } else {
                        InputStream var3 = var1.getInputStream(entry);

                        byte[] content;
                        try {
                           content = var3.readAllBytes();
                        } catch (Throwable var7) {
                           if (var3 != null) {
                              try {
                                 var3.close();
                              } catch (Throwable var6) {
                                 var7.addSuppressed(var6);
                              }
                           }

                           throw var7;
                        }

                        if (var3 != null) {
                           var3.close();
                        }

                        return content;
                     }
                  }));
               } catch (Throwable var10) {
                  try {
                     var1.close();
                  } catch (Throwable var7) {
                     var10.addSuppressed(var7);
                  }

                  throw var10;
               }

               var1.close();
               return var13;
            }

            var1.close();
            return var12;
         } else {
            return a(a("assets/chatpaches/seal.txt"), ClassIntegrityValidator::a);
         }
      } catch (Throwable var11) {
         return false;
      }
   }

   private static File c() {
      try {
         URL var0 = ClassIntegrityValidator.class.getProtectionDomain().getCodeSource().getLocation();
         if (var0 == null) {
            return null;
         } else {
            File var1 = new File(var0.toURI());
            return var1.isFile() ? var1 : null;
         }
      } catch (Exception var2) {
         return null;
      }
   }

   private static boolean a(byte[] sealBytes, Reader source) throws Exception {
      if (sealBytes == null) {
         return false;
      } else {
         String var2 = (new String(sealBytes, StandardCharsets.UTF_8)).replace("\r\n", "\n");
         int var3 = var2.lastIndexOf("\nsig ");
         if (var3 < 0) {
            return false;
         } else {
            String var4 = var2.substring(0, var3 + 1);

            try {
               byte[] var5 = Base64.getUrlDecoder().decode(var2.substring(var3 + 5).trim());
            } catch (IllegalArgumentException var16) {
               return false;
            }

            var4.getBytes(StandardCharsets.UTF_8);
            if (false) {
               return false;
            } else {
               int var6 = 0;

               for(String var10 : var4.split("\n")) {
                  String var11 = var10.trim();
                  if (!var11.isEmpty() && !var11.startsWith("#") && !var11.startsWith("VBSEAL")) {
                     int var12 = var11.indexOf(32);
                     if (var12 != 64) {
                        return false;
                     }

                     String var13 = var11.substring(0, var12);
                     String var14 = var11.substring(var12 + 1).trim();
                     if (!var14.endsWith(".class") || var14.contains("..")) {
                        return false;
                     }

                     byte[] var15 = source.read(var14);
                     if (var15 == null) {
                        return false;
                     }

                     if (!var13.equalsIgnoreCase(b(a(var15)))) {
                        return false;
                     }

                     ++var6;
                  }
               }

               return var6 >= 10;
            }
         }
      }
   }

   private static byte[] a(String path) {
      try {
         InputStream var1 = ClassIntegrityValidator.class.getClassLoader().getResourceAsStream(path);

         byte[] var2;
         try {
            var2 = var1 == null ? null : var1.readAllBytes();
         } catch (Throwable var5) {
            if (var1 != null) {
               try {
                  var1.close();
               } catch (Throwable var4) {
                  var5.addSuppressed(var4);
               }
            }

            throw var5;
         }

         if (var1 != null) {
            var1.close();
         }

         return var2;
      } catch (Exception var6) {
         return null;
      }
   }

   private static byte[] a(byte[] d) throws Exception {
      return MessageDigest.getInstance("SHA-256").digest(d);
   }

   private static String b(byte[] b) {
      StringBuilder var1 = new StringBuilder(b.length * 2);

      for(byte var5 : b) {
         var1.append(String.format("%02x", var5));
      }

      return var1.toString();
   }

   interface Reader {
      byte[] read(String var1) throws Exception;
   }
}
