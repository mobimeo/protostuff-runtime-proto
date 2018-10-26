package net.webby.protostuff.runtime;

import io.protostuff.Tag;

import java.util.Map;

/**
 *
 * @author Alex Shvid
 *
 */


public class MapObjectClass {

	@Tag(1)
	protected Map<String, String> stringToString;

	@Tag(2)
	protected Map<String, MapValueObject> stringToObject;

	@Tag(3)
	protected Map<MapKeyObject, MapValueObject> objectToObject;

}
