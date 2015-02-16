package com.byclosure.maven.plugins.middleman;

import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal gems-install
 * @requiresProject true
 */
public class GemsInstallMojo extends AbstractMiddlemanMojo {

	@Override
	public void execute() throws MojoExecutionException {
		installBundler();
		doBundleInstall();
	}

	@Override
	public void executeMiddleman() throws MojoExecutionException {
	}
}