package com.cognizant.telstra.util;

import java.io.File;

import android.content.Context;

/**
 * This class deals with file related transactions. It create new cache file directory,
 * finds file in the directory and clear it from cache when required.
 * 
 * @author Ravi Bhojani
 * 
 */
public class FileCache
{

	private File cacheDir;

	public FileCache(Context context)
	{
		// Find the dir to save cached images
		// if
		// (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
		// cacheDir=new
		// File(android.os.Environment.getExternalStorageDirectory(),"ImageList");
		// else
		cacheDir = context.getCacheDir();
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url)
	{
		//identify images by hashcode. Not a perfect solution, good for the
		// demo.
		if (null != url)
		{
			//String filename = String.valueOf(url); //.hashCode()
			File f = new File(cacheDir, url);
			return f;
		}
		return null;
	}

	public void clear()
	{
		File[] files = cacheDir.listFiles();
		for (File f : files)
			f.delete();
	}

}