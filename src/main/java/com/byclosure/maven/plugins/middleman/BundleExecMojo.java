package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

		final Map<String, String> env = new HashMap<String, String>(System.getenv());
		env.put("GEM_HOME", gem_home);
		env.put("GEM_PATH", gem_path);

		final CommandLine cmd = getCrossPlatformCommandLine(jrubyFile.getPath());
		cmd.addArgument("-S");
		cmd.addArgument(new File(gem_home, new File("bin", "bundle").getPath()).getPath());
		cmd.addArgument("exec");

		if (execArgs != null) {
			cmd.addArguments(execArgs.split(" "));
		}

		executeCommandLine(cmd, env, project.getBasedir());
	}
}