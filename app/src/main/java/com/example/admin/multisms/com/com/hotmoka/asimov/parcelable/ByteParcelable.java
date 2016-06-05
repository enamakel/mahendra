package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a byte value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ByteParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final byte value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ByteParcelable(byte value) {
		this.value = value;
	}

	private ByteParcelable(Parcel in) {
	    value = in.readByte();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public byte getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeByte(value);
    }

    public static final Creator<ByteParcelable> CREATOR = new Creator<ByteParcelable>() {

    	@Override
    	public ByteParcelable createFromParcel(Parcel in) {
            return new ByteParcelable(in);
        }

    	@Override
        public ByteParcelable[] newArray(int size) {
            return new ByteParcelable[size];
        }
    };
}