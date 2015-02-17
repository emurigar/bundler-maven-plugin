package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMiddlemanMojo extends AbstractMojo {

	/**
	 * @parameter default-value="${project.basedir}/src/main/webapp/" expression="${middleman.root}"
	 */
	protected String mmRoot;

	/**
	 * @parameter default-value="development" expression="${middleman.env}"
	 */
	protected String mmEnv;


	protected boolean isWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}

	protected CommandLine getCrossPlatformCommandLine(String executable) {
		final CommandLine cmdLine;

		if (isWindows()) {
			cmdLine = new CommandLine("cmd");
			cmdLine.addArgument("/c");
			cmdLine.addArgument(executable);
		} else {
			cmdLine = new CommandLine(executable);
		}

		return cmdLine;
	}

	protected void executeCommandLine(CommandLine cmdLine) throws MojoExecutionException {
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(new File(mmRoot));

		PumpStreamHandler pump = new PumpStreamHandler(System.out, System.err, System.in);
		executor.setStreamHandler(pump);

		final Map<String, String> env = new HashMap<String, String>(System.getenv());
		env.put("MM_ENV", mmEnv);

		try {
			executor.execute(cmdLine, env);
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}

}
