package a;

public class LicenseKeySetting extends Setting {
   public String a;

   public LicenseKeySetting(String name, String def) {
      super(name);
      this.a = def;
   }

   public void a(String v) {
      this.a = v == null ? "" : v.trim();
   }

   public String a() {
      return SignedLicenseToken.c(this.a);
   }

   public String b() {
      return SignedLicenseToken.d(this.a);
   }
}
