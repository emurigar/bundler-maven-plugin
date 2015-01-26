package com.byclosure.maven.plugins.middleman;

import de.saumya.mojo.gem.AbstractGemMojo;
import de.saumya.mojo.ruby.gems.GemException;
import de.saumya.mojo.ruby.script.Script;
import de.saumya.mojo.ruby.script.ScriptException;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.IOException;

public abstract class AbstractMiddlemanMojo extends AbstractGemMojo {
    /** @parameter default-value="${project.basedir}/src/main/webapp/Gemfile" expression="${middleman.bundle_file}" */
    protected String bundleFileLocation;

    /** @parameter default-value="${project.basedir}/src/main/webapp/" expression="${middleman.root}" */
    protected String mmRoot;

    /** @parameter default-value="development" expression="${middleman.env}" */
    protected String mmEnv;

    public abstract void executeMiddleman() throws MojoExecutionException,
            ScriptException, IOException, GemException;

    @Override
    public void executeWithGems() throws MojoExecutionException,
            ScriptException, IOException, GemException {

        this.factory.addEnv("BUNDLE_GEMFILE", bundleFileLocation);

        final Script biScript = factory.newScriptFromSearchPath("bundle");
        biScript.addArgs("install");
        biScript.executeIn(launchDirectory());

        this.factory.addEnv("MM_ROOT", mmRoot);
        this.factory.addEnv("MM_ENV", mmEnv);

        executeMiddleman();
    }

}
