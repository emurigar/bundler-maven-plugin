package com.byclosure.maven.plugins.middleman;

import de.saumya.mojo.ruby.gems.GemException;
import de.saumya.mojo.ruby.script.Script;
import de.saumya.mojo.ruby.script.ScriptException;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.IOException;

/**
 * @goal server
 * @requiresProject true
 */
public class ServerMojo extends AbstractMiddlemanMojo {

    @Override
    public void executeMiddleman() throws MojoExecutionException, ScriptException, IOException, GemException {
        final Script script = factory.newScriptFromSearchPath("middleman");
        script.addArg("server");
        script.executeIn(launchDirectory());
    }

}