package net.webby.protostuff.runtime;

import org.junit.Assert;
import org.junit.Test;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * 
 * @author Alex Shvid
 *
 */

public class UUIDTest {

	private Schema<UUIDClass> uuidSchema = RuntimeSchema.getSchema(UUIDClass.class);
	private Schema<UUIDObjectClass> uuidObjectSchema = RuntimeSchema.getSchema(UUIDObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);
	
	@Test
	public void testUUID() throws Exception {
		
		UUIDClass ins = new UUIDClass();
		ins.uuidValue = java.util.UUID.randomUUID();
		
		byte[] blob = ProtobufIOUtil.toByteArray(ins, uuidSchema, buffer);
		
		UUIDObjectClass message = uuidObjectSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, uuidObjectSchema);

		Assert.assertEquals(ins.uuidValue.getLeastSignificantBits(), message.uuidValue.leastSigBits);
		Assert.assertEquals(ins.uuidValue.getMostSignificantBits(), message.uuidValue.mostSigBits);
	}
}
