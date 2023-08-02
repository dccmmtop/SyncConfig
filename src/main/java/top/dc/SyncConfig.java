package top.dc;

import org.tinylog.Logger;

/**
 * @author dc on 2023/4/16
 */
public class SyncConfig {

    public static void main(String[] args) throws Exception, NotFoundEnvArg {
        Logger.info("启动...");
        Config  config = Config.loadConfig();
        config.scheduledCloudBackup();
        FileMonitor fileMonitor = new FileMonitor(1000);
        for (String filePath : config.getAbFilePathList()) {
            fileMonitor.monitor(filePath, new FileListener(config));
        }
        fileMonitor.start();
    }
}
