package com.byclosure.maven.plugins.middleman;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.exec.CommandLine;

/**
 * @goal install
 * @requiresProject true
 */
public class BundleInstallMojo extends AbstractJRubyMojo {
	/**
	 * @parameter default-value="Gemfile" expression="${bundler.gemfile}"
	 */
	protected String gemfile;

	@Override
	protected void executeComand() throws IOException {
		final File jrubyFile = new File(jruby_bin, "jruby");

		final Map<String, String> env = getEnv();

		final CommandLine cmd = getCrossPlatformCommandLine(jrubyFile.getPath());
		cmd.addArgument("-S");
		cmd.addArgument(new File(gem_home, new File("bin", "bundle").getPath())
				.getPath());
		cmd.addArgument("install");
		cmd.addArgument("--binstubs=" + binstubs);
		cmd.addArgument("--shebang=jruby");
		cmd.addArgument("--gemfile=" + gemfile);

		executeCommandLine(cmd, env, new File(working_dir));
	}
}