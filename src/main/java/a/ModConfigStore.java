package a;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import net.fabricmc.loader.api.FabricLoader;

public final class ModConfigStore {
   private static final String a = "AutoClick.Reaction delay";
   private static final ExecutorService b = Executors.newSingleThreadExecutor((r) -> {
      Thread var1 = new Thread(r, "viabackward-config");
      var1.setDaemon(true);
      return var1;
   });

   private ModConfigStore() {
   }

   private static Path c() {
      return FabricLoader.getInstance().getConfigDir().resolve("viabackward.properties");
   }

   private static Path[] d() {
      Path var0 = FabricLoader.getInstance().getConfigDir();
      return new Path[]{var0.resolve("zhuravelhandview.properties"), var0.resolve("miniclient.properties")};
   }

   private static boolean a(Properties p, Path file) {
      try {
         InputStream var2 = Files.newInputStream(file);

         boolean var3;
         try {
            p.load(var2);
            var3 = true;
         } catch (Throwable var6) {
            if (var2 != null) {
               try {
                  var2.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (var2 != null) {
            var2.close();
         }

         return var3;
      } catch (IOException var7) {
         return false;
      }
   }

   public static void a(List<ToggleableModule> modules) {
      Properties var1 = new Properties();
      if (!a(var1, c())) {
         boolean var2 = false;

         for(Path var6 : d()) {
            var2 |= a(var1, var6);
         }

         if (!var2) {
            return;
         }
      }

      String var23 = var1.getProperty("gui.key");
      if (var23 != null) {
         try {
            CombatClientInitializer.g = Integer.parseInt(var23.trim());
         } catch (NumberFormatException var22) {
         }
      }

      try {
         ModuleSettingsScreen.a = Float.parseFloat(var1.getProperty("gui.px", "NaN").trim());
         ModuleSettingsScreen.b = Float.parseFloat(var1.getProperty("gui.py", "NaN").trim());
      } catch (NumberFormatException var21) {
      }

      String var24 = var1.getProperty("license.since");
      String var25 = var1.getProperty("license.bound");
      LicenseModule.a(var1.getProperty("license.state"), g(), LicenseStateVault.a());
      String var26 = var1.getProperty("version");
      boolean var27 = "2".equals(var26) || "3".equals(var26) || "4".equals(var26);

      for(ToggleableModule var8 : modules) {
         String var9 = var1.getProperty(var8.o + ".key");
         if (var9 != null) {
            try {
               var8.s = Integer.parseInt(var9.trim());
            } catch (NumberFormatException var18) {
            }
         }

         if (var27) {
            if (var8.v && "true".equals(var1.getProperty(var8.o + ".enabled"))) {
               var8.a(true);
            }

            for(Setting var11 : var8.r) {
               String var12 = var1.getProperty(var8.o + "." + var11.f);
               if (var12 != null && (!"AutoClick.Reaction delay".equals(var8.o + "." + var11.f) || !"300".equals(var12.trim()))) {
                  if (var11 instanceof BooleanSetting) {
                     BooleanSetting var13 = (BooleanSetting)var11;
                     var13.a = Boolean.parseBoolean(var12.trim());
                  } else if (var11 instanceof SliderSetting) {
                     SliderSetting var14 = (SliderSetting)var11;

                     try {
                        var14.a(Double.parseDouble(var12.trim()));
                     } catch (NumberFormatException var20) {
                     }
                  } else if (var11 instanceof ChoiceSetting) {
                     ChoiceSetting var15 = (ChoiceSetting)var11;

                     try {
                        var15.a(Integer.parseInt(var12.trim()));
                     } catch (NumberFormatException var19) {
                     }
                  } else if (var11 instanceof LicenseKeySetting) {
                     LicenseKeySetting var16 = (LicenseKeySetting)var11;
                     var16.a(var12);
                  }
               }
            }
         }
      }

      LicenseModule.a(var24, var25);
   }

   private static Path e() {
      return FabricLoader.getInstance().getGameDir().resolve(".vb");
   }

   private static Path[] f() {
      Path var0 = FabricLoader.getInstance().getGameDir();
      return new Path[]{var0.resolve(".zhv"), var0.resolve(".nlc")};
   }

   private static String g() {
      String var0 = a(e());
      if (var0 != null) {
         return var0;
      } else {
         for(Path var4 : f()) {
            var0 = a(var4);
            if (var0 != null) {
               return var0;
            }
         }

         return null;
      }
   }

   private static String a(Path file) {
      try {
         return Files.readString(file).trim();
      } catch (Exception var2) {
         return null;
      }
   }

   private static void a(String blob) {
      try {
         Path var1 = e();
         Path var2 = var1.resolveSibling(".vb.tmp");
         Files.writeString(var2, blob == null ? "" : blob);
         Files.move(var2, var1, StandardCopyOption.REPLACE_EXISTING);
      } catch (Exception var3) {
      }

   }

   public static void a() {
      b(CombatClientInitializer.f);
   }

   public static synchronized void b(List<ToggleableModule> modules) {
      Properties var1 = c(modules);
      b.execute(() -> a(var1));
   }

   public static void b() {
      a();

      try {
         b.submit(() -> {
         }).get(5L, TimeUnit.SECONDS);
      } catch (Exception var1) {
      }

      LicenseStateVault.b();
   }

   private static Properties c(List<ToggleableModule> modules) {
      Properties var1 = new Properties();
      var1.setProperty("version", "4");
      var1.setProperty("gui.key", String.valueOf(CombatClientInitializer.g));
      if (!Float.isNaN(ModuleSettingsScreen.a)) {
         var1.setProperty("gui.px", String.valueOf(ModuleSettingsScreen.a));
         var1.setProperty("gui.py", String.valueOf(ModuleSettingsScreen.b));
      }

      var1.setProperty("license.state", LicenseModule.a());

      for(ToggleableModule var3 : modules) {
         var1.setProperty(var3.o + ".key", String.valueOf(var3.s));
         if (var3.v) {
            var1.setProperty(var3.o + ".enabled", String.valueOf(var3.p()));
         }

         for(Setting var5 : var3.r) {
            if (var5 instanceof BooleanSetting var6) {
               var1.setProperty(var3.o + "." + var5.f, String.valueOf(var6.a));
            } else if (var5 instanceof SliderSetting var7) {
               var1.setProperty(var3.o + "." + var5.f, String.valueOf(var7.e));
            } else if (var5 instanceof ChoiceSetting var8) {
               var1.setProperty(var3.o + "." + var5.f, String.valueOf(var8.b));
            } else if (var5 instanceof LicenseKeySetting var9) {
               var1.setProperty(var3.o + "." + var5.f, var9.a);
            }
         }
      }

      return var1;
   }

   private static void a(Properties p) {
      try {
         Path var1 = c();
         Path var2 = var1.resolveSibling("viabackward.properties.tmp");
         Files.createDirectories(var1.getParent());
         OutputStream var3 = Files.newOutputStream(var2);

         try {
            p.store(var3, "ViaBackward");
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

         Files.move(var2, var1, StandardCopyOption.REPLACE_EXISTING);
         String var9 = p.getProperty("license.state", "");
         a(var9);
         LicenseStateVault.a(var9);
      } catch (IOException var8) {
      }

   }
}
