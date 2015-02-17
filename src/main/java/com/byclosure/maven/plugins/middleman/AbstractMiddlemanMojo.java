package com.byclosure.maven.plugins.middleman;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.BuildPluginManager;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.xml.Xpp3Dom;
import org.twdata.maven.mojoexecutor.MojoExecutor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.twdata.maven.mojoexecutor.MojoExecutor.*;

public abstract class AbstractMiddlemanMojo extends AbstractMojo {
	/**
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	protected MavenProject mavenProject;

	/**
	 * @parameter expression="${session}"
	 * @required
	 * @readonly
	 */
	protected MavenSession mavenSession;

	/**
	 * @component
	 * @required
	 */
	protected BuildPluginManager pluginManager;

	/**
	 * @parameter expression="${middleman.gem_home}"
	 * default-value="${project.build.directory}/rubygems"
	 */
	protected File gemHome;

	/**
	 * @parameter expression="${middleman.gem_path}"
	 * default-value="${project.build.directory}/rubygems"
	 */
	protected File gemPath;


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
		final List<Element> argList = getEmptyArguments();

		argList.add(element(name("argument"), "install"));

		executeMojo(
				getExecMavenPlugin(),
				goal("exec"),
				getBundleConfiguration(argList),
				getEnv()
		);
	}


	protected ExecutionEnvironment getEnv() {
		return executionEnvironment(
				mavenProject,
				mavenSession,
				pluginManager
		);
	}

	protected Plugin getExecMavenPlugin() {
		return plugin(
				groupId("org.codehaus.mojo"),
				artifactId("exec-maven-plugin"),
				version("1.3.2")
		);
	}

	protected Xpp3Dom getBundleConfiguration(List<Element> argList) {
		MojoExecutor.Element[] argArr = new MojoExecutor.Element[argList.size()];
		argArr = argList.toArray(argArr);

		return configuration(
				//element(name("environmentVariables"),
				//		element(name("GEM_HOME"), gemHome.getPath()),
				//		element(name("GEM_PATH"), gemPath.getPath())),
				element(name("executable"), "bundle"),
				element(name("workingDirectory"), mmRoot),
				element(name("arguments"), argArr)
		);
	}

	protected List<Element> getEmptyArguments() {
		final List<Element> argList = new ArrayList<Element>();
		return argList;
	}

}
