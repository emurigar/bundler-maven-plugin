package com.byclosure.maven.plugins.middleman;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractJRubyMojo extends AbstractMojo {

	/**
	 * @parameter default-value="${project.basedir}" expression="${bundler.working_dir}"
	 */
	protected String working_dir;

	/**
	 * @parameter default-value="https://s3.amazonaws.com/jruby.org/downloads/1.7.19/jruby-bin-1.7.19.tar.gz" expression="${bundler.jruby_download_location}"
	 */
	protected String jruby_download_location;

	/**
	 * @parameter default-value="${project.basedir}/vendor" expression="${bundler.vendor}"
	 */
	protected String vendor;

	/**
	 * @parameter default-value="${project.basedir}/vendor/jruby-1.7.19/bin" expression="${bundler.jruby_home}"
	 */
	protected String jruby_bin;

	/**
	 * @parameter default-value="${project.basedir}/vendor/gem_home" expression="${bundler.gem_home}"
	 */
	protected String gem_home;

	/**
	 * @parameter default-value="${project.basedir}/vendor/gem_home" expression="${bundler.gem_home}"
	 */
	protected String gem_path;

	/**
	 * @parameter default-value="${project.basedir}/vendor/bin" expression="${bundler.binstubs}"
	 */
	protected String binstubs;

	/**
	 * @parameter default-value="1.8.2" expression="${bundler.bundler_version}"
	 */
	protected String bundler_version;

	protected abstract void executeComand() throws IOException;

	@Override
	public void execute() throws MojoExecutionException {
		try {
			extractJRubyToVendor();
			ensureBundlerInstalled();
			executeComand();
		} catch (IOException e) {
			throw new MojoExecutionException(e.getMessage());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}


	protected void extractJRubyToVendor() throws IOException, URISyntaxException, MojoExecutionException {
		final File vendorDir = new File(vendor);
		vendorDir.mkdirs();

		final File jrubyToDownload = new File(jruby_download_location);
		final File jrubydownloadedFile = new File(vendorDir, jrubyToDownload.getName());

		if (!jrubydownloadedFile.exists()) {

			System.out.println("Downloading: " + jruby_download_location);
			final URL jrubyUrl = new URL(jruby_download_location);

			final ReadableByteChannel rbc = Channels.newChannel(jrubyUrl.openStream());
			final FileOutputStream fos = new FileOutputStream(jrubydownloadedFile);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

			Decompressor.decompress(jrubydownloadedFile.getPath(), vendorDir.getPath() + "/");
		}
	}

	protected void ensureBundlerInstalled() throws IOException {
		final File gemHomeDir = new File(gem_home);
		gemHomeDir.mkdirs();

		final File bundleFile = new File(gem_home, new File("bin", "bundle").getPath());

		if (!bundleFile.exists()) {
			installBundler();
		}
	}

	private void installBundler() throws IOException {

		final Map<String, String> env = new HashMap<String, String>(System.getenv());
		env.put("GEM_HOME", gem_home);
		env.put("GEM_PATH", gem_home);

		final CommandLine cmd = getCrossPlatformCommandLine(new File(jruby_bin, "jruby").getPath());
		cmd.addArgument("-S");
		cmd.addArgument(new File(jruby_bin, "gem").getPath());
		cmd.addArgument("install");
		cmd.addArgument("bundler");
		cmd.addArgument("-v " + bundler_version, false);
		cmd.addArgument("--no-rdoc");
		cmd.addArgument("--no-ri");

		executeCommandLine(cmd, env, new File(working_dir));
	}


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

	protected void executeCommandLine(CommandLine cmdLine, Map<String, String> env, File workingDir) throws IOException {
		DefaultExecutor executor = new DefaultExecutor();
		executor.setWorkingDirectory(workingDir);

		PumpStreamHandler pump = new PumpStreamHandler(System.out, System.err, System.in);
		executor.setStreamHandler(pump);

		executor.execute(cmdLine, env);
	}

}
