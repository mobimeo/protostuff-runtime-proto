package net.webby.protostuff.runtime;

import io.protostuff.Tag;

/**
 * 
 * @author Alex Shvid
 *
 */


public class PrimitiveArraysClass {

	@Tag(1)
	protected ArrayObject booleanValue;

	@Tag(2)
	protected ArrayObject byteValue;

	@Tag(3)
	protected ArrayObject charValue;
	
	@Tag(4)
	protected ArrayObject shortValue;

	@Tag(5)
	protected ArrayObject intValue;

	@Tag(6)
	protected ArrayObject longValue;

	@Tag(7)
	protected ArrayObject floatValue;

	@Tag(8)
	protected ArrayObject doubleValue;
	
}
