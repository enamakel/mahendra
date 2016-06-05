package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import java.util.Collection;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * A parcelable wrapper of a collection of parcelable objects.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class CollectionParcelable<P extends Parcelable> implements Parcelable {

	/**
	 * The wrapped value.
	 */

	private final Collection<P> value;

	/**
	 * Builds a parcelable wrapper of the given value.
	 *
	 * @param value the wrapped value
	 */

	public CollectionParcelable(Collection<P> value) {
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	private CollectionParcelable(Parcel in) {
		Object[] elements = in.readArray(getClass().getClassLoader());
		String type = in.readString();
		Collection<P> collection = null;

		try {
			collection = (Collection<P>) Class.forName(type).newInstance();
		} catch (InstantiationException e) {
			Log.d(getClass().getName(), e.toString());
		} catch (IllegalAccessException e) {
			Log.d(getClass().getName(), e.toString());
		} catch (ClassNotFoundException e) {
			Log.d(getClass().getName(), e.toString());
		}

		for (Object e: elements)
			collection.add((P) e);

		this.value = collection;
	}

	/**
	 * Yields the wrapped value.
	 * 
	 * @return the wrapped value
	 */

	public Collection<P> getValue() {
		return value;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
		out.writeArray(value.toArray());
		out.writeString(value.getClass().getName());
    }

    public static final Creator<CollectionParcelable<?>> CREATOR = new Creator<CollectionParcelable<?>>() {

    	@SuppressWarnings("rawtypes")
		@Override
    	public CollectionParcelable<?> createFromParcel(Parcel in) {
            return new CollectionParcelable(in);
        }

    	@Override
        public CollectionParcelable<?>[] newArray(int size) {
            return new CollectionParcelable[size];
        }
    };
}