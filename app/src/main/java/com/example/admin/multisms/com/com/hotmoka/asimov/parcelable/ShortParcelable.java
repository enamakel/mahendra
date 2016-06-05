package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a short value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ShortParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final short value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ShortParcelable(short value) {
		this.value = value;
	}

	private ShortParcelable(Parcel in) {
	    value = (short) in.readInt();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public short getValue() {
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

    public static final Creator<ShortParcelable> CREATOR = new Creator<ShortParcelable>() {

    	@Override
    	public ShortParcelable createFromParcel(Parcel in) {
            return new ShortParcelable(in);
        }

    	@Override
        public ShortParcelable[] newArray(int size) {
            return new ShortParcelable[size];
        }
    };
}