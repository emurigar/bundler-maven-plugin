package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal gem-env
 * @requiresProject false
 */
public class GemEnvMojo extends AbstractMiddlemanMojo {

	@Override
	public void execute() throws MojoExecutionException {
		CommandLine cmdLine = getCrossPlatformCommandLine("gem");
		cmdLine.addArgument("env");
		executeCommandLine(cmdLine);
	}

}