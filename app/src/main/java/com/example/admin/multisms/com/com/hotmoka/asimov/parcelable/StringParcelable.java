package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a string value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class StringParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final String value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public StringParcelable(String value) {
		this.value = value;
	}

	private StringParcelable(Parcel in) {
	    value = in.readString();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public String getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(value);
    }

    public static final Creator<StringParcelable> CREATOR = new Creator<StringParcelable>() {

    	@Override
    	public StringParcelable createFromParcel(Parcel in) {
            return new StringParcelable(in);
        }

    	@Override
        public StringParcelable[] newArray(int size) {
            return new StringParcelable[size];
        }
    };
}