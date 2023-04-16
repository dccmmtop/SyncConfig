package top.dc.handle;

import org.apache.commons.io.FileUtils;
import org.tinylog.Logger;
import top.dc.Config;

import java.io.File;

/**
 * @author dc on 2023/4/16
 */
public class NeoVimConfigHandleImpl implements ConfigHandle {
    @Override
    public Boolean select(String path) {
        return path.contains("\\nvim") || path.contains("/nvim");
    }

    @Override
    public void deal(String path) {
        try {
            Config config = Config.getInstance();
            String bakDir = config.getConfigBakPath();
            String nvimPath = "";
            for (String listenPath : config.getAbFilePathList()) {
                if(this.select(listenPath)){
                    nvimPath = listenPath;
                    break;
                }
            }
            if("".equals(nvimPath)){
                Logger.info("未找到监听的neovim目录");
                return;
            }

            FileUtils.copyDirectory(new File(nvimPath), new File(bakDir + File.separator + "nvim"));
            Logger.info("neovim 配置备份完成");
        } catch (Exception e) {
            Logger.info("neovim 配置备份失败: " + e.getMessage());
        }
    }

}
