package com.workday.bert;

import java.nio.ByteBuffer;

public class BertDecimal implements BertElement {

	public int offset;
	public long value;

	public BertDecimal() {
	}

	public BertDecimal(int offset, long value) {
		this.offset = offset;
		this.value = value;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	public void setValue(long value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public BertList getAsBertList() {
		throw new IllegalStateException("This is not a bert list");
	}

	@Override
	public BertObj getAsBertObj() {
		return new BertObj(0, ByteBuffer.allocate(Long.SIZE / Byte.SIZE)
				.putLong(value).array());
	}

	@Override
	public BertDecimal getAsBertDecimal() {
		return this;
	}

}
