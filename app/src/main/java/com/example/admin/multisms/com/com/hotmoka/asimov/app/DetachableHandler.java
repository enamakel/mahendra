package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

/**
 * A handler that gets automatically attached and detached to an activity,
 * following its lifecycle.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public abstract class DetachableHandler<Context extends AsimovActivityInterface, Progress, Result> {

	/**
	 * The context where this handler is running.
	 */

	private Context context;

	/**
	 * The last progress token;
	 */

	private Progress latestProgress;

	/**
	 * The handler used for the concurrent execution.
	 */

	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler() {

		@SuppressWarnings("unchecked")
		@Override
		public final void handleMessage(Message message) {
			switch (message.what) {
			case PROGRESS:
				//onProgressUpdate(unparcelProgress(message.getData().getParcelable("progress")));
				onProgressUpdate((Progress) message.obj);
				break;
			case END:
				onPostExecute((Result) message.obj);
				break;
			}
		}
	};

	/**
	 * The result of the computation, if it has been already computed but it has been impossible
	 * to signal the context about the event.
	 */

	private Result result;

	/**
	 * The main body of the handler. It performs the asynchronous computation
	 * and yields the result.
	 *
	 * @return the result of the computation
	 */

	protected abstract Result run();

	private final static int PROGRESS = 0;
	private final static int END = 1;

	/**
	 * Call this method to notify the current progress of the computation.
	 *
	 * @param progress the current progress
	 */

	protected final void notifyProgress(Progress progress) {
		Message message = handler.obtainMessage();
		message.obj = progress;
		message.what = PROGRESS;

		handler.sendMessage(message);
	}

	void notifyResult(Result result) {
		Message message = handler.obtainMessage();
		message.obj = result;
		message.what = END;

		handler.sendMessage(message);
	}

	private void onProgressUpdate(Progress progress) {
		Context context = this.context;

		if (context != null)
			onProgressUpdate(context, progress);
		else
			latestProgress = progress;
	}

	/**
	 * Called every time a bit of progress has been performed and this task
	 * is attached to a context. This method is allowed to interact with the
	 * views of the context activity.
	 * 
	 * @param context the context this task is attached to
	 * @param progress the progress token
	 */

	protected abstract void onProgressUpdate(Context context, Progress progress);

	void onPostExecute(Result result) {
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
	 * represented when the task will be re-attached to a context. This method
	 * is allowed to interact with the views of the context activity.
	 *
	 * @param context the context this task is attached to
	 * @param result the result of the task
	 */

	protected void onPostExecute(Context context, Result result) {};

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

	void detach() {
		onDetach();

		this.context = null;
	}

	/**
	 * Called when the context of this handle disappears. For instance, when an
	 * activity is the context of this handler and its {@code onStop} method gets called.
	 */

	protected void onDetach() {}

	/**
	 * Called when the context of this handler re-appears. For instance, when an
	 * activity is the context of this handler and its {@code onStart} method gets called.
	 *
	 * @param context the context
	 */

	@SuppressWarnings("unchecked")
	void attachTo(AsimovActivityInterface context) {
		if (context != this.context) {
			onAttach(this.context = (Context) context);
	
			// we signal the last progress
			if (latestProgress != null)
				onProgressUpdate(latestProgress);
	
			// if a result has been already computed but could not be signaled to
			// the context, this is the moment to do it
			if (result != null)
				onPostExecute(result);
		}
	}

	/**
	 * Called when the context of this handler re-appears. For instance, when an
	 * activity is the context of this handler and its {@code onStart} method gets called.
	 *
	 * @param context the context
	 */

	protected void onAttach(Context context) {}
}