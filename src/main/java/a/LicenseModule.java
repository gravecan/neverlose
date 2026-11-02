package a;

public class LicenseModule extends ToggleableModule {
   public static LicenseModule a;
   private static final long c = 300000L;
   public final LicenseKeySetting b = this.b("License Key", "");
   private static LicenseActivationState d;
   private static boolean e;
   private static long f;

   public LicenseModule() {
      super("License", "Paste your license token here to activate the modules.", -1);
      this.u = "MISC";
      this.v = false;
      a = this;
      this.b.a = "";
   }

   public static void a(String fromConfig, String fromMirror, String fromVault) {
      LicenseActivationState var3 = LicenseActivationState.a(fromConfig);
      LicenseActivationState var4 = LicenseActivationState.a(fromMirror);
      LicenseActivationState var5 = LicenseActivationState.a(fromVault);
      boolean var6 = fromConfig != null && !fromConfig.isEmpty() && var3 == null;
      boolean var7 = fromMirror != null && !fromMirror.isEmpty() && var4 == null;
      boolean var8 = fromVault != null && !fromVault.isEmpty() && var5 == null;
      LicenseActivationState var9 = selectLatestState(var3, var4, var5);
      if (!var6 && !var7 && !var8) {
         if (var9 == null) {
            d = null;
            e = false;
         } else if (hasConflictingLicenses(var9, var3, var4, var5)) {
            e = true;
            d = var9;
         } else {
            d = var9;
            e = false;
            if (var5 == null) {
               LicenseStateVault.a(var9.a());
            }

         }
      } else {
         e = true;
         d = var9;
      }
   }

   private static LicenseActivationState selectLatestState(LicenseActivationState... rs) {
      LicenseActivationState var1 = null;

      for(LicenseActivationState var5 : rs) {
         if (var5 != null && (var1 == null || var5.c > var1.c)) {
            var1 = var5;
         }
      }

      return var1;
   }

   private static boolean hasConflictingLicenses(LicenseActivationState reference, LicenseActivationState... rs) {
      for(LicenseActivationState var5 : rs) {
         if (var5 != null && !var5.a.equals(reference.a)) {
            return true;
         }
      }

      return false;
   }

   public static void a(String oldSince, String oldBound) {
   }

   public static String a() {
      return d == null ? "" : d.a();
   }

   private static void q() {
      f = System.currentTimeMillis();
      LicenseStateVault.a(a());
      ModConfigStore.a();
   }

   private static SignedLicenseToken r() {
      return a == null ? null : SignedLicenseToken.b(a.b.a);
   }

   private static LicenseStateValidator.Result s() {
      long var0 = System.currentTimeMillis();
      SignedLicenseToken var2 = r();
      LicenseStateValidator.Result var3 = LicenseStateValidator.a(var2, d, e, HardwareIdProvider.a(), var0);
      if (var3.a == LicenseStateValidator.State.b) {
         if (LicenseRevocationManager.a(var2.c, HardwareIdProvider.a())) {
            return new LicenseStateValidator.Result(LicenseStateValidator.State.g, 0L, (LicenseActivationState)null);
         } else if (var2.a() && !var2.d.equalsIgnoreCase(HardwareIdProvider.a())) {
            return new LicenseStateValidator.Result(LicenseStateValidator.State.e, 0L, (LicenseActivationState)null);
         } else {
            d = LicenseActivationState.a(var2.b, var0, HardwareIdProvider.a());
            e = false;
            q();
            return new LicenseStateValidator.Result(LicenseStateValidator.State.c, d.a(var0, var2.f * 1000L), d);
         }
      } else {
         if (var3.c != null) {
            boolean var4 = var3.c.c > (d == null ? 0L : d.c);
            d = var3.c;
            if (var4 && var0 - f > 300000L && !e) {
               q();
            }
         }

         return var3.a == LicenseStateValidator.State.c && LicenseRevocationManager.a(var2.c, HardwareIdProvider.a()) ? new LicenseStateValidator.Result(LicenseStateValidator.State.g, 0L, var3.c) : var3;
      }
   }

   public static boolean b() {
      return ClassIntegrityValidator.a() && s().a();
   }

   public static boolean c() {
      return s().a == LicenseStateValidator.State.d;
   }

   public static boolean d() {
      return s().a == LicenseStateValidator.State.g;
   }

   public static LicenseStateValidator.State e() {
      return !ClassIntegrityValidator.a() ? LicenseStateValidator.State.f : s().a;
   }

   public static long f() {
      return s().b;
   }

   public static String g() {
      String var10000;
      switch (e()) {
         case c -> var10000 = "ACTIVE";
         case d -> var10000 = "EXPIRED";
         case e -> var10000 = "OTHER PC";
         case f -> var10000 = "TAMPERED";
         case g -> var10000 = "REVOKED";
         default -> var10000 = "NOT ACTIVE";
      }

      return var10000;
   }

   public static boolean h() {
      if (d == null) {
         return false;
      } else {
         SignedLicenseToken var0 = SignedLicenseToken.b(d.a);
         return var0 != null && d.d.equalsIgnoreCase(HardwareIdProvider.a()) && !LicenseRevocationManager.a(var0.c, HardwareIdProvider.a());
      }
   }

   public static String a(String entered) {
      if (!h()) {
         return entered == null ? "" : entered;
      } else {
         SignedLicenseToken var1 = SignedLicenseToken.b(entered);
         if (var1 == null) {
            return SignedLicenseToken.c(d.a);
         } else {
            return !var1.b.equals(d.a) && i() ? SignedLicenseToken.c(d.a) : entered;
         }
      }
   }

   public static boolean i() {
      return e() == LicenseStateValidator.State.c;
   }

   public static void b(String raw) {
      SignedLicenseToken var1 = SignedLicenseToken.b(raw);
      if (var1 != null) {
         if (d == null || !d.a.equals(var1.b)) {
            if (d == null || !i()) {
               if (!LicenseRevocationManager.a(var1.c, HardwareIdProvider.a())) {
                  if (!var1.a() || var1.d.equalsIgnoreCase(HardwareIdProvider.a())) {
                     d = LicenseActivationState.a(var1.b, System.currentTimeMillis(), HardwareIdProvider.a());
                     e = false;
                     q();
                  }
               }
            }
         }
      }
   }

   public static String j() {
      long var0 = f() / 1000L;
      long var2 = var0 / 86400L;
      var0 %= 86400L;
      long var4 = var0 / 3600L;
      var0 %= 3600L;
      long var6 = var0 / 60L;
      var0 %= 60L;
      StringBuilder var8 = new StringBuilder();
      if (var2 > 0L) {
         var8.append(var2).append("d ");
      }

      if (var4 > 0L || var2 > 0L) {
         var8.append(var4).append("h ");
      }

      if (var6 > 0L || var4 > 0L || var2 > 0L) {
         var8.append(var6).append("m ");
      }

      var8.append(var0).append("s");
      return var8.toString();
   }

   public static int k() {
      SignedLicenseToken var0 = r();
      return var0 == null ? 0 : var0.b();
   }

   public static String l() {
      return a == null ? "" : SignedLicenseToken.d(a.b.a);
   }

   public static String m() {
      return HardwareIdProvider.b();
   }

   public static boolean n() {
      return d != null && d.d.equalsIgnoreCase(HardwareIdProvider.a());
   }

   public static String o() {
      SignedLicenseToken var0 = r();
      return var0 == null ? "" : var0.c;
   }
}
