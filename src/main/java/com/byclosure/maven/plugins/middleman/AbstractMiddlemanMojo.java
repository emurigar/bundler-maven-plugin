package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;

public abstract class AbstractMiddlemanMojo extends AbstractMojo {

	/**
	 * @parameter default-value="${project.basedir}/src/main/webapp/" expression="${middleman.root}"
	 */
	protected String mmRoot;

	/**
	 * @parameter default-value="development" expression="${middleman.env}"
	 */
	protected String mmEnv;


	public abstract void executeMiddleman() throws MojoExecutionException;

	@Override
	public void execute() throws MojoExecutionException {
		doBundleInstall();
		executeMiddleman();
	}

	protected void doBundleInstall() throws MojoExecutionException {
		CommandLine cmdLine = new CommandLine("bundle");
		cmdLine.addArgument("update");

		executeProcess(cmdLine);
	}

	protected void executeProcess(CommandLine cmdLine) throws MojoExecutionException {
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(new File(mmRoot));

		PumpStreamHandler pump = new PumpStreamHandler(System.out, System.err, System.in);
		executor.setStreamHandler(pump);

		try {
			executor.execute(cmdLine, System.getenv());
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		}
	}

}
