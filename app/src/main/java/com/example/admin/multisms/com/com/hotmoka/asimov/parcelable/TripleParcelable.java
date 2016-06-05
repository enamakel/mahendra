package com.example.admin.multisms.com.com.hotmoka.asimov.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A parcelable triple of three parcelables.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public class TripleParcelable<T1 extends Parcelable, T2 extends Parcelable, T3 extends Parcelable> implements Parcelable {

	/**
	 * The first component of the triple.
	 */

	private final T1 first;

	/**
	 * The second component of the triple.
	 */

	private final T2 second;

	/**
	 * The third component of the triple.
	 */

	private final T3 third;

	/**
	 * Builds a parcelable triple of two parcelables.
	 *
	 * @param first the first component of the triple
	 * @param second the first component of the triple
	 * @param triple the first component of the triple
	 */

	public TripleParcelable(T1 first, T2 second, T3 third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	private TripleParcelable(Parcel in) {
	    this.first = in.readParcelable(getClass().getClassLoader());
	    this.second = in.readParcelable(getClass().getClassLoader());
	    this.third = in.readParcelable(getClass().getClassLoader());
	}

	/**
	 * Yields the first component of the triple.
	 * 
	 * @return the first component of the triple
	 */

	public T1 getFirst() {
		return first;
	}

	/**
	 * Yields the second component of the triple.
	 * 
	 * @return the second component of the triple
	 */

	public T2 getSecond() {
		return second;
	}

	/**
	 * Yields the third component of the triple.
	 * 
	 * @return the third component of the triple
	 */

	public T3 getThird() {
		return third;
	}

	@Override
    public int describeContents() {
        return 0;
    }

	@Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(first, 0);
        out.writeParcelable(second, 0);
        out.writeParcelable(third, 0);
    }

    public static final Creator<TripleParcelable<?, ?, ?>> CREATOR = new Creator<TripleParcelable<?, ?, ?>>() {

    	@SuppressWarnings("rawtypes")
		@Override
    	public TripleParcelable<?, ?, ?> createFromParcel(Parcel in) {
            return new TripleParcelable(in);
        }

    	@Override
        public TripleParcelable<?, ?, ?>[] newArray(int size) {
            return new TripleParcelable[size];
        }
    };
}