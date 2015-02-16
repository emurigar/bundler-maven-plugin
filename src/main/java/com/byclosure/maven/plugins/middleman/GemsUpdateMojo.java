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
		installBundler();

		final List<MojoExecutor.Element> argList = getJRubyCompleteArguments();
		argList.add(element(name("argument"), "bundle"));
		argList.add(element(name("argument"), "update"));

		executeMojo(
				getExecMavenPlugin(),
				goal("exec"),
				getConfiguration(argList),
				getEnv()
		);
	}

	@Override
	public void executeMiddleman() throws MojoExecutionException {
	}
}