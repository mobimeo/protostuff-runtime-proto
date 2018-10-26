package net.webby.protostuff.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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


public class MapObjectTest {

	private Schema<MapClass> mapSchema = RuntimeSchema.getSchema(MapClass.class);
	private Schema<MapObjectClass> mapObjectSchema = RuntimeSchema.getSchema(MapObjectClass.class);
	private LinkedBuffer buffer = LinkedBuffer.allocate(4096);

	@Test
	public void testPrint() throws Exception {

		Schema<MapClass> schema = RuntimeSchema.getSchema(MapClass.class);

		String content = Generators.newProtoGenerator(schema).generate();

		System.out.println(content);

	}

	@Test
	public void test() throws Exception {

		MapClass mapClass = new MapClass();

		MapKeyObject mapKeyObject = new MapKeyObject();
		mapKeyObject.setValue("value");
		MapValueObject mapValueObject = new MapValueObject();
		mapValueObject.setValue("value");

		mapClass.objectToObject = new HashMap<MapKeyObject, MapValueObject>();
		mapClass.objectToObject.put(mapKeyObject, mapValueObject);

		mapClass.stringToObject = new HashMap<String, MapValueObject>();
		mapClass.stringToObject.put("stringKey", mapValueObject);

		mapClass.stringToString = new HashMap<String, String>();
		mapClass.stringToString.put("stringKey", "stringValue");

		byte[] blob = ProtobufIOUtil.toByteArray(mapClass, mapSchema, buffer);

		MapClass message = mapSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, mapSchema);

		for (Map.Entry<String, MapValueObject> entry : message.stringToObject.entrySet()) {
			Assert.assertEquals("stringKey", entry.getKey());
			Assert.assertEquals("value", entry.getValue().getValue());
		}

		for (Map.Entry<String, String> entry : message.stringToString.entrySet()) {
			Assert.assertEquals("stringKey", entry.getKey());
			Assert.assertEquals("stringValue", entry.getValue());
		}

		for (Map.Entry<MapKeyObject, MapValueObject> entry : message.objectToObject.entrySet()) {
			Assert.assertEquals("value", entry.getKey().getValue());
			Assert.assertEquals("value", entry.getValue().getValue());
		}
	}

	@Test
	public void testHashMap() throws Exception {
		testImpl(new HashMap<String, String>());
	}

	@Test
	public void testTreeMap() throws Exception {
		testImpl(new TreeMap<String, String>());
	}

	private void testImpl(Map<String,String> mapImpl) throws Exception {

		MapClass ins = new MapClass();
		ins.stringToString = mapImpl;
		ins.stringToString.put("test1", "test1");
		ins.stringToString.put("test2", "test2");

		byte[] blob = ProtobufIOUtil.toByteArray(ins, mapSchema, buffer);

		MapClass message = mapSchema.newMessage();
		ProtobufIOUtil.mergeFrom(blob, message, mapSchema);

		Assert.assertEquals(HashMap.class, message.stringToString.getClass());

	}

}
