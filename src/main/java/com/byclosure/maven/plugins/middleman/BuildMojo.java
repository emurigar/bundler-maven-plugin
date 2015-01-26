package com.byclosure.maven.plugins.middleman;

import de.saumya.mojo.ruby.gems.GemException;
import de.saumya.mojo.ruby.script.Script;
import de.saumya.mojo.ruby.script.ScriptException;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.IOException;

/**
 * @goal build
 * @phase compile
 * @requiresProject true
 */
public class BuildMojo extends AbstractMiddlemanMojo {
    @Override
    public void executeMiddleman() throws MojoExecutionException, ScriptException, IOException, GemException {
        final Script script = factory.newScriptFromSearchPath("middleman");
        script.addArg("build");
        script.executeIn(launchDirectory());
    }
}