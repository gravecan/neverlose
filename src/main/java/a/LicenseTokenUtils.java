package a;

public final class LicenseTokenUtils {
   private LicenseTokenUtils() {
   }

   public static String a(String raw) {
      return SignedLicenseToken.a(raw);
   }

   public static boolean b(String raw) {
      return SignedLicenseToken.b(raw) != null;
   }

   public static int c(String raw) {
      SignedLicenseToken var1 = SignedLicenseToken.b(raw);
      return var1 == null ? 0 : var1.b();
   }

   public static String d(String raw) {
      return SignedLicenseToken.c(raw);
   }

   public static String e(String raw) {
      return SignedLicenseToken.d(raw);
   }

   public static String f(String raw) {
      SignedLicenseToken var1 = SignedLicenseToken.b(raw);
      return var1 == null ? "" : var1.c;
   }
}
