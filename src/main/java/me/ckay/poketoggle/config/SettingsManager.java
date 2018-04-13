package me.ckay.poketoggle.config;

import me.ckay.poketoggle.utils.Utils;
import me.ckay.poketoggle.PokeToggle;

public class SettingsManager
{
	static SettingsManager instance = new SettingsManager();
	private PluginConfiguration data;

	public static SettingsManager getInstance()
	{
		return instance;
	}

	// Badges
	public void setupData(PokeToggle p)
	{
		Utils.saveResource(p, "data.yml", false);
		this.data = new PluginConfiguration(p, "data.yml");
	}

	public PluginConfiguration getData()
	{
		return data;
	}

	public void saveData()
	{
		this.data.save();
	}

	public void reloadData()
	{
		this.data.load();
	}

	
}
