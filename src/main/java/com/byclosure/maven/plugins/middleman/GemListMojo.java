package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal gem-list
 * @requiresProject false
 */
public class GemListMojo extends AbstractMiddlemanMojo {

	@Override
	public void execute() throws MojoExecutionException {
		CommandLine cmdLine = getCrossPlatformCommandLine("gem");
		cmdLine.addArgument("list");
		executeCommandLine(cmdLine);
	}

}