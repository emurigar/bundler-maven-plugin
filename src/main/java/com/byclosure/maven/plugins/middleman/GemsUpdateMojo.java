package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal gems-update
 * @requiresProject true
 */
public class GemsUpdateMojo extends AbstractMiddlemanMojo {

	@Override
	public void execute() throws MojoExecutionException {
		CommandLine cmdLine = new CommandLine("bundle");
		cmdLine.addArgument("update");

		executeProcess(cmdLine);
	}

	@Override
	public void executeMiddleman() throws MojoExecutionException {
	}
}