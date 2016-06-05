package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import android.os.Bundle;

/**
 * A list activity that can be called with an explicit parameter.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

public abstract class AsimovCallableListActivity<Param> extends AsimovListActivity implements AsimovCallableActivityInterface<Param> {

	@Override
	AsimovActivityImpl mkAsimovImplementation() {
		return new AsimovCallableActivityImpl();
	}

	@Override
	public AsimovCallableActivityImpl getAsimovImplementation() {
		return (AsimovCallableActivityImpl) super.getAsimovImplementation();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		onCreate((Param) getAsimovImplementation().unparcelParameter(getIntent()));
	}

	/**
	 * Called when the activity is created or recreated. The parameter passed
	 * from the caller activity is explicitly provided.
	 *
	 * @param param the parameter passed from the caller activity
	 */

	protected abstract void onCreate(Param param);
}