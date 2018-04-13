package me.ckay.poketoggle.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;

import me.ckay.poketoggle.PokeToggle;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

/**
 * A simple utility class to manage configurations with a file associated to them.
 */
public class PluginConfiguration
{
	private File file;
	private ConfigurationNode config;
	private YAMLConfigurationLoader configurationLoader;

	public PluginConfiguration(File file)
	{
		this.file = file;
		this.load();
	}

	public PluginConfiguration(PokeToggle plugin, String name)
	{
		this(new File(plugin.getConfigDir().toFile(), name));
	}

	public void load()
	{
		try
		{
			if (!file.isFile())
			{
				if (file.getParentFile() != null)
				{
					file.getParentFile().mkdirs();
				}

				file.createNewFile();
			}

			configurationLoader = YAMLConfigurationLoader.builder().setFile(file).build();
			config = configurationLoader.load();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}

	public void save()
	{
		try
		{
			configurationLoader.save(config);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public ConfigurationNode getConfig()
	{
		return config;
	}

	public YAMLConfigurationLoader getConfigurationLoader()
	{
		return configurationLoader;
	}

	public File getFile()
	{
		return file;
	}

	public String getFileName()
	{
		return file.getName();
	}

	public int getInt(String path)
	{
		return this.config.getNode((Object[]) path.split("\\.")).getInt();
	}

	public double getDouble(String path)
	{
		return this.config.getNode((Object[]) path.split("\\.")).getDouble();
	}

	public String getString(String path)
	{
		return this.config.getNode((Object[]) path.split("\\.")).getString();
	}

	public boolean getBoolean(String path)
	{
		return this.config.getNode((Object[]) path.split("\\.")).getBoolean();
	}

	public Object get(String path)
	{
		return this.config.getNode((Object[]) path.split("\\.")).getValue();
	}

	public void set(String path, Object val)
	{
		this.config.getNode((Object[]) path.split("\\.")).setValue(val);
	}

	public List<String> getStringList(String path)
	{
		List<String> list = Lists.newArrayList();

		try
		{
			list = new ArrayList<>(this.config.getNode((Object[]) path.split("\\.")).getList(TypeToken.of(String.class)));
		}
		catch (ObjectMappingException e)
		{
			;
		}

		return list;
	}
}

