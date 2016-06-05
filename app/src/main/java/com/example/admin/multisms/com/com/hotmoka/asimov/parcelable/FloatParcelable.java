package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a float value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class FloatParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final float value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public FloatParcelable(float value) {
		this.value = value;
	}

	private FloatParcelable(Parcel in) {
	    value = in.readFloat();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public float getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeFloat(value);
    }

    public static final Creator<FloatParcelable> CREATOR = new Creator<FloatParcelable>() {

    	@Override
    	public FloatParcelable createFromParcel(Parcel in) {
            return new FloatParcelable(in);
        }

    	@Override
        public FloatParcelable[] newArray(int size) {
            return new FloatParcelable[size];
        }
    };
}