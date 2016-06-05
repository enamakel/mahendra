package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a double value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class DoubleParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final double value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public DoubleParcelable(double value) {
		this.value = value;
	}

	private DoubleParcelable(Parcel in) {
	    value = in.readDouble();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public double getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeDouble(value);
    }

    public static final Creator<DoubleParcelable> CREATOR = new Creator<DoubleParcelable>() {

    	@Override
    	public DoubleParcelable createFromParcel(Parcel in) {
            return new DoubleParcelable(in);
        }

    	@Override
        public DoubleParcelable[] newArray(int size) {
            return new DoubleParcelable[size];
        }
    };
}