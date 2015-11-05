package com.workday.bert;

import java.util.Arrays;
import java.util.zip.DataFormatException;

import com.workday.bert.BertCode;
import com.workday.bert.BertDecimal;
import com.workday.bert.BertElement;
import com.workday.bert.BertList;
import com.workday.bert.BertObj;

public class Bert {

	public BertElement decode(byte[] data, int offset)
			throws DataFormatException {
		int i = 0;
		byte[] header = new byte[2];

		if (offset > data.length) {
			throw new IndexOutOfBoundsException("Offset " + offset
					+ " > than data lentgh " + data.length);
		}
		header[0] = data[offset];
		header[1] = data[offset + 1];
		if (BertCode.ERL_DIST_HEADER.equals(header)) {
			throw new DataFormatException(
					"Encountered ERL_DIST_HEADER, at the moment parsing of this structure is not implemented");
		}
		BertCode code = BertCode.get( ByteArrayUtils.readInt(data, offset++, 1));
		switch (code) {
		case ERL_VERSION:
			return decode(data, offset);
		case ERL_SMALL_ATOM:
			return decodeAtom(data, offset, 1);
		case ERL_ATOM:
			return decodeAtom(data, offset, 2);
		case ERL_BIN:
			return decodeBin(data, offset);
		case ERL_SMALL_BIGNUM:
			return decodeBigNum(data, offset, 1);
		case ERL_LARGE_BIGNUM:
			return decodeBigNum(data, offset, 4);
		case ERL_SMALL_INT:
			return decodeInt(data, offset, 1);
		case ERL_INT:
			return decodeInt(data, offset, 4);
		case ERL_SMALL_TUPLE:
			return decodeTuple(data, offset, 1);
		case ERL_LARGE_TUPLE:
			return decodeTuple(data, offset, 4);
		case ERL_LIST:
			return decodeList(data, offset);
		case ERL_NIL:
			return decodeNil(offset);// skip null list
		default:
			throw new DataFormatException(
					"Encountered an unrecognized BERT field " + i + " at "
							+ Integer.toHexString(offset));
		}
	}

	public BertObj decodeNil(int offset) {
		BertObj obj = new BertObj();
		obj.setOffset(offset);
		return obj; // nil already ++ ofted at type check phase
	}

	public BertDecimal decodeInt(byte[] data, int offset, int len) {
		BertDecimal obj = new BertDecimal();
		obj.setValue(ByteArrayUtils.readInt(data, offset, len));
		obj.setOffset(offset + len);
		return obj;
	}

	public BertDecimal decodeBigNum(byte[] data, int offset, int len) {
		BertDecimal obj = new BertDecimal();
		// int saved = offset;
		int size = ByteArrayUtils.readInt(data, (int) offset, len);
		offset = offset + len;
		obj.setValue(ByteArrayUtils.readLong(data, (int) offset, size));
		obj.setOffset(offset + size + 1);
		return obj;
	}

	public BertObj decodeBin(byte[] data, int offset) {
		BertObj obj = new BertObj();
		int size = ByteArrayUtils.readInt(data, (int) offset, 4);
		offset = offset + 4;
		obj.setValue(Arrays
				.copyOfRange(data, (int) offset, (int) offset + size));
		if (size > 0) {
			obj.setOffset(offset + size);
		} else {
			obj.setOffset(offset + 1);
		}
		return obj;
	}

	public BertObj decodeAtom(byte[] data, int offset, int len) {
		BertObj obj = new BertObj();
		int size = ByteArrayUtils.readInt(data, (int) offset, len);
		offset += len;
		obj.setValue(Arrays
				.copyOfRange(data, (int) offset, (int) offset + size));
		obj.setOffset(offset + size);
		return obj;
	}

	public BertList decodeTuple(byte[] data, int offset, int len)
			throws DataFormatException {
		BertList obj = new BertList();
		int elements = ByteArrayUtils.readInt(data, (int) offset, len);
		obj.setOffset(offset + len);
		for (int i = 0; i < elements; i++) {
			obj.add(decode(data, obj.getOffset()));
		}
		return obj;
	}

	public BertElement decodeList(byte[] data, int offset)
			throws DataFormatException {
		BertList obj = new BertList();
		int elements = ByteArrayUtils.readInt(data, offset, 4);
		obj.setOffset(offset + 4);

		for (int i = 0; i < elements; i++) {
			obj.add(decode(data, (int) obj.getOffset()));
		}
		return obj;
	}

}