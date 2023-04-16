package top.dc.handle;

/**
 * @author dc on 2023/4/16
 */
public interface ConfigHandle {
    /**
     * 是否选择本类来处理变动的文件
     * @param path 变动的文件的路径
     * @return bool
     */
    Boolean select(String path);

    /**
     * 当文件发生变动时，要执行的动作
     * @param path 变动文件的绝对路径
     */
    void deal(String path);
}
