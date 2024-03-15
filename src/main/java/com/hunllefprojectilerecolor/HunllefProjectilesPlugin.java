package com.hunllefprojectilerecolor;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Projectile;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Hunllef Projectiles",
	description = "Alter the Projectiles for Crystalline Hunllef's Prayer-Disable Attack",
	tags = {"gauntlet", "boss", "pvm", "hunllef"}
)
public class HunllefProjectilesPlugin extends Plugin
{
	// Hunllef's Head Projectile IDs
	private static final int MAGIC_PROJECTILE = 1713;

	@Inject
	private Client client;

	@Inject
	private HunllefProjectilesConfig config;

	@Provides
	HunllefProjectilesConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(HunllefProjectilesConfig.class);
	}

	@Subscribe
	public void onProjectileMoved(ProjectileMoved projectileMoved)
	{
		Projectile projectile = projectileMoved.getProjectile();
		if (projectile.getId() == MAGIC_PROJECTILE)
		{
			replaceProjectile(projectile, config.style().getMagic());
		}
	}

	private void replaceProjectile(Projectile projectile, int projectileId)
	{
		Projectile p = client.createProjectile(projectileId,
			projectile.getFloor(),
			projectile.getX1(), projectile.getY1(),
			projectile.getHeight(),
			projectile.getStartCycle(), projectile.getEndCycle(),
			projectile.getSlope(),
			projectile.getStartHeight(), projectile.getEndHeight(),
			projectile.getInteracting(),
			projectile.getTarget().getX(), projectile.getTarget().getY());

		client.getProjectiles().addLast(p);
		projectile.setEndCycle(0);
	}
}
