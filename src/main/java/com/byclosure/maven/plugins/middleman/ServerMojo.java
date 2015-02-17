package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal server
 * @requiresProject true
 */
public class ServerMojo extends AbstractMiddlemanMojo {

	/**
	 * @parameter default-value="false" expression="${middleman.force_polling}"
	 */
	protected boolean forcePolling;

	/**
	 * @parameter default-value="0.25" expression="${middleman.latency}"
	 */
	protected float latency;


	@Override
	public void executeMiddleman() throws MojoExecutionException {
		CommandLine cmdLine = new CommandLine("bundle");
		cmdLine.addArgument("exec");
		cmdLine.addArgument("middleman");
		cmdLine.addArgument("server");
		cmdLine.addArgument("-e");
		cmdLine.addArgument(mmEnv);

		if (forcePolling) {
			cmdLine.addArgument("--force-polling");
		}

		if (latency != 0.25) {
			cmdLine.addArgument(" --latency=" + latency);
		}

		executeProcess(cmdLine);
	}

}