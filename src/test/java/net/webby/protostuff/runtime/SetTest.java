package net.webby.protostuff.runtime;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.ByteArrayInput;
import io.protostuff.Input;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.parser.Field;
import io.protostuff.parser.Field.Modifier;
import io.protostuff.parser.Message;
import io.protostuff.parser.MessageField;
import io.protostuff.parser.Proto;
import io.protostuff.parser.ProtoUtil;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */

public class SetTest {

	private Schema<SetClass> collectionSchema = RuntimeSchema.getSchema(SetClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void testGenerator() throws Exception {
		
		String content = Generators.newProtoGenerator(collectionSchema).generate();
		
		//System.out.println(content);

		Proto proto = new Proto(new File("test.proto"));
		ProtoUtil.loadFrom(new ByteArrayInputStream(content.getBytes()), proto);
		 
		String packageName = this.getClass().getPackage().getName();
		
		Assert.assertEquals(proto.getPackageName(), packageName.replace('.', '_'));
		Assert.assertEquals(proto.getJavaPackageName(), packageName);
		Assert.assertEquals(0, proto.getEnumGroups().size());
		Assert.assertEquals(1, proto.getMessages().size());
		
		Message msg = proto.getMessage("SetClass");
		Assert.assertNotNull(msg);
		
		for (Field<?> field : msg.getFieldMap().values()) {
			Assert.assertEquals(Modifier.REPEATED, field.getModifier());
		}
		
		Assert.assertEquals(14,  msg.getFieldCount());
		Assert.assertTrue(msg.getField("booleanValue") instanceof Field.Bool);
		Assert.assertTrue(msg.getField("byteValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("charValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("shortValue") instanceof Field.UInt32);
		Assert.assertTrue(msg.getField("intValue") instanceof Field.Int32);
		Assert.assertTrue(msg.getField("longValue") instanceof Field.Int64);
		Assert.assertTrue(msg.getField("floatValue") instanceof Field.Float);
		Assert.assertTrue(msg.getField("doubleValue") instanceof Field.Double);
		Assert.assertEquals("DynamicObject", msg.getField("objValue").getJavaType());
		Assert.assertTrue(msg.getField("stringValue") instanceof Field.String);
		Assert.assertTrue(msg.getField("dateValue") instanceof Field.Fixed64);
		Assert.assertTrue(msg.getField("biValue") instanceof Field.Bytes);
		Assert.assertTrue(msg.getField("bdValue") instanceof Field.String);
		Assert.assertTrue(msg.getField("uuidValue") instanceof MessageField);
	}
	
	@Test
	public void testIO() throws Exception {
		
		SetClass ins = new SetClass();
		ins.stringValue = new LinkedHashSet<String>();
		ins.stringValue.add("test1");
		ins.stringValue.add("test2");
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, collectionSchema, buffer);
		
		Input in = new ByteArrayInput(blob, false);

		Assert.assertEquals(10, in.readFieldNumber(null)); // ListClass.stringValue
	  Assert.assertEquals("test1", in.readString());
	  
		Assert.assertEquals(10, in.readFieldNumber(null)); // ListClass.stringValue
	  Assert.assertEquals("test2", in.readString());
	  
	  Assert.assertEquals(0, in.readFieldNumber(null)); // ListClass.END
		
	}
	
	@Test
	public void testHashSet() throws Exception {
		testImpl(new HashSet<String>());
	}
	
	@Test
	public void testLinkedHashSet() throws Exception {
		testImpl(new LinkedHashSet<String>());
	}
	
	private void testImpl(Set<String> setImpl) {
		
		SetClass ins = new SetClass();
		ins.stringValue = setImpl;
		ins.stringValue.add("test1");
		ins.stringValue.add("test2");
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, collectionSchema, buffer);
		
		SetClass message = collectionSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, collectionSchema);
		
		Assert.assertEquals(HashSet.class, message.stringValue.getClass());
	}
	
}
