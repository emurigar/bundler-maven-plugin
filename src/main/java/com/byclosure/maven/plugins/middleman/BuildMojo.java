package com.byclosure.maven.plugins.middleman;

import org.apache.maven.plugin.MojoExecutionException;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.util.List;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

/**
 * @goal build
 * @phase generate-resources
 * @requiresProject true
 */
public class BuildMojo extends AbstractMiddlemanMojo {
	@Override
	public void executeMiddleman() throws MojoExecutionException {
		final List<MojoExecutor.Element> argList = getEmptyArguments();

		argList.add(element(name("argument"), "exec"));
		argList.add(element(name("argument"), "middleman build -e " + mmEnv));

		executeMojo(
				getExecMavenPlugin(),
				goal("exec"),
				getBundleConfiguration(argList),
				getEnv()
		);
	}
}