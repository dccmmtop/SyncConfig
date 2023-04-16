package top.dc;

import org.tinylog.Logger;

/**
 * @author dc on 2023/4/16
 */
public class NotFoundConfigHandle extends RuntimeException {
    public NotFoundConfigHandle(String msg) {
        super();
        Logger.error(msg);
    }
}
