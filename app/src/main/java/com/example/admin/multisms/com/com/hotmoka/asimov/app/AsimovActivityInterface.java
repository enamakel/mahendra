package com.example.admin.multisms.com.com.hotmoka.asimov.app;

import android.content.Intent;

/**
 * An Asimov activity is able to start detachable asynchronous tasks
 * and call other activities with an explicit parameter.
 * 
 * @author Fausto Spoto <fausto.spoto@hotmoka.com>
 */

interface AsimovActivityInterface {

	/**
	 * Yields the object that implements the Asimov business logic.
	 *
	 * @return the implementation
	 */

	public AsimovActivityImpl getAsimovImplementation();

	public void startActivity(Intent intent);
}