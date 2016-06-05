package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of an integer value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class IntegerParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final int value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public IntegerParcelable(int value) {
		this.value = value;
	}

	private IntegerParcelable(Parcel in) {
	    value = in.readInt();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public int getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(value);
    }

    public static final Creator<IntegerParcelable> CREATOR = new Creator<IntegerParcelable>() {

    	@Override
    	public IntegerParcelable createFromParcel(Parcel in) {
            return new IntegerParcelable(in);
        }

    	@Override
        public IntegerParcelable[] newArray(int size) {
            return new IntegerParcelable[size];
        }
    };
}