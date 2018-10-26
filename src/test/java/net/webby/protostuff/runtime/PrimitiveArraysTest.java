package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.ByteArrayInput;
import io.protostuff.Input;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.parser.Field;
import io.protostuff.parser.Message;
import io.protostuff.parser.Proto;
import io.protostuff.parser.ProtoUtil;
import io.protostuff.parser.Field.Modifier;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */


public class PrimitiveArraysTest {

	private Schema<PrimitiveArraysClass> schema = RuntimeSchema.getSchema(PrimitiveArraysClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void test() throws Exception {
		
		String content = Generators.newProtoGenerator(schema).generate();
		
		System.out.println(content);
		
		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(4, proto.getMessages().size());
		
		Message msg = proto.getMessage("PrimitiveArraysClass");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.OPTIONAL, field.getModifier());
		}
		
		Assert.assertEquals(8,  msg.getFieldCount());
		Assert.assertEquals("ArrayObject", msg.getField("booleanValue").getJavaType());
		Assert.assertEquals("ArrayObject", msg.getField("charValue").getJavaType());
		Assert.assertEquals("ArrayObject", msg.getField("shortValue").getJavaType());
		Assert.assertEquals("ArrayObject", msg.getField("intValue").getJavaType());
		Assert.assertEquals("ArrayObject", msg.getField("longValue").getJavaType());
		Assert.assertEquals("ArrayObject", msg.getField("floatValue").getJavaType());
		Assert.assertEquals("ArrayObject", msg.getField("doubleValue").getJavaType());
		
	}
	
	@Test
	public void testIO() throws Exception {
		
		PrimitiveArraysClass instance = new PrimitiveArraysClass();

		byte[] blob = ProtobufIOUtil.toByteArray(instance, schema, buffer);
		
		Input in = new ByteArrayInput(blob, false);

		int fieldNum = in.readFieldNumber(null); // PrimitiveArraysClass.booleanValue
		Assert.assertEquals(0, fieldNum);
		
		ProtobufIOUtil.mergeFrom(blob, instance, schema);
		
	}
}
