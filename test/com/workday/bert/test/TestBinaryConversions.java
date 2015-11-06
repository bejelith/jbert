package com.workday.bert.test;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.workday.bert.ByteArrayUtils;

public class TestBinaryConversions {
	

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	static byte[][] a = new byte[4][]; 
	static {
		a[0] = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};		 
		a[1] = new byte[]{(byte) 0, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
		a[2] = new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF};
		a[3] = new byte[]{(byte) 0x1, (byte) 0x1, (byte) 0x1, (byte) 0x1};
	}
	
	@Test
	public void TestByteArraytoInt() {
		 assertEquals(-1, ByteArrayUtils.readInt(a[0], 0, 4));
		 assertEquals(-1, ByteArrayUtils.readInt(a[1], 1, 4));
		 assertEquals(255, ByteArrayUtils.readInt(a[2], 0, 4));
		 assertEquals(16843009, ByteArrayUtils.readInt(a[3], 0, 4));
		 assertEquals(0xFFFFFFFFL, ByteArrayUtils.readLong(a[0], 0, 4));
	}
	
	@Test
	public void TestReadIntIndexOutOfArray(){
		thrown.expect(ArrayIndexOutOfBoundsException.class);
		ByteArrayUtils.readInt(a[0], 0, 1);
	}
	
	@Test
	public void TestReadLongIndexOufOfArray(){
		thrown.expect(ArrayIndexOutOfBoundsException.class);
		ByteArrayUtils.readLong(a[0], 0, 6);
	}
}
