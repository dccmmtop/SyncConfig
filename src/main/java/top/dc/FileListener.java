package top.dc;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

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
//        System.out.println("onStart");
    }

    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("新建：" + directory.getAbsolutePath());
        deal(directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("修改：" + directory.getAbsolutePath());
        deal(directory.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("删除：" + directory.getAbsolutePath());
        deal(directory.getAbsolutePath());
    }

    @Override
    public void onFileCreate(File file) {
//        String compressedPath = file.getAbsolutePath();
//        System.out.println("新建：" + compressedPath);
//        if (file.canRead()) {
//            // TODO 读取或重新加载文件内容
//            System.out.println("文件变更，进行处理");
//        }
    }

    @Override
    public void onFileChange(File file) {
        String compressedPath = file.getAbsolutePath();
        System.out.println("修改：" + compressedPath);
        deal(file.getAbsolutePath());
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("删除：" + file.getAbsolutePath());
    }

    @Override
    public void onStop(FileAlterationObserver observer) {
        super.onStop(observer);
//        System.out.println("onStop");
    }

    private void deal(String path) {
        try {
            config.find(path).deal(path);
        } catch (NotFoundConfigHandle e) {
            System.out.println(e.getMessage());
        }
    }
}
