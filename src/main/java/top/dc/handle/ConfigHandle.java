package top.dc.handle;

/**
 * @author dc on 2023/4/16
 */
public interface ConfigHandle {
    Boolean select(String path);
    void deal(String path);
}
