package a;

public class SliderSetting extends Setting {
   public final double a;
   public final double b;
   public final double c;
   public final String d;
   public double e;

   public SliderSetting(String name, double min, double max, double def, double step, String unit) {
      super(name);
      this.a = min;
      this.b = max;
      this.c = step;
      this.d = unit;
      this.a(def);
   }

   public void a(double v) {
      v = (double)Math.round(v / this.c) * this.c;
      this.e = Math.max(this.a, Math.min(this.b, v));
   }

   public double a() {
      return (this.e - this.a) / (this.b - this.a);
   }

   public void b(double f) {
      this.a(this.a + Math.max((double)0.0F, Math.min((double)1.0F, f)) * (this.b - this.a));
   }

   public String b() {
      String var1 = this.c >= (double)1.0F ? String.valueOf((int)Math.round(this.e)) : String.format("%.2f", this.e);
      return var1 + this.d;
   }
}
