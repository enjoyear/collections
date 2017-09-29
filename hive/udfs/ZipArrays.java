package hive.udfs;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ConstantObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;


@Description(name = "zip_arrays", value = "_FUNC_(name1, col1, name2, col2, ...) - Creates an array of struct by zipping all array columns")
public class ZipArrays extends GenericUDF {
  private static final Log log = LogFactory.getLog(ZipArrays.class.getName());

  @Override
  public ObjectInspector initialize(ObjectInspector[] arguments)
      throws UDFArgumentException {
    int numFields = arguments.length;
    if (numFields % 2 == 1) {
      throw new UDFArgumentLengthException("ZipArrays expects an even number of arguments.");
    }
    int structLength = numFields / 2;

    ArrayList<String> fieldNames = new ArrayList<>(structLength);
    ArrayList<ObjectInspector> fieldValues = new ArrayList<>(structLength);

    for (int i = 0; i < numFields; ++i) {
      //Even arg is the name
      if (i % 2 == 0) {
        if (!(arguments[i] instanceof ConstantObjectInspector)) {
          throw new UDFArgumentTypeException(i,
              "Even arguments of ZipArrays must be a constant string as a field name in struct: " + arguments[i]);
        }
        ConstantObjectInspector constantOI = (ConstantObjectInspector) arguments[i];
        fieldNames.add(constantOI.getWritableConstantValue().toString());
      } else /*Odd arg is the array*/ {
        if (!(arguments[i] instanceof ListObjectInspector)) {
          throw new UDFArgumentTypeException(i,
              "Odd arguments of ZipArrays must be an array as values in struct: " + arguments[i]);
        }
        ListObjectInspector listOI = (ListObjectInspector) arguments[i];
        fieldValues.add(listOI.getListElementObjectInspector());
      }
    }
    StructObjectInspector soi = ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldValues);
    return ObjectInspectorFactory.getStandardListObjectInspector(soi);
  }

  @Override
  public Object evaluate(DeferredObject[] arguments)
      throws HiveException {
    List<Object[]> zippedArray = new ArrayList<>();
    if (arguments.length == 0) {
      return zippedArray;
    }
    int min = Integer.MAX_VALUE;
    for (int arg = 1; arg < arguments.length; arg += 2) {
      min = Math.min(min, ((List) arguments[arg].get()).size());
    }
    for (int r = 0; r < min; ++r) {
      Object[] struct = new Object[arguments.length / 2];
      for (int i = 0; i < arguments.length / 2; ++i) {
        struct[i] = ((List) arguments[2 * i + 1].get()).get(r);
      }
      zippedArray.add(struct);
    }
    return zippedArray;
  }

  @Override
  public String getDisplayString(String[] children) {
    return getStandardDisplayString("zip_arrays", children, ",");
  }
}
