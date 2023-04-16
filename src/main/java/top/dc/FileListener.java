package top.dc;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.tinylog.Logger;

import java.io.File;

/**
 * @author dc on 2023/4/16
 */
public class FileListener extends FileAlterationListenerAdaptor {

    private final Config config;

    public FileListener(Config config) {
        this.config = config;
    }

    @Override
    public void onStart(FileAlterationObserver observer) {
        super.onStart(observer);
//        Logger.info("onStart");
    }

    @Override
    public void onDirectoryCreate(File directory) {
        Logger.info("新建：" + directory.getAbsolutePath());
        deal(directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File directory) {
        Logger.info("修改：" + directory.getAbsolutePath());
        deal(directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        Logger.info("删除：" + directory.getAbsolutePath());
        deal(directory.getAbsolutePath());
    }

    @Override
    public void onFileCreate(File file) {
//        String compressedPath = file.getAbsolutePath();
//        Logger.info("新建：" + compressedPath);
//        if (file.canRead()) {
//            // TODO 读取或重新加载文件内容
//            Logger.info("文件变更，进行处理");
//        }
    }

    @Override
    public void onFileChange(File file) {
        String compressedPath = file.getAbsolutePath();
        Logger.info("修改：" + compressedPath);
        deal(file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file) {
        Logger.info("删除：" + file.getAbsolutePath());
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
//        Logger.info("onStop");
    }

    private void deal(String path) {
        try {
            config.find(path).deal(path);
        } catch (NotFoundConfigHandle e) {
            Logger.info(e.getMessage());
        }
    }
}
