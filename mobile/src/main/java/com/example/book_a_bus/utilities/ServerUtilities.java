package com.example.book_a_bus.utilities;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public final class ServerUtilities
{
	private ServerUtilities()
	{
		// throw new UnsupportedOperationException();
	}


	private final static int CONN_TIMEOUT = 30000;
	private static HttpClient mClient;

	public final static HttpClient getDefaultClient()
	{
		if (mClient == null)
		{
			mClient = new DefaultHttpClient();

			final ClientConnectionManager mgr = mClient.getConnectionManager();
			final HttpParams hparams = mClient.getParams();
			final ClientConnectionManager conman = new ThreadSafeClientConnManager(hparams, mgr.getSchemeRegistry());
			mClient = new DefaultHttpClient(conman, hparams);

			HttpConnectionParams.setConnectionTimeout(hparams, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(hparams, CONN_TIMEOUT);

			ConnManagerParams.setTimeout(hparams, CONN_TIMEOUT);
		}
		return mClient;
	}

	
}
