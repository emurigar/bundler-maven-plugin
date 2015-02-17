package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal bundle-install
 * @requiresProject false
 */
public class BundleInstallMojo extends AbstractMiddlemanMojo {

	@Override
	public void execute() throws MojoExecutionException {
		CommandLine cmdLine = getCrossPlatformCommandLine("bundle");
		cmdLine.addArgument("update");
		executeCommandLine(cmdLine);
	}

}