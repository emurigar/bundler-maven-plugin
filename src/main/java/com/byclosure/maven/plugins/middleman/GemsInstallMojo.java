package com.byclosure.maven.plugins.middleman;

import de.saumya.mojo.gem.AbstractGemMojo;
import de.saumya.mojo.ruby.gems.GemException;
import de.saumya.mojo.ruby.script.Script;
import de.saumya.mojo.ruby.script.ScriptException;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.IOException;

/**
 * @goal gems-install
 * @requiresProject true
 */
public class GemsInstallMojo extends AbstractGemMojo {

    /** @parameter default-value="${project.basedir}/src/main/webapp/Gemfile" expression="${middleman.bundle_file}" */
    protected String bundleFileLocation;

    @Override
    public void executeWithGems() throws MojoExecutionException,
            ScriptException, IOException, GemException {

        this.factory.addEnv("BUNDLE_GEMFILE", bundleFileLocation);

        final Script buScript = factory.newScriptFromSearchPath("bundle");
        buScript.addArgs("install");
        buScript.executeIn(launchDirectory());
    }
}