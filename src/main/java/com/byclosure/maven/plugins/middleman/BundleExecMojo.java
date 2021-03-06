package com.byclosure.maven.plugins.middleman;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;

/**
 * @goal exec
 * @requiresProject true
 */
public class BundleExecMojo extends AbstractJRubyMojo {
	/**
	 * @parameter default-value="" expression="${bundler.exec_args}"
	 */
	protected String execArgs;

	@Override
	protected void executeComand() throws IOException {
		final File jrubyFile = new File(jruby_bin, "jruby");

		final Map<String, String> env = getEnv();

		final CommandLine cmd = getCrossPlatformCommandLine(jrubyFile.getPath());
		cmd.addArgument("-S");
		cmd.addArgument(new File(gem_home, new File("bin", "bundle").getPath())
				.getPath());
		cmd.addArgument("exec");

		if (execArgs != null) {
			final String[] splitted = execArgs.split(" ");

			if (splitted.length > 0) {
				if (!isWindows()) {
					splitted[0] = new File(binstubs, splitted[0]).getPath();
				}

				cmd.addArguments(splitted);
			}
		}

		executeCommandLine(cmd, env, new File(working_dir));
	}
}