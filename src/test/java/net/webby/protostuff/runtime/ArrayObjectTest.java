package net.webby.protostuff.runtime;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.tools.javac.util.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.Tag;
import io.protostuff.runtime.RuntimeSchema;

import javax.swing.*;

/**
 * 
 * @author Alex Shvid
 *
 */

public class ArrayObjectTest {

	private Schema<ArrayObject> arraySchema = RuntimeSchema.getSchema(ArrayObject.class);
	private Schema<ArrayObjectClass> arrayObjectSchema = RuntimeSchema.getSchema(ArrayObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void testPrint() throws Exception {
		
		Schema<ArrayObject> schema = RuntimeSchema.getSchema(ArrayObject.class);
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
	}
	
  /*
   * BOOL
   */
	
	public static class PrimitiveBooleanArrayObjectClass {
		
		@Tag(1)
		protected boolean[] value;
	}
	
	@Test
	public void testPrimitiveBoolean() throws Exception {
		testClass(PrimitiveBooleanArrayObjectClass.class, new boolean[] { false, true }, boolean.class, "booleanValue"); 
	}
	
	public static class BooleanArrayObjectClass {
		
		@Tag(1)
		protected Boolean[] value;
	}
	
	@Test
	public void testBoolean() throws Exception {
		testClass(BooleanArrayObjectClass.class, new Boolean[] { Boolean.FALSE, Boolean.TRUE }, Boolean.class, "booleanValue"); 
	}

	/*
	 * BYTE
	 */
	
	public static class ByteArrayObjectClass {
		
		@Tag(1)
		protected int[] value;
	}
	
	@Test
	public void testByte() throws Exception {
		testClass(ByteArrayObjectClass.class, new Byte[] { 0x12, 0x34 }, Byte.class, "byteValue"); 
	}
	
	/*
	 * CHAR
	 */
	
	public static class PrimitiveCharArrayObjectClass {
		
		@Tag(1)
		protected char[] value;
	}
	
	@Test
	public void testPrimitiveChar() throws Exception {
		testClass(PrimitiveCharArrayObjectClass.class, new char[] { 'a', 'b' }, char.class, "charValue"); 
	}
	
	public static class CharArrayObjectClass {
		
		@Tag(1)
		protected Character[] value;
	}
	
	@Test
	public void testChar() throws Exception {
		testClass(CharArrayObjectClass.class, new Character[] { 'a', 'b' }, Character.class, "charValue"); 
	}
	
	/*
	 * SHORT
	 */
	
	public static class PrimitiveShortArrayObjectClass {
		
		@Tag(1)
		protected short[] value;
	}
	
	@Test
	public void testPrimitiveShort() throws Exception {
		testClass(PrimitiveShortArrayObjectClass.class, new short[] { 555, 777 }, short.class, "shortValue"); 
	}
	
	public static class ShortArrayObjectClass {
		
		@Tag(1)
		protected Short[] value;
	}
	
	@Test
	public void testShort() throws Exception {
		testClass(ShortArrayObjectClass.class, new Short[] { 555, 777 }, Short.class, "shortValue"); 
	}
	
	/*
	 * INT
	 */
	
	public static class PrimitiveIntArrayObjectClass {
		
		@Tag(1)
		protected int[] value;
	}
	
	@Test
	public void testPrimitiveInt() throws Exception {
		testClass(PrimitiveIntArrayObjectClass.class, new int[] { 555, 777 }, int.class, "intValue"); 
	}
	
	public static class IntArrayObjectClass {
		
		@Tag(1)
		protected Integer[] value;
	}
	
	@Test
	public void testInt() throws Exception {
		testClass(IntArrayObjectClass.class, new Integer[] { 555, 777 }, Integer.class, "intValue"); 
	}
	
	/*
	 * LONG
	 */
	
	public static class PrimitiveLongArrayObjectClass {
		
		@Tag(1)
		protected long[] value;
	}
	
	@Test
	public void testPrimitiveLong() throws Exception {
		testClass(PrimitiveLongArrayObjectClass.class, new long[] { 555l, 777l }, long.class, "longValue"); 
	}
	
	public static class LongArrayObjectClass {
		
		@Tag(1)
		protected Long[] value;
	}
	
	@Test
	public void testLong() throws Exception {
		testClass(LongArrayObjectClass.class, new Long[] { 555l, 777l }, Long.class, "longValue"); 
	}
	
	/*
	 * FLOAT
	 */
	
	public static class PrimitiveFloatArrayObjectClass {
		
		@Tag(1)
		protected float[] value;
	}
	
	@Test
	public void testPrimitiveFloat() throws Exception {
		testClass(PrimitiveFloatArrayObjectClass.class, new float[] { 0.55f, 0.77f }, float.class, "floatValue"); 
	}
	
	public static class FloatArrayObjectClass {
		
		@Tag(1)
		protected Float[] value;
	}
	
	@Test
	public void testFloat() throws Exception {
		testClass(FloatArrayObjectClass.class, new Float[] { 0.55f, 0.77f }, Float.class, "floatValue"); 
	}	
	
	/*
	 * DOUBLE
	 */
	
	public static class PrimitiveDoubleArrayObjectClass {
		
		@Tag(1)
		protected double[] value;
	}
	
	@Test
	public void testPrimitiveDouble() throws Exception {
		testClass(PrimitiveDoubleArrayObjectClass.class, new double[] { 0.55, 0.77 }, double.class, "doubleValue"); 
	}
	
	public static class DoubleArrayObjectClass {
		
		@Tag(1)
		protected Double[] value;
	}
	
	@Test
	public void testDouble() throws Exception {
		testClass(DoubleArrayObjectClass.class, new Double[] { 0.55, 0.77 }, Double.class, "doubleValue"); 
	}	
	
	/*
	 * IMPL
	 */
	
	private <T> void testClass(Class<T> schemaCls, Object expectedArray, Class<?> itemClass, String fieldName) throws Exception {

		int len = Array.getLength(expectedArray);
		Schema<T> schema = RuntimeSchema.getSchema(schemaCls);

		ArrayObject ins = new ArrayObject();
		List<?> expectedList = convertObjectToList(expectedArray);
		List<DynamicObject> valueList = new ArrayList<DynamicObject>();
		for (Object obj: expectedList) {
			DynamicObject dynamicObject = new DynamicObject();
			assignValue(dynamicObject, obj, itemClass);
			valueList.add(dynamicObject);
		}
		ins.value = valueList;
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, arraySchema, buffer);

		ArrayObjectClass message = arrayObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, arrayObjectSchema);

