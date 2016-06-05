package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of an array of characters.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ArrayParcelableOfCharacter implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final char[] value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ArrayParcelableOfCharacter(char[] value) {
		this.value = value;
	}

	private ArrayParcelableOfCharacter(Parcel in) {
		this.value = in.createCharArray();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public char[] getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		out.writeCharArray(value);
    }

    public static final Creator<ArrayParcelableOfCharacter> CREATOR = new Creator<ArrayParcelableOfCharacter>() {

		@Override
    	public ArrayParcelableOfCharacter createFromParcel(Parcel in) {
            return new ArrayParcelableOfCharacter(in);
        }

    	@Override
        public ArrayParcelableOfCharacter[] newArray(int size) {
            return new ArrayParcelableOfCharacter[size];
        }
    };
}