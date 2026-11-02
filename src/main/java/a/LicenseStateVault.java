package a;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class LicenseStateVault {
   private static final String a = System.getProperty("viabackward.vault.reg", "HKCU\\Software\\ViaBackward");
   private static final String b = "HKCU\\Software\\Zhuravel\\HandView";
   private static final String c = "state";
   private static final long d = 2000L;
   private static final ExecutorService e = Executors.newSingleThreadExecutor((r) -> {
      Thread var1 = new Thread(r, "viabackward-vault");
      var1.setDaemon(true);
      return var1;
   });
   private static volatile String f;

   private LicenseStateVault() {
   }

   private static boolean c() {
      return System.getProperty("os.name", "").toLowerCase().contains("win");
   }

   private static Path d() {
      if (!c()) {
         return Paths.get(System.getProperty("user.home", "."), ".viabackward", "hv.dat");
      } else {
         String var0 = System.getenv("LOCALAPPDATA");
         if (var0 == null || var0.isEmpty()) {
            var0 = Paths.get(System.getProperty("user.home", "."), "AppData", "Local").toString();
         }

         return Paths.get(var0, "ViaBackward", "hv.dat");
      }
   }

   private static Path e() {
      if (!c()) {
         return Paths.get(System.getProperty("user.home", "."), ".zhuravel", "hv.dat");
      } else {
         String var0 = System.getenv("LOCALAPPDATA");
         if (var0 == null || var0.isEmpty()) {
            var0 = Paths.get(System.getProperty("user.home", "."), "AppData", "Local").toString();
         }

         return Paths.get(var0, "Zhuravel", "hv.dat");
      }
   }

   public static String a() {
      String var0 = null;
      LicenseActivationState var1 = null;

      for(String var5 : new String[]{a(d()), a(e()), d(a), d("HKCU\\Software\\Zhuravel\\HandView")}) {
         LicenseActivationState var6 = LicenseActivationState.a(var5);
         if (var6 != null && (var1 == null || var6.c > var1.c)) {
            var1 = var6;
            var0 = var5;
         }
      }

      return var0;
   }

   public static void a(String blob) {
      e.execute(() -> b(blob));
   }

   private static void b(String blob) {
      c(blob);
      String var1 = blob == null ? "" : blob;
      if (!var1.equals(f)) {
         e(blob);
         f = var1;
      }
   }

   public static void b() {
      try {
         e.submit(() -> {
         }).get(5L, TimeUnit.SECONDS);
      } catch (Exception var1) {
      }

   }

   private static String a(Path path) {
      try {
         return Files.readString(path).trim();
      } catch (Exception var2) {
         return null;
      }
   }

   private static void c(String blob) {
      try {
         Path var1 = d();
         Files.createDirectories(var1.getParent());
         Path var2 = var1.resolveSibling("hv.dat.tmp");
         Files.writeString(var2, blob == null ? "" : blob, StandardCharsets.US_ASCII);
         Files.move(var2, var1, StandardCopyOption.REPLACE_EXISTING);
      } catch (Exception var3) {
      }

   }

   private static String d(String path) {
      if (!c()) {
         return null;
      } else {
         try {
            Process var1 = (new ProcessBuilder(new String[]{"reg", "query", path, "/v", "state"})).redirectErrorStream(true).start();
            String var2 = new String(var1.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (!var1.waitFor(2000L, TimeUnit.MILLISECONDS)) {
               var1.destroyForcibly();
               return null;
            } else {
               int var3 = var2.indexOf("REG_SZ");
               return var3 < 0 ? null : var2.substring(var3 + 6).trim();
            }
         } catch (Exception var4) {
            return null;
         }
      }
   }

   private static void e(String blob) {
      if (c()) {
         try {
            Process var1 = (new ProcessBuilder(new String[]{"reg", "add", a, "/v", "state", "/t", "REG_SZ", "/d", blob == null ? "" : blob, "/f"})).redirectErrorStream(true).start();
            if (!var1.waitFor(2000L, TimeUnit.MILLISECONDS)) {
               var1.destroyForcibly();
            }
         } catch (Exception var2) {
         }

      }
   }
}
