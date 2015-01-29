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

    /** @parameter default-value="false" expression="${middleman.force_polling}" */
    protected boolean forcePolling;

    /** @parameter default-value="0.25" expression="${middleman.latency}" */
    protected float latency;

    @Override
    public void executeMiddleman() throws MojoExecutionException, ScriptException, IOException, GemException {
        final Script script = factory.newScriptFromSearchPath("middleman");
        script.addArg("server");

        if (forcePolling) {
            script.addArg("--force-polling");
        }

        if (latency != 0.25) {
            script.addArg("--latency=" + latency);
        }

        script.executeIn(launchDirectory());
    }

}