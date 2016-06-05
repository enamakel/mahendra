package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of an array of bytes.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ArrayParcelableOfInteger implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final byte[] value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ArrayParcelableOfInteger(byte[] value) {
		this.value = value;
	}

	private ArrayParcelableOfInteger(Parcel in) {
		this.value = in.createByteArray();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public byte[] getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		out.writeByteArray(value);
    }

    public static final Creator<ArrayParcelableOfInteger> CREATOR = new Creator<ArrayParcelableOfInteger>() {

		@Override
    	public ArrayParcelableOfInteger createFromParcel(Parcel in) {
            return new ArrayParcelableOfInteger(in);
        }

    	@Override
        public ArrayParcelableOfInteger[] newArray(int size) {
            return new ArrayParcelableOfInteger[size];
        }
    };
}