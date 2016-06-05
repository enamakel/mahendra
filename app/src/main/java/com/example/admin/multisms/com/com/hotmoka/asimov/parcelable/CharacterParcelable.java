package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable wrapper of a character value.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class CharacterParcelable implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final char value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public CharacterParcelable(char value) {
		this.value = value;
	}

	private CharacterParcelable(Parcel in) {
	    value = (char) in.readInt();
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public char getValue() {
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

    public static final Creator<CharacterParcelable> CREATOR = new Creator<CharacterParcelable>() {

    	@Override
    	public CharacterParcelable createFromParcel(Parcel in) {
            return new CharacterParcelable(in);
        }

    	@Override
        public CharacterParcelable[] newArray(int size) {
            return new CharacterParcelable[size];
        }
    };
}