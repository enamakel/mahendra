package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import android.app.ListActivity;
import android.os.Bundle;

/**
 * A list activity that deals with asynchronous tasks. These tasks
 * are detached and attached to the activity automatically, by following
 * the normal lifecycle of Android activities.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public abstract class AsimovListActivity extends ListActivity implements AsimovActivityInterface {

	private final AsimovActivityImpl impl = mkAsimovImplementation();

	AsimovActivityImpl mkAsimovImplementation() {
		return new AsimovActivityImpl();
	}

	@Override
	public AsimovActivityImpl getAsimovImplementation() {
		return impl;
	}

	@Override
	protected void onCreate(Bundle instanceState) {
		super.onCreate(instanceState);

		if (instanceState != null)
			impl.onCreate(this, instanceState);
	}

	@Override
	protected void onStart() {
		super.onStart();

		impl.onStart(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle instanceState) {
		super.onSaveInstanceState(instanceState);

		impl.onSaveInstanceState(this, instanceState);
	}

	@Override
	protected void onStop() {
		super.onStop();

		impl.onStop();
	}

	/**
	 * Start the given task with the given parameters. If the task was already running,
	 * nothing is started.
	 *
	 * @param task the task
	 * @param params the parameters
	 * @return the task that gets run. This might be an equal task, already running
	 */

	protected final <Params> DetachableAsyncTask<?, ?, ?, ?> submit(DetachableAsyncTask<?, Params, ?, ?> task, Params... params) {
		return impl.submit(this, task, params);
	}

	/**
	 * Start the given task. If the task was already running, nothing is started.
	 *
	 * @param task the task
	 * @return the task that gets run. This might be an equal task, already running
	 */

	protected final <Result> DetachableHandler<?, ?, ?> submit(DetachableHandler<?, ?, Result> task) {
		return impl.submit(this, task);
	}

	/**
	 * Starts a new callee activity and passes the given parameter.
	 *
	 * @param callee the callee activity
	 * @param param the parameter
	 */

	protected final <Param> void call(Class<? extends AsimovCallableActivityInterface<Param>> callee, Param param) {
		impl.call(this, callee, param);
	}
}