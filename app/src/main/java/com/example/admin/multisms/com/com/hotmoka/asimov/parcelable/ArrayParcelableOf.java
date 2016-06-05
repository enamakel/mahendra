package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * A parcelable wrapper of an array of parcelable objects.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class ArrayParcelableOf<P extends Parcelable> implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final P[] value;

	private final String type;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public ArrayParcelableOf(P[] value) {
		this.value = value;
		this.type = value.getClass().getComponentType().getName();
	}

	@SuppressWarnings("unchecked")
	private ArrayParcelableOf(Parcel in) {
		Object[] elements = in.readArray(getClass().getClassLoader());
		this.type = in.readString();
		P[] array = null;

		try {
			array = (P[]) java.lang.reflect.Array.newInstance(Class.forName(type), elements.length);
		} catch (ClassNotFoundException e) {
			Log.d(getClass().getName(), e.toString());
		}

		System.arraycopy(elements, 0, array, 0, elements.length);

		this.value = array;
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public P[] getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		out.writeArray(value);
		out.writeString(type);
    }

    public static final Creator<ArrayParcelableOf<?>> CREATOR = new Creator<ArrayParcelableOf<?>>() {

    	@SuppressWarnings("rawtypes")
		@Override
    	public ArrayParcelableOf<?> createFromParcel(Parcel in) {
            return new ArrayParcelableOf(in);
        }

    	@Override
        public ArrayParcelableOf<?>[] newArray(int size) {
            return new ArrayParcelableOf[size];
        }
    };
}