		List<DynamicObject> list = message.value.value;
		Assert.assertEquals(len, list.size());
	}

	private List<?> convertObjectToList(Object expectedArray) {
		if (expectedArray instanceof int[]) {
			int[] array = (int[]) expectedArray;
			List<Object> list = new ArrayList();
			for (int i : array) {
				list.add(i);
			}
			return list;
		}
		if (expectedArray instanceof short[]) {
			short[] array = ((short[]) expectedArray);
			List<Object> list = new ArrayList();
			for (short i : array) {
				list.add(i);
			}
			return list;
		}
		if (expectedArray instanceof char[]) {
			char[] array = ((char[]) expectedArray);
			List<Object> list = new ArrayList();
			for (char i : array) {
				list.add(i);
			}
			return list;
		}
		if (expectedArray instanceof long[]) {
			long[] array = ((long[]) expectedArray);
			List<Object> list = new ArrayList();
			for (long i : array) {
				list.add(i);
			}
			return list;
		}
		if (expectedArray instanceof double[]) {
			double[] array = ((double[]) expectedArray);
			List<Object> list = new ArrayList();
			for (double i : array) {
				list.add(i);
			}
			return list;
		}
		if (expectedArray instanceof boolean[]) {
			boolean[] array = ((boolean[]) expectedArray);
			List<Object> list = new ArrayList();
			for (boolean i : array) {
				list.add(i);
			}
			return list;
		}
		if (expectedArray instanceof float[]) {
			float[] array = ((float[]) expectedArray);
			List<Object> list = new ArrayList();
			for (float i : array) {
				list.add(i);
			}
			return list;
		}
		if (expectedArray instanceof byte[]) {
			byte[] array = ((byte[]) expectedArray);
			List<Object> list = new ArrayList();
			for (byte i : array) {
				list.add(i);
			}
			return list;
		}
		Object[] objArray = ((Object[]) expectedArray);
		return Arrays.asList(objArray);
	}

	private void assignValue(DynamicObject dynamicObject, Object obj, Class<?> itemClass) {
		if (itemClass.getSimpleName().equals("Double") || (itemClass.getSimpleName().equals("double"))) {
			dynamicObject.doubleValue = (Double) obj;
		}
		if (itemClass.getSimpleName().equals("Float") || (itemClass.getSimpleName().equals("float"))) {
			dynamicObject.floatValue = (Float) obj;
		}
		if (itemClass.getSimpleName().equals("Integer") || (itemClass.getSimpleName().equals("int"))) {
			dynamicObject.intValue = (Integer) obj;
		}
		if (itemClass.getSimpleName().equals("Character") || (itemClass.getSimpleName().equals("char"))) {
			dynamicObject.charValue = (Character) obj;
		}
		if (itemClass.getSimpleName().equals("Long") || (itemClass.getSimpleName().equals("long"))) {
			dynamicObject.longValue = (Long) obj;
		}
		if (itemClass.getSimpleName().equals("Short") || (itemClass.getSimpleName().equals("short"))) {
			dynamicObject.shortValue = (Short) obj;
		}
	}
}
