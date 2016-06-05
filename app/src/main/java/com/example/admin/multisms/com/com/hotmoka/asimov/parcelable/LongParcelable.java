package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a long value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class LongParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final long value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public LongParcelable(long value) {
		this.value = value;
	}

	private LongParcelable(Parcel in) {
	    value = in.readLong();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public long getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(value);
    }

    public static final Creator<LongParcelable> CREATOR = new Creator<LongParcelable>() {

    	@Override
    	public LongParcelable createFromParcel(Parcel in) {
            return new LongParcelable(in);
        }

    	@Override
        public LongParcelable[] newArray(int size) {
            return new LongParcelable[size];
        }
    };
}