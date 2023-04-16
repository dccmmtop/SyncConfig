package top.dc;

/**
 * @author dc on 2023/4/16
 */
public class SyncConfig {

    public static void main(String[] args) throws Exception, NotFoundEnvArg {
        Config  config = Config.loadConfig();
        FileMonitor fileMonitor = new FileMonitor(1000);
        for (String filePath : config.getAbFilePathList()) {
            fileMonitor.monitor(filePath, new FileListener(config));
        }
        fileMonitor.start();
    }
}
