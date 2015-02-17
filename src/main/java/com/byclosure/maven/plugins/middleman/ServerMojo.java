package com.byclosure.maven.plugins.middleman;

import org.apache.maven.plugin.MojoExecutionException;

import java.util.List;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

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
		final List<Element> argList = getEmptyArguments();

		argList.add(element(name("argument"), "exec"));
		argList.add(element(name("argument"), "middleman server -e " + mmEnv));

		if (forcePolling) {
			argList.add(element(name("argument"), "--force-polling"));
		}

		if (latency != 0.25) {
			argList.add(element(name("argument"), "--latency=" + latency));
		}

		executeMojo(
				getExecMavenPlugin(),
				goal("exec"),
				getBundleConfiguration(argList),
				getEnv()
		);
	}

}