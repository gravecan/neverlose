package a;

public class BooleanSetting extends Setting {
   public boolean a;

   public BooleanSetting(String name, boolean def) {
      super(name);
      this.a = def;
   }

   public void a() {
      this.a = !this.a;
   }
}
