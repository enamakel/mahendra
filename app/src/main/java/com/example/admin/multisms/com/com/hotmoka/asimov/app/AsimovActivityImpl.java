package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

/**
 * Implementation code for Asimov activities.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

class AsimovActivityImpl {

	/**
	 * The list of tasks currently attached to instances of this activity.
	 */

	private final static Map<DetachableAsyncTask<?, ?, ?, ?>, DetachableAsyncTask<?, ?, ?, ?>> tasks
		= new HashMap<DetachableAsyncTask<?, ?, ?, ?>, DetachableAsyncTask<?, ?, ?, ?>>();

	/**
	 * The list of handlers currently attached to instances of this activity.
	 */

	private final static Map<DetachableHandler<?, ?, ?>, DetachableHandler<?, ?, ?>> handlers
		= new HashMap<DetachableHandler<?, ?, ?>, DetachableHandler<?, ?, ?>>();

	/**
	 * The key used to pass a parameter as an intent extra to a callable Asimov activity.
	 */
	
	final static String PARAM = AsimovActivityImpl.class.getPackage().getName() + ".param";

	/**
	 * The key used to pass the type of the parameter as an intent extra to a callable
	 * Asimov activity.
	 */
	
	final static String PARAM_TYPE = AsimovActivityImpl.class.getPackage().getName() + ".param_type";

	/**
	 * All tasks for this class of activities are attached to the context.
	 *
	 * @param context the context activity
	 */

	void onStart(AsimovActivityInterface context) {
		// we attach all tasks to the new instance of this activity
		for (DetachableAsyncTask<?, ?, ?, ?> task: tasks.keySet())
			task.attachTo(context);

		// we attach all handlers to the new instance of this activity
		for (DetachableHandler<?, ?, ?> handler: handlers.keySet())
			handler.attachTo(context);
	}

	void onCreate(AsimovActivityInterface context, Bundle instanceState) {
		recoverStateFrom(context.getClass(), context, instanceState);
	}

	private void recoverStateFrom(Class<?> clazz, AsimovActivityInterface context, Bundle savedInstanceState) {
		for (Field field: clazz.getDeclaredFields())
			if (field.isAnnotationPresent(State.class))
				recoverStateOf(field, context, savedInstanceState);
	
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != Activity.class)
			recoverStateFrom(superclass, context, savedInstanceState);
	}

	@SuppressWarnings("unchecked")
	private void recoverStateOf(Field field, AsimovActivityInterface context, Bundle bundle) {
		Class<?> type = field.getType();

		if (Parcelable.class.isAssignableFrom(type))
			set(field, context, bundle.getParcelable(keyFor(field)));
		else if (Collection.class.isAssignableFrom(type))
			try {
				// we recreate the exact collection class that was current at the
				// time when the activity has been stopped
				Parcelable[] elements = bundle.getParcelableArray(keyFor(field));

				Collection<Parcelable> collection;
				if (elements == null)
					collection = null;
				else {
					collection = (Collection<Parcelable>) Class.forName(bundle.getString(keyFor(field) + ":type")).newInstance();
					for (Parcelable p: elements)
						collection.add(p);
				}

				set(field, context, collection);
			}
			catch (InstantiationException e) {}
			catch (IllegalAccessException e) {}
			catch (ClassNotFoundException e) {}
		else if (type.isArray()) {
			Class<?> elementsType = type.getComponentType();

			if (Parcelable.class.isAssignableFrom(elementsType))
				// we recreate the exact array type that was current at the
				// time when the activity has been stopped
				set(field, context, bundle.getParcelableArray(keyFor(field)));
			else if (elementsType == int.class)
				set(field, context, bundle.getIntArray(keyFor(field)));
			else if (elementsType == byte.class)
				set(field, context, bundle.getByteArray(keyFor(field)));
			else if (elementsType == char.class)
				set(field, context, bundle.getCharArray(keyFor(field)));
			else if (elementsType == short.class)
				set(field, context, bundle.getShortArray(keyFor(field)));
			else if (elementsType == long.class)
				set(field, context, bundle.getLongArray(keyFor(field)));
			else if (elementsType == float.class)
				set(field, context, bundle.getFloatArray(keyFor(field)));
			else if (elementsType == double.class)
				set(field, context, bundle.getDoubleArray(keyFor(field)));
		}
		else if (type == int.class)
			set(field, context, bundle.getInt(keyFor(field)));
		else if (type == byte.class)
			set(field, context, bundle.getByte(keyFor(field)));
		else if (type == char.class)
			set(field, context, bundle.getChar(keyFor(field)));
		else if (type == short.class)
			set(field, context, bundle.getShort(keyFor(field)));
		else if (type == long.class)
			set(field, context, bundle.getLong(keyFor(field)));
		else if (type == float.class)
			set(field, context, bundle.getFloat(keyFor(field)));
		else if (type == double.class)
			set(field, context, bundle.getDouble(keyFor(field)));
	}

	void onSaveInstanceState(AsimovActivityInterface context, Bundle instanceState) {
		saveStateFrom(context.getClass(), context, instanceState);
	}

	private void saveStateFrom(Class<?> clazz, AsimovActivityInterface context, Bundle instanceState) {
		for (Field field: clazz.getDeclaredFields())
			if (field.isAnnotationPresent(State.class))
				saveStateOf(field, context, instanceState);
	
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != Activity.class)
			saveStateFrom(superclass, context, instanceState);
	}

	private void saveStateOf(Field field, AsimovActivityInterface context, Bundle bundle) {
		Class<?> type = field.getType();

		if (Parcelable.class.isAssignableFrom(type))
			bundle.putParcelable(keyFor(field), (Parcelable) get(field, context));
		else if (Collection.class.isAssignableFrom(type)) {
			Collection<?> collection = (Collection<?>) get(field, context);
			Parcelable[] array = collection != null ? collection.toArray(new Parcelable[collection.size()]) : null;
			bundle.putParcelableArray(keyFor(field), array);
			if (collection != null)
				bundle.putString(keyFor(field) + ":type", collection.getClass().getName());
		}
		else if (type.isArray()) {
			Class<?> elementsType = type.getComponentType();

			if (Parcelable.class.isAssignableFrom(elementsType))
				bundle.putParcelableArray(keyFor(field), (Parcelable[]) get(field, context));
			else if (elementsType == int.class)
				bundle.putIntArray(keyFor(field), (int[]) get(field, context));
			else if (elementsType == byte.class)
				bundle.putByteArray(keyFor(field), (byte[]) get(field, context));
			else if (elementsType == char.class)
				bundle.putCharArray(keyFor(field), (char[]) get(field, context));
			else if (elementsType == short.class)
				bundle.putShortArray(keyFor(field), (short[]) get(field, context));
			else if (elementsType == long.class)
				bundle.putLongArray(keyFor(field), (long[]) get(field, context));
			else if (elementsType == double.class)
				bundle.putDoubleArray(keyFor(field), (double[]) get(field, context));
			else if (elementsType == float.class)
				bundle.putFloatArray(keyFor(field), (float[]) get(field, context));
		}
		else if (type == int.class)
			bundle.putInt(keyFor(field), (Integer) get(field, context));
		else if (type == byte.class)
			bundle.putByte(keyFor(field), (Byte) get(field, context));
		else if (type == char.class)
			bundle.putChar(keyFor(field), (Character) get(field, context));
		else if (type == short.class)
			bundle.putShort(keyFor(field), (Short) get(field, context));
		else if (type == long.class)
			bundle.putLong(keyFor(field), (Long) get(field, context));
		else if (type == double.class)
			bundle.putDouble(keyFor(field), (Double) get(field, context));
		else if (type == float.class)
			bundle.putFloat(keyFor(field), (Float) get(field, context));
	}

	private Object get(Field field, AsimovActivityInterface context) {
		field.setAccessible(true);

		try {
			return field.get(context);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
			Log.d(context.getClass().getName(), e.toString());
		}

		return null;
	}

	private void set(Field field, AsimovActivityInterface context, Object value) {
		field.setAccessible(true);

		try {
			field.set(context, value);
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
			Log.d(context.getClass().getName(), e.toString());
		}
	}

	private static String keyFor(Field field) {
		return field.getDeclaringClass().getName() + "." + field.getName();
	}

	void onStop() {
		// we detach all tasks from this instance
		for (DetachableAsyncTask<?, ?, ?, ?> task: tasks.keySet())
			task.detach();

		// we detach all handlers from this instance
		for (DetachableHandler<?, ?, ?> handler: handlers.keySet())
			handler.detach();
	}

	/**
	 * Start the given task with the given parameters. If the task was already running,
	 * nothing is started.
	 *
	 * @param task the task
	 * @param params the parameters
	 * @return the task that gets run. This might be an equal task, already running
	 */

	<Params> DetachableAsyncTask<?, ?, ?, ?> submit(AsimovActivityInterface context, DetachableAsyncTask<?, Params, ?, ?> task, Params... params) {
		DetachableAsyncTask<?, ?, ?, ?> result = tasks.get(task);

		if (result == null) {
			tasks.put(task, result = task);
			task.attachTo(context);
			task.execute(params);
		}

		return result;
	}

	/**
	 * Start the given task with the given parameters. If the task was already running,
	 * nothing is started.
	 *
	 * @param task the task
	 * @param params the parameters
	 * @return the task that gets run. This might be an equal task, already running
	 */

	<Result> DetachableHandler<?, ?, ?> submit(AsimovActivityInterface context, DetachableHandler<?, ?, Result> task) {
		DetachableHandler<?, ?, ?> result = handlers.get(task);

		if (result == null) {
			handlers.put(task, result = task);
			task.attachTo(context);
			new Thread(new RunnableHandler<Result>(task)).start();
		}

		return result;
	}

	private static class RunnableHandler<Result> implements Runnable {
		private final DetachableHandler<?, ?, Result> handler;
		private RunnableHandler(DetachableHandler<?, ?, Result> handler) {
			this.handler = handler;
		}

		@Override
		public void run() {
			handler.notifyResult(handler.run());
		}
	}

	/**
	 * Detaches the given task from those already running for instances of this activity.
	 *
	 * @param task the task
	 */

	void remove(DetachableAsyncTask<?, ?, ?, ?> task) {
		tasks.remove(task);
	}

	/**
	 * Detaches the given handler from those already running for instances of this activity.
	 *
	 * @param handler the handler
	 */

	void remove(DetachableHandler<?, ?, ?> handler) {
		handlers.remove(handler);
	}

	/**
	 * Calls the given callee activity by passing the given parameter.
	 *
	 * @param Param the type of the parameter passed to the callee activity
	 * @param context the caller activity
	 * @param callee the class of the callee activity
	 * @param param the parameter passed to the callee activity
	 */

	<Param> void call(AsimovActivityInterface context, Class<? extends AsimovCallableActivityInterface<Param>> callee, Param param) {
		Class<?> type = param.getClass();
		Intent intent = new Intent((Activity) context, callee).putExtra(PARAM_TYPE, type);

		if (param instanceof Parcelable)
			context.startActivity(intent.putExtra(PARAM, (Parcelable) param));
		else if (type == Integer.class)
			context.startActivity(intent.putExtra(PARAM, ((Integer) param).intValue()));
		else if (type == Boolean.class)
			context.startActivity(intent.putExtra(PARAM, ((Boolean) param).booleanValue()));
		else if (type == Character.class)
			context.startActivity(intent.putExtra(PARAM, ((Character) param).charValue()));
		else if (type == Byte.class)
			context.startActivity(intent.putExtra(PARAM, ((Byte) param).byteValue()));
		else if (type == Short.class)
			context.startActivity(intent.putExtra(PARAM, ((Short) param).shortValue()));
		else if (type == Long.class)
			context.startActivity(intent.putExtra(PARAM, ((Long) param).longValue()));
		else if (type == Float.class)
			context.startActivity(intent.putExtra(PARAM, ((Float) param).floatValue()));
		else if (type == Double.class)
			context.startActivity(intent.putExtra(PARAM, ((Double) param).doubleValue()));
		else if (type.isArray()) {
			Class<?> elementsType = type.getComponentType();

			if (Parcelable.class.isAssignableFrom(elementsType))
				context.startActivity(intent.putExtra(PARAM, (Parcelable[]) param));
			else if (elementsType == int.class)
				context.startActivity(intent.putExtra(PARAM, (int[]) param));
			else if (elementsType == boolean.class)
				context.startActivity(intent.putExtra(PARAM, (boolean[]) param));
			else if (elementsType == char.class)
				context.startActivity(intent.putExtra(PARAM, (char[]) param));
			else if (elementsType == byte.class)
				context.startActivity(intent.putExtra(PARAM, (byte[]) param));
			else if (elementsType == short.class)
				context.startActivity(intent.putExtra(PARAM, (short[]) param));
			else if (elementsType == long.class)
				context.startActivity(intent.putExtra(PARAM, (long[]) param));
			else if (elementsType == float.class)
				context.startActivity(intent.putExtra(PARAM, (float[]) param));
			else if (elementsType == double.class)
				context.startActivity(intent.putExtra(PARAM, (double[]) param));
			else if (elementsType == CharSequence.class)
				context.startActivity(intent.putExtra(PARAM, (CharSequence[]) param));
			else
				Log.e(getClass().getName(), "invalid parameter type " + type);
		}
		else if (param instanceof Collection<?>) {
			@SuppressWarnings("unchecked")
			Collection<Parcelable> collection = (Collection<Parcelable>) param;

			// collections are passed as arrays of Parcelable
			context.startActivity(intent.putExtra(PARAM, collection.toArray(new Parcelable[collection.size()])));
		}
		else if (param instanceof CharSequence)
			context.startActivity(intent.putExtra(PARAM, (CharSequence) param)); 
		else
			Log.e(getClass().getName(), "invalid parameter type " + type);
	}
}