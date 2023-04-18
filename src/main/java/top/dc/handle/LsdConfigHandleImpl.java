package top.dc.handle;

import org.apache.commons.io.FileUtils;
import org.tinylog.Logger;
import top.dc.Backup;
import top.dc.Config;

import java.io.File;

/**
 * @author dc on 2023/4/16
 */
public class LsdConfigHandleImpl implements ConfigHandle {
    private Backup backup;
    @Override
    public Boolean select(String path) {
        return path.endsWith("lsd\\config.yaml");
    }

    @Override
    public void deal(String path) {
        try {

            for (Backup backup : Config.getInstance().getBackupList()) {
                Logger.debug("backupFrom: {}", backup.getFrom());
                Logger.debug("path: {}", path);
                if(path.startsWith(backup.getFrom())){
                    this.backup = backup;
                    break;
                }
            }
            if(backup == null){
                Logger.info("未找到监听的lsd目录");
                return;
            }
            FileUtils.copyFileToDirectory(new File(path), new File(backup.getTo()));
            Logger.info("lsd config.yaml 备份完成");
        } catch (Exception e) {
            Logger.info("lsd config.yaml 备份失败: " + e.getMessage());
        }
    }
}
