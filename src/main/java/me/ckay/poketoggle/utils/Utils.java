package me.ckay.poketoggle.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import me.ckay.poketoggle.PokeToggle;

public class Utils
{
	public static void saveResource(PokeToggle plugin, String resourcePath, boolean replace)
	{
		if (resourcePath == null || resourcePath.equals(""))
		{
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}

		resourcePath = resourcePath.replace('\\', '/');
		InputStream in = PokeToggle.class.getClassLoader().getResourceAsStream(resourcePath);

		if (in == null)
		{
			throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found");
		}

		File outFile = new File(plugin.getConfigDir().toFile(), resourcePath);
		int lastIndex = resourcePath.lastIndexOf('/');
		File outDir = new File(plugin.getConfigDir().toFile(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

		if (!outDir.exists())
		{
			outDir.mkdirs();
		}

		try
		{
			if (!outFile.exists() || replace)
			{
				FileOutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;

				while ((len = in.read(buf)) > 0)
				{
					out.write(buf, 0, len);
				}

				out.close();
				in.close();
			}
			else
			{
				plugin.getLogger().warn("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists. That's a good thing!");
			}
		}
		catch (IOException ex)
		{
			plugin.getLogger().warn("Could not save " + outFile.getName() + " to " + outFile, ex);
		}
	}
}

