package com.workday.bert;

public interface BertElement {

	public void setOffset(int offset);

	public int getOffset();

	public BertList getAsBertList();

	public BertObj getAsBertObj();

	public BertDecimal getAsBertDecimal();
}
