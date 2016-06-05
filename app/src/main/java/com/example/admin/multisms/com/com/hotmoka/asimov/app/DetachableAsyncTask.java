package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

/**
 * An asynchronous task that gets automatically attached and detached to an activity,
 * following its lifecycle.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public abstract class DetachableAsyncTask<Context extends AsimovActivityInterface, Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	/**
	 * The current activity this task is attached to, if any.
	 */

	private Context context;

	/**
	 * The last progress token;
	 */

	private Progress[] latestProgress;

	/**
	 * The result of the computation, if it has been already computed but it has been impossible
	 * to signal the context about the event.
	 */

	private Result result;

	/**
	 * True if the task has been cancelled and it has been impossible to signal the context about the event.
	 */

	private boolean cancelled;

	/**
	 * The partial result of the computation, if the task has been cancelled and it has been impossible
	 * to signal the context about the event.
	 */

	private Result cancelledResult;

	@Override
	protected final void onPreExecute() {
		Context context = this.context;

		if (context != null)
			onPreExecute(context);
	}

	/**
	 * Called at the beginning of the execution of the task, if it is attached to a context.
	 * It my never be called if the task is not attached to any context at its start.
	 *
	 * @param context the context this task is attached to
	 */

	protected void onPreExecute(Context context) {};

	@Override
	protected final void onProgressUpdate(Progress... progress) {
		Context context = this.context;

		if (context != null)
			onProgressUpdate(context, progress);
		else
			latestProgress = progress;
	}

	/**
	 * Called every time a bit of progress has been performed and this task
	 * is attached to a context. This method is allowed to interact with the UI
	 * of the context.
	 *
	 * @param context the context this task is attached to
	 * @param progress the progress tokens
	 */

	protected abstract void onProgressUpdate(Context context, Progress... progress);

	@Override
	protected final void onPostExecute(Result result) {
		Context context = this.context;

		if (context != null) {
			onPostExecute(context, result);
			context.getAsimovImplementation().remove(this);
		}
		else
			this.result = result;
	}

	/**
	 * Called at the end of the execution of this task, if it is attached to a context.
	 * If this task is not attached to a context, the call is skipped and will be
	 * represented when the task will be re-attached to a context. This method is allowed
	 * to interact with the UI of the context.
	 *
	 * @param context the context this task is attached to
	 * @param result the result of the task
	 */

	protected void onPostExecute(Context context, Result result) {};

	@Override
	protected final void onCancelled(Result result) {
		Context context = this.context;

		if (context != null) {
			onCancelled(context, result);
			context.getAsimovImplementation().remove(this);
		}
		else
			this.cancelledResult = result;
	}

	/**
	 * Called if this task is cancelled, if it is attached to a context.
	 * If this task is not attached to a context, the call is skipped and will be
	 * represented when the task will be re-attached to a context.
	 * This method is allowed to interact with the UI of the context.
	 *
	 * @param context the context this task is attached to
	 * @param result the partial result of the task
	 */

	protected void onCancelled(Context context, Result result) {};

	@Override
	protected final void onCancelled() {
		Context context = this.context;

		if (context != null) {
			onCancelled(context);
			context.getAsimovImplementation().remove(this);
		}
		else
			this.cancelled = true;
	}

	/**
	 * Called if this task is cancelled, if it is attached to a context.
	 * If this task is not attached to a context, the call is skipped and will be
	 * represented when the task will be re-attached to a context.
	 * This method is allowed to interact with the UI of the context.
	 *
	 * @param context the context this task is attached to
	 */

	protected void onCancelled(Context context) {};

	@Override
	public boolean equals(Object other) {
		return other.getClass() == getClass();
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

	/**
	 * Called when the context of this task disappears. For instance, when an
	 * activity is the context of this task and its {@code onStop} method gets called.
	 */

	protected void detach() {
		onDetach();

		this.context = null;
	}

	/**
	 * Called when the context of this task disappears. For instance, when an
	 * activity is the context of this task and its {@code onStop} method gets called.
	 */

	protected void onDetach() {}

	/**
	 * Called when the context of this task re-appears. For instance, when an
	 * activity is the context of this task and its {@code onStart} method gets called.
	 *
	 * @param context the context
	 */

	@SuppressLint("NewApi")
	@SuppressWarnings("unchecked")
	void attachTo(AsimovActivityInterface context) {
		if (context == this.context)
			return;

		onAttach((Context) context);

		this.context = (Context) context;

		// we signal the last progress
		if (latestProgress != null)
			onProgressUpdate(this.context, latestProgress);

		// if a result has been already computed but could not be signaled to
		// the context, this is the moment to do it
		if (result != null)
			onPostExecute(result);

		// the task has been cancelled but it was not possible to signal the context
		// about it: we do it now
		if (cancelled)
			this.onCancelled();

		// if a result has been already computed but could not be signaled to
		// the context, this is the moment to do it
		if (cancelledResult != null)
			this.onCancelled(cancelledResult);
	}

	/**
	 * Called when the context of this task re-appears. For instance, when an
	 * activity is the context of this task and its {@code onStart} method gets called.
	 *
	 * @param context the context
	 */

	protected void onAttach(Context context) {}
}