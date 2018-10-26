package net.webby.protostuff.runtime;

import java.util.Map;

import io.protostuff.Tag;

/**
 *
 * @author Alex Shvid
 *
 */


public class MapClass {

	@Tag(1)
	protected Map<String, String> stringToString;

	@Tag(2)
	protected Map<String, MapValueObject> stringToObject;

	@Tag(3)
	protected Map<MapKeyObject, MapValueObject> objectToObject;

}
