package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of an array of float.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ArrayParcelableOfFloat implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final float[] value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ArrayParcelableOfFloat(float[] value) {
		this.value = value;
	}

	private ArrayParcelableOfFloat(Parcel in) {
		this.value = in.createFloatArray();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public float[] getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		out.writeFloatArray(value);
    }

    public static final Creator<ArrayParcelableOfFloat> CREATOR = new Creator<ArrayParcelableOfFloat>() {

		@Override
    	public ArrayParcelableOfFloat createFromParcel(Parcel in) {
            return new ArrayParcelableOfFloat(in);
        }

    	@Override
        public ArrayParcelableOfFloat[] newArray(int size) {
            return new ArrayParcelableOfFloat[size];
        }
    };
}