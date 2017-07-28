package net.anotheria.moskito.extensions.analyze.config;


import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.slf4j.LoggerFactory;

/**
 * Config for MoSKitoAnalyze plugin.
 *
 * @author esmakula
 */
@ConfigureMe(name = "moskito-analyze-plugin")
public class MoskitoAnalyzeConfig {

    @Configure
    private String url;

    @Configure
    private String[] hosts;

    @Configure
    private AnalyzeChart[] charts;

    /**
     * Monitor object.
     */
    private static final Object monitor = new Object();

    /**
     * {@link MoskitoAnalyzeConfig} instance
     */
    private static MoskitoAnalyzeConfig instance;

    public static MoskitoAnalyzeConfig getInstance() {
        if (instance != null)
            return instance;
        synchronized (monitor) {
            if (instance != null)
                return instance;
            instance = new MoskitoAnalyzeConfig();
            try {
                ConfigurationManager.INSTANCE.configure(instance);
            } catch (Exception e) {
                LoggerFactory.getLogger(MoskitoAnalyzeConfig.class).warn("Configuration failed. Relying on defaults. " + e.getMessage());
            }
            return instance;
        }
    }

    private MoskitoAnalyzeConfig(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getHosts() {
        return hosts;
    }

    public void setHosts(String[] hosts) {
        this.hosts = hosts;
    }

    public AnalyzeChart[] getCharts() {
        return charts;
    }

    public void setCharts(AnalyzeChart[] charts) {
        this.charts = charts;
    }

}
