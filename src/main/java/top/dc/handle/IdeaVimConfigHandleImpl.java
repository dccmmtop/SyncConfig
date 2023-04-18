package top.dc.handle;

import org.apache.commons.io.FileUtils;
import org.tinylog.Logger;
import top.dc.Backup;
import top.dc.Config;

import java.io.File;

/**
 * @author dc on 2023/4/16
 */
public class IdeaVimConfigHandleImpl implements ConfigHandle {
    private Backup backup;
    @Override
    public Boolean select(String path) {
        return path.endsWith(".ideavimrc");
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
                Logger.info("未找到监听的ideavim目录");
                return;
            }

            FileUtils.copyFile(new File(path), new File(backup.getTo()));
            Logger.info("ideavimrc 备份完成");
        } catch (Exception e) {
            Logger.info("ideavimrc 备份失败: " + e.getMessage());
        }
    }

}
