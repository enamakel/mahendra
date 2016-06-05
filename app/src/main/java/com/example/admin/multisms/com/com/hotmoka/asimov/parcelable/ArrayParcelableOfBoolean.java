package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of an array of booleans.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ArrayParcelableOfBoolean implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final boolean[] value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ArrayParcelableOfBoolean(boolean[] value) {
		this.value = value;
	}

	private ArrayParcelableOfBoolean(Parcel in) {
		this.value = in.createBooleanArray();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public boolean[] getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		out.writeBooleanArray(value);
    }

    public static final Creator<ArrayParcelableOfBoolean> CREATOR = new Creator<ArrayParcelableOfBoolean>() {

		@Override
    	public ArrayParcelableOfBoolean createFromParcel(Parcel in) {
            return new ArrayParcelableOfBoolean(in);
        }

    	@Override
        public ArrayParcelableOfBoolean[] newArray(int size) {
            return new ArrayParcelableOfBoolean[size];
        }
    };
}