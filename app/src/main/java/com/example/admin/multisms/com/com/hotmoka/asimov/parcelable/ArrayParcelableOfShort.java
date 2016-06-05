package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of an array of shorts.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ArrayParcelableOfShort implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final short[] value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ArrayParcelableOfShort(short[] value) {
		this.value = value;
	}

	private ArrayParcelableOfShort(Parcel in) {
		int[] array = in.createIntArray();
		this.value = new short[array.length];

		for (int pos = 0; pos < array.length; pos++)
			this.value[pos] = (short) array[pos];
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public short[] getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		int[] array = new int[value.length];
		System.arraycopy(value, 0, array, 0, array.length);

		out.writeIntArray(array);
    }

    public static final Creator<ArrayParcelableOfShort> CREATOR = new Creator<ArrayParcelableOfShort>() {

		@Override
    	public ArrayParcelableOfShort createFromParcel(Parcel in) {
            return new ArrayParcelableOfShort(in);
        }

    	@Override
        public ArrayParcelableOfShort[] newArray(int size) {
            return new ArrayParcelableOfShort[size];
        }
    };
}