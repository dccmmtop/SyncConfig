package top.dc.handle;

import org.apache.commons.io.FileUtils;
import top.dc.Config;

import java.io.File;

/**
 * @author dc on 2023/4/16
 */
public class LsdConfigHandleImpl implements ConfigHandle {
    @Override
    public Boolean select(String path) {
        return path.endsWith("lsd\\config.yaml");
    }

    @Override
    public void deal(String path) {
        try {
            Config config = Config.getInstance();
            String bakDir = config.getConfigBakPath();
            FileUtils.copyFile(new File(path), new File(bakDir + File.separator + "lsd_config.yaml_bak"));
            System.out.println("lsd config.yaml 备份完成");
        } catch (Exception e) {
            System.out.println("lsd config.yaml 备份失败: " + e.getMessage());
        }
    }

}
