package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import java.util.Collection;

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;

class AsimovCallableActivityImpl extends AsimovActivityImpl {

	Object unparcelParameter(Intent intent) {
		Class<?> type = (Class<?>) intent.getSerializableExtra(PARAM_TYPE);

		if (type == int.class)
			return intent.getIntExtra(PARAM, 0);
		else if (type == boolean.class)
			return intent.getBooleanExtra(PARAM, false);
		else if (type == char.class)
			return intent.getCharExtra(PARAM, '\0');
		else if (type == byte.class)
			return intent.getByteExtra(PARAM, (byte) 0);
		else if (type == short.class)
			return intent.getShortExtra(PARAM, (short) 0);
		else if (type == long.class)
			return intent.getLongExtra(PARAM, 0L);
		else if (type == float.class)
			return intent.getFloatExtra(PARAM, 0.0f);
		else if (type == double.class)
			return intent.getDoubleExtra(PARAM, 0.0);
		else if (type == CharSequence.class)
			return intent.getCharSequenceExtra(PARAM);
		else if (type.isArray()) {
			Class<?> elementsType = type.getComponentType();

			if (Parcelable.class.isAssignableFrom(elementsType)) {
				// we transform the declared type of the array
				Parcelable[] array = (Parcelable[]) intent.getParcelableArrayExtra(PARAM);
				Object[] result = (Object[]) java.lang.reflect.Array.newInstance(type.getComponentType(), array.length);
				System.arraycopy(array, 0, result, 0, array.length);

				return result;
			}
			else if (elementsType == int.class)
				return intent.getIntArrayExtra(PARAM);
			else if (elementsType == boolean.class)
				return intent.getBooleanArrayExtra(PARAM);
			else if (elementsType == char.class)
				return intent.getCharArrayExtra(PARAM);
			else if (elementsType == byte.class)
				return intent.getByteArrayExtra(PARAM);
			else if (elementsType == short.class)
				return intent.getShortArrayExtra(PARAM);
			else if (elementsType == long.class)
				return intent.getLongArrayExtra(PARAM);
			else if (elementsType == float.class)
				return intent.getFloatArrayExtra(PARAM);
			else if (elementsType == double.class)
				return intent.getDoubleArrayExtra(PARAM);
			else if (elementsType == CharSequence.class)
				return intent.getCharSequenceArrayExtra(PARAM);
		}
		else if (Collection.class.isAssignableFrom(type)) {
			// collections are passed as arrays of Parcelable
			Parcelable[] array = (Parcelable[]) intent.getParcelableArrayExtra(PARAM);
			try {
				@SuppressWarnings("unchecked")
				Collection<Parcelable> collection = (Collection<Parcelable>) type.newInstance();
				for (Parcelable p: array)
					collection.add(p);

				return collection;
			} catch (InstantiationException e) {
				Log.e(getClass().getName(), e.toString());
			} catch (IllegalAccessException e) {
				Log.e(getClass().getName(), e.toString());
			}
		}
		else if (Parcelable.class.isAssignableFrom(type))
			return intent.getParcelableExtra(PARAM);

		Log.e(getClass().getName(), "invalid parameter type: " + type);

		return null;
	}
}