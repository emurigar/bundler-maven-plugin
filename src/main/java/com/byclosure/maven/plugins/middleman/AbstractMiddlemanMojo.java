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
import java.nio.file.Path;
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
	 * @parameter default-value="~/.m2/repository/org/jruby/jruby-complete/1.7.4/jruby-complete-1.7.4.jar" expression="${middleman.jruby_complete_path}"
	 */
	protected String jrubyCompletePath;

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

	/**
	 * @parameter default-value="-Xmx500m" expression="${middleman.Xmx}"
	 */
	protected String Xmx;

	public abstract void executeMiddleman() throws MojoExecutionException;

	@Override
	public void execute() throws MojoExecutionException {

		installBundler();
		doBundleInstall();

		executeMiddleman();
	}

	protected void installBundler() throws MojoExecutionException {
		final List<Element> argList = getJRubyCompleteArguments();

		argList.add(element(name("argument"), "gem"));
		argList.add(element(name("argument"), "install"));
		argList.add(element(name("argument"), "bundler"));

		executeMojo(
				getExecMavenPlugin(),
				goal("exec"),
				getConfiguration(argList),
				getEnv()
		);
	}

	protected void doBundleInstall() throws MojoExecutionException {
		final List<Element> argList = getJRubyCompleteArguments();

		argList.add(element(name("argument"), "bundle"));
		argList.add(element(name("argument"), "install"));

		executeMojo(
				getExecMavenPlugin(),
				goal("exec"),
				getConfiguration(argList),
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

	protected Xpp3Dom getConfiguration(List<Element> argList) {
		MojoExecutor.Element[] argArr = new MojoExecutor.Element[argList.size()];
		argArr = argList.toArray(argArr);

		return configuration(
				element(name("environmentVariables"),
						element(name("GEM_HOME"), gemHome.getPath()),
						element(name("GEM_PATH"), gemPath.getPath())),
				element(name("executable"), "java"),
				element(name("workingDirectory"), mmRoot),
				element(name("arguments"), argArr)
		);
	}

	protected List<Element> getJRubyCompleteArguments() {
		final String replacedJrubyCompletePath;

		if (jrubyCompletePath.contains("~")) {
			final File file = new File(System.getProperty("user.home"));
			replacedJrubyCompletePath = new File(file, jrubyCompletePath.replace("~", "")).getPath();
		} else {
			replacedJrubyCompletePath = jrubyCompletePath;
		}

		final List<Element> argList = new ArrayList<Element>();
		argList.add(element(name("argument"), Xmx));
		argList.add(element(name("argument"), "-Xss1024k"));
		argList.add(element(name("argument"), "-jar"));
		argList.add(element(name("argument"), replacedJrubyCompletePath));
		argList.add(element(name("argument"), "-S"));

		return argList;
	}

}
