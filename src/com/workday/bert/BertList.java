package com.workday.bert;

import java.util.ArrayList;
import java.util.List;

public class BertList implements BertElement {

	private int offset = 0;
	private List<BertElement> values = new ArrayList<BertElement>();

	public BertList(int offset, List<BertElement> values) {
		this.offset = offset;
		this.values = values;
	}

	public BertList() {

	}

	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	public BertElement get(int index) {
		return values.get(index);
	}

	@Override
	public BertList getAsBertList() {
		return this;
	}

	@Override
	public BertObj getAsBertObj() {
		throw new IllegalStateException("This is not a bert object");
	}

	@Override
	public BertDecimal getAsBertDecimal() {
		throw new IllegalStateException("This is not a bert decimal type");
	}

	public void setValue(BertElement value) {
		offset += value.getOffset();
		values.add(value);
	}

	public void add(BertElement e) {
		offset = e.getOffset();
		values.add(e);
	}

}
