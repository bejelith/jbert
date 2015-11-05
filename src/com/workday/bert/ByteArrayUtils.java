package com.workday.bert;

public class ByteArrayUtils {

	public static long readLong(byte[] a, long offset, int lenght) {
		long ret = 0;
		for (int i = lenght - 1; i > 0; i--) {
			ret |= ((a[(int) offset++] & 0xFF) << 8 * i) & 0xFFFFFFFFL;
		}
		ret |= a[(int) offset++] & 0xFF;
		return ret;
	}

	public static int readInt(byte[] a, long offset, int lenght) {
		int ret = 0;
		for (int i = lenght - 1; i > 0; i--) {
			ret |= (a[(int) offset++] & 0xFF) << 8 * i;
		}
		ret |= (int) a[(int) offset++] & 0xFF;
		return ret;
	}
}
