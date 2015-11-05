package com.workday.bert;

public class BertObj implements BertElement {

	private int offset = 0;
	private byte[] value = null;

	public BertObj() {
	}

	public BertObj(int offset, byte[] val) {
		this.offset = offset;
		this.value = val;
	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	public byte[] getValue() {
		return value;
	}

	@Override
	public BertList getAsBertList() {
		throw new IllegalStateException("This is not a bert list");
	}

	@Override
	public BertObj getAsBertObj() {
		return this;
	}

	@Override
	public BertDecimal getAsBertDecimal() {
		throw new IllegalStateException("This is not a bert decimal");
		// TODO i can convert it if the byte[] is not longer than 'long' type
	}

	public byte[] getAsByteArray() {
		return value;
	}

}
