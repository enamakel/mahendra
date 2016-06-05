package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import java.util.Collection;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * A parcelable pair. The components of the pair must be types
 * that can be written into a parcel.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class PairParcelable<T1, T2> implements Parcelable {

	private final static byte COLLECTION = 0;
	private final static byte NON_COLLECTION = 1;

	/**
	 * The first component of the pair.
	 */

	private final T1 first;

	/**
	 * The second component of the pair.
	 */

	private final T2 second;

	/**
	 * Builds a parcelable pair.
	 *
	 * @param first the first component of the pair
	 * @param second the first component of the pair
	 */

	public PairParcelable(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	@SuppressWarnings("unchecked")
	private PairParcelable(Parcel in) {
		this.first = (T1) readFromParcel(in);
		this.second = (T2) readFromParcel(in);
	}

	@SuppressWarnings("unchecked")
	private Object readFromParcel(Parcel in) {
		switch (in.readByte()) {
		case COLLECTION:
			String type = in.readString();
			Collection<Object> collection = null;

			try {
				collection = (Collection<Object>) Class.forName(type).newInstance();
				Object[] array = in.readArray(getClass().getClassLoader());
				for (Object element: array)
					collection.add(element);
			} catch (InstantiationException e) {
				Log.d(getClass().getName(), e.toString());
			} catch (IllegalAccessException e) {
				Log.d(getClass().getName(), e.toString());
			} catch (ClassNotFoundException e) {
				Log.d(getClass().getName(), e.toString());
			}

			return collection;
		default:
			return in.readValue(getClass().getClassLoader());
		}
	}

	/**
	 * Yields the first component of the pair.
	 * 
	 * @return the first component of the pair
	 */

	public T1 getFirst() {
		return first;
	}

	/**
	 * Yields the second component of the pair.
	 * 
	 * @return the second component of the pair
	 */

	public T2 getSecond() {
		return second;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		writeToParcel(out, first);
		writeToParcel(out, second);
    }

    private void writeToParcel(Parcel out, Object value) {
    	Class<?> type = value.getClass();

    	if (Collection.class.isAssignableFrom(type)) {
			Collection<?> collection = (Collection<?>) value;

			// collections are passed as arrays and the name of the collection
			out.writeByte(COLLECTION);
			out.writeString(collection.getClass().getName());
			out.writeArray(collection.toArray());
		}
    	else {
    		out.writeByte(NON_COLLECTION);
    		out.writeValue(value);
    	}
    }

	public static final Creator<PairParcelable<?, ?>> CREATOR = new Creator<PairParcelable<?, ?>>() {

    	@SuppressWarnings("rawtypes")
		@Override
    	public PairParcelable<?, ?> createFromParcel(Parcel in) {
            return new PairParcelable(in);
        }

    	@Override
        public PairParcelable<?, ?>[] newArray(int size) {
            return new PairParcelable[size];
        }
    };
}