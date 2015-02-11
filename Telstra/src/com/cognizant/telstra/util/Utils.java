package com.cognizant.telstra.util;

/**
 * 
 */

import java.io.InputStream;
import java.io.OutputStream;

import android.util.Log;

/**
 * @author Ravi Bhojani
 * 
 */
public class Utils
{
	private final static String LOG_TAG = Utils.class.getName();
	
	public static void CopyStream(InputStream is, OutputStream os)
	{
		final int buffer_size = 1024;
		try
		{
			byte[] bytes = new byte[buffer_size];
			for (;;)
			{
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		}
		catch (Exception e)
		{
			Log.i(LOG_TAG, "Exception "+e.getMessage(),e);
		}
	}
}
