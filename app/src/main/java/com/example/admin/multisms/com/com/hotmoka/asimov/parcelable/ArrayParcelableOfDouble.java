package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of an array of double.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ArrayParcelableOfDouble implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final double[] value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ArrayParcelableOfDouble(double[] value) {
		this.value = value;
	}

	private ArrayParcelableOfDouble(Parcel in) {
		this.value = in.createDoubleArray();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public double[] getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		out.writeDoubleArray(value);
    }

    public static final Creator<ArrayParcelableOfDouble> CREATOR = new Creator<ArrayParcelableOfDouble>() {

		@Override
    	public ArrayParcelableOfDouble createFromParcel(Parcel in) {
            return new ArrayParcelableOfDouble(in);
        }

    	@Override
        public ArrayParcelableOfDouble[] newArray(int size) {
            return new ArrayParcelableOfDouble[size];
        }
    };
}