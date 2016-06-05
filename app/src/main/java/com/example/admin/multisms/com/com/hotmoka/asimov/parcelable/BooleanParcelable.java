package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a boolean value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class BooleanParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final boolean value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public BooleanParcelable(boolean value) {
		this.value = value;
	}

	private BooleanParcelable(Parcel in) {
	    value = in.readInt() == 0 ? false : true;
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public boolean getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(value ? 1 : 0);
    }

    public static final Creator<BooleanParcelable> CREATOR = new Creator<BooleanParcelable>() {

    	@Override
    	public BooleanParcelable createFromParcel(Parcel in) {
            return new BooleanParcelable(in);
        }

    	@Override
        public BooleanParcelable[] newArray(int size) {
            return new BooleanParcelable[size];
        }
    };
}