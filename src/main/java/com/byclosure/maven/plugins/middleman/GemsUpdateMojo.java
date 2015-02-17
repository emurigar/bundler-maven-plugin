package com.byclosure.maven.plugins.middleman;

import org.apache.maven.plugin.MojoExecutionException;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.util.List;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

/**
 * @goal gems-update
 * @requiresProject true
 */
public class GemsUpdateMojo extends AbstractMiddlemanMojo {

	@Override
	public void execute() throws MojoExecutionException {
		final List<MojoExecutor.Element> argList = getEmptyArguments();
		argList.add(element(name("argument"), "update"));

		executeMojo(
				getExecMavenPlugin(),
				goal("exec"),
				getBundleConfiguration(argList),
				getEnv()
		);
	}

	@Override
	public void executeMiddleman() throws MojoExecutionException {
	}
}