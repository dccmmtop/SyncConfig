package top.dc.handle;

import org.apache.commons.io.FileUtils;
import org.tinylog.Logger;
import top.dc.Backup;
import top.dc.Config;

import java.io.File;

/**
 * @author dc on 2023/4/16
 */
public class NeoVimConfigHandleImpl implements ConfigHandle {
    private Backup backup;
    @Override
    public Boolean select(String path) {
        return path.contains("\\nvim") || path.contains("/nvim");
    }

    @Override
    public void deal(String path) {
        try {
            Config config = Config.getInstance();
            for (Backup backup : config.getBackupList()) {
                Logger.debug("backupFrom: {}", backup.getFrom());
                Logger.debug("path: {}", path);
                if(path.startsWith(backup.getFrom())){
                    this.backup = backup;
                    break;
                }
            }
            if(backup == null){
                Logger.info("未找到监听的neovim目录");
                return;
            }

            FileUtils.copyDirectory(new File(backup.getFrom()), new File(backup.getTo()));
            Logger.info("neovim 配置备份完成");
        } catch (Exception e) {
            Logger.info("neovim 配置备份失败: {}", e);
        }
    }
}
