package hive.udfs;

public class Sanitize extends UDF {
  public String evaluate(String string) {
    return cleanse(string);
  }

  public static String cleanse(String in) {
    String regexSpace = "[\r\n\t\u000f]";
    String regexDelete = "[\ufffd\u001a\u0000\\p{Cs}\\p{Cn}\u2028]";

    if (in == null)
      return in;

    in = in.replaceAll(regexSpace, " ");
    in = in.replaceAll(regexDelete, "");

    return in;
  }
}