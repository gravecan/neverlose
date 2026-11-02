package a;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class LicenseRevocationManager {
   public static final String a = "viabackward.feed";
   private static final String b = "/assets/chatpaches/revoked.txt";
   private static final long c = 900000L;
   private static final Duration d = Duration.ofSeconds(5L);
   private static volatile Set<String> e = Set.of();
   private static volatile Set<String> f = Set.of();
   private static volatile Set<String> g = Set.of();
   private static volatile Set<String> h = Set.of();
   private static volatile String i = "";
   private static volatile Path j;
   private static volatile long k;
   private static volatile boolean l;
   private static final AtomicBoolean m = new AtomicBoolean();

   private LicenseRevocationManager() {
   }

   public static synchronized void a(Path cache, String feed) {
      if (!l) {
         l = true;
         j = cache;
         i = feed == null ? "" : feed.trim();
         a(c());
         String var2 = d();
         if (var2 != null) {
            a(var2, true);
         }

         a();
      }
   }

   public static boolean a(String key, String hwid) {
      String var2 = d(key);
      if (var2 == null || !e.contains(var2) && !g.contains(var2)) {
         String var3 = e(hwid);
         return var3 != null && (f.contains(var3) || h.contains(var3));
      } else {
         return true;
      }
   }

   public static void a() {
      if (l && !i.isEmpty() && !m.get()) {
         long var0 = System.currentTimeMillis();
         if (var0 - k >= 900000L) {
            k = var0;
            if (m.compareAndSet(false, true)) {
               Thread var2 = new Thread(LicenseRevocationManager::b, "viabackward-revocations");
               var2.setDaemon(true);
               var2.start();
            }
         }
      }
   }

   private static void b() {
      try {
         HttpClient var0 = HttpClient.newBuilder().connectTimeout(d).build();
         HttpRequest var1 = HttpRequest.newBuilder(URI.create(i)).timeout(d).header("User-Agent", "viabackward").GET().build();
         HttpResponse var2 = var0.send(var1, BodyHandlers.ofString(StandardCharsets.UTF_8));
         if (var2.statusCode() == 200) {
            String var3 = (String)var2.body();
            if (a(var3, true)) {
               f(var3);
            }
         }
      } catch (Exception var7) {
      } finally {
         m.set(false);
      }

   }

   static synchronized void a(String text) {
      if (text != null && !text.isBlank()) {
         HashSet var1 = new HashSet(e);
         HashSet var2 = new HashSet(f);
         a(text, var1, var2);
         e = Set.copyOf(var1);
         f = Set.copyOf(var2);
      }
   }

   static synchronized boolean a(String text, boolean requireSignature) {
      if (text != null && !text.isBlank()) {
         if (requireSignature) {
            String var2 = b(text);
            if (var2 == null || !LicenseActivationState.a(c(text), var2)) {
               return false;
            }
         }

         HashSet var4 = new HashSet();
         HashSet var3 = new HashSet();
         a(text, var4, var3);
         g = Set.copyOf(var4);
         h = Set.copyOf(var3);
         return true;
      } else {
         return false;
      }
   }

   private static void a(String text, Set<String> keys, Set<String> hwids) {
      for(String var6 : text.replace("\r\n", "\n").split("\n")) {
         String var7 = var6.trim();
         if (!var7.isEmpty() && !var7.startsWith("#") && !var7.startsWith("NL-REVOCATIONS") && !var7.regionMatches(true, 0, "sig ", 0, 4)) {
            String[] var8 = var7.split("\\s+", 2);
            if (var8.length >= 2) {
               String var9 = var8[0].toLowerCase(Locale.ROOT);
               byte var10 = -1;
               switch (var9.hashCode()) {
                  case 106079:
                     if (var9.equals("key")) {
                        var10 = 0;
                     }
                     break;
                  case 3215978:
                     if (var9.equals("hwid")) {
                        var10 = 1;
                     }
               }

               switch (var10) {
                  case 0:
                     String var12 = d(var8[1]);
                     if (var12 != null) {
                        keys.add(var12);
                     }
                     break;
                  case 1:
                     String var11 = e(var8[1]);
                     if (var11 != null) {
                        hwids.add(var11);
                     }
               }
            }
         }
      }

   }

   private static String b(String text) {
      String[] var1 = text.replace("\r\n", "\n").split("\n", -1);

      for(int var2 = var1.length - 1; var2 >= 0; --var2) {
         String var3 = var1[var2].trim();
         if (!var3.isEmpty()) {
            return var3.regionMatches(true, 0, "sig ", 0, 4) ? var3.substring(4).trim() : null;
         }
      }

      return null;
   }

   private static String c(String text) {
      String var1 = text.replace("\r\n", "\n");
      int var2 = var1.lastIndexOf("\nsig ");
      return var2 < 0 ? null : var1.substring(0, var2 + 1);
   }

   private static String d(String raw) {
      if (raw == null) {
         return null;
      } else {
         StringBuilder var1 = new StringBuilder(raw.length());

         for(char var5 : raw.toUpperCase(Locale.ROOT).toCharArray()) {
            if (var5 >= 'A' && var5 <= 'Z' || var5 >= '0' && var5 <= '9') {
               var1.append(var5);
            }
         }

         return var1.length() != 8 && var1.length() != 16 ? null : var1.toString();
      }
   }

   private static String e(String raw) {
      return d(raw);
   }

   private static String c() {
      try {
         InputStream var0 = LicenseRevocationManager.class.getResourceAsStream("/assets/chatpaches/revoked.txt");

         String var1;
         try {
            var1 = var0 == null ? "" : new String(var0.readAllBytes(), StandardCharsets.UTF_8);
         } catch (Throwable var4) {
            if (var0 != null) {
               try {
                  var0.close();
               } catch (Throwable var3) {
                  var4.addSuppressed(var3);
               }
            }

            throw var4;
         }

         if (var0 != null) {
            var0.close();
         }

         return var1;
      } catch (Exception var5) {
         return "";
      }
   }

   private static String d() {
      Path var0 = j;
      if (var0 == null) {
         return null;
      } else {
         try {
            return Files.readString(var0, StandardCharsets.UTF_8);
         } catch (Exception var2) {
            return null;
         }
      }
   }

   private static void f(String body) {
      Path var1 = j;
      if (var1 != null) {
         try {
            Path var2 = var1.getParent();
            if (var2 != null) {
               Files.createDirectories(var2);
            }

            Path var3 = var1.resolveSibling(String.valueOf(var1.getFileName()) + ".tmp");
            Files.writeString(var3, body, StandardCharsets.UTF_8);
            Files.move(var3, var1, StandardCopyOption.REPLACE_EXISTING);
         } catch (Exception var4) {
         }

      }
   }
}
