package top.dc;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileFilter;

/**
 * @author dc on 2023/4/16
 */
public class FileMonitor {

    private final FileAlterationMonitor monitor;

    public FileMonitor(long interval) {
        monitor = new FileAlterationMonitor(interval);
    }

    /**
     * 给文件添加监听
     *
     * @param path     文件路径
     * @param listener 文件监听器
     */
    public void monitor(String path, FileAlterationListener listener) {
        FileAlterationObserver observer;
        File file = new File(path);
        if(FileUtils.isDirectory(file)){
            observer = new FileAlterationObserver(new File(path));
        }else{

            File parentFile = file.getParentFile();
            FileFilter fileFilter = new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getAbsolutePath().equals(path);
                }
            };
            observer = new FileAlterationObserver(parentFile,fileFilter);
        }
        monitor.addObserver(observer);
        observer.addListener(listener);
    }

    public void stop() throws Exception {
        monitor.stop();
    }

    public void start() throws Exception {
        monitor.start();

    }
}