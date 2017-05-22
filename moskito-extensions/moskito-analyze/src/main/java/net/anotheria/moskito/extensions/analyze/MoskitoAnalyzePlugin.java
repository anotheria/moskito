package net.anotheria.moskito.extensions.analyze;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.extensions.analyze.config.MoskitoAnalyzeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MoSKito Analyze plugin.
 *
 * @author esmakula
 */
public class MoskitoAnalyzePlugin extends AbstractMoskitoPlugin {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(MoskitoAnalyzePlugin.class);

    @Override
    public void initialize() {
        MoskitoAnalyzeConfig.getInstance();
    }

    @Override
    public void deInitialize() {
    }
}
