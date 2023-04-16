package top.dc.handle;

import org.apache.commons.io.FileUtils;
import top.dc.Config;
import top.dc.NotFoundEnvArg;

import java.io.File;
import java.io.IOException;

/**
 * @author dc on 2023/4/16
 */
public class IdeaVimConfigHandleImpl implements ConfigHandle {
    @Override
    public Boolean select(String path) {
        return path.endsWith(".ideavimrc");
    }

    @Override
    public void deal(String path) {
        try {
            Config config = Config.getInstance();
            String bakDir = config.getConfigBakPath();
            FileUtils.copyFile(new File(path), new File(bakDir + File.separator + "ideavimrc_bak"));
            System.out.println("ideavimrc 备份完成");
        } catch (Exception e) {
            System.out.println("ideavimrc 备份失败: " + e.getMessage());
        }
    }

}
