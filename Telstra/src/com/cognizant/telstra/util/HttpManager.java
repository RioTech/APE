package com.cognizant.telstra.util;

/**
 * 
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class handle all the operation related to HTTP. This is only being used
 * for getting image from the server.
 * 
 * @author Ravi Bhojani
 *         
 */
public class HttpManager
{
	/**
	 * This method will establish connection with server and return stream 
	 * @param request
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */
	public InputStream getResponse(String request) throws MalformedURLException, IOException, Exception
	{
		URL url = new URL(request);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(30000);
		InputStream is = connection.getInputStream();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
		{
			return is;
		}
		else
		{
			return null;
		}
	}
}
