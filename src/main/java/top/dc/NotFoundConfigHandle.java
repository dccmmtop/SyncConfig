package top.dc;

/**
 * @author dc on 2023/4/16
 */
public class NotFoundConfigHandle extends RuntimeException {
    public NotFoundConfigHandle(String msg) {
        super();
        System.out.println(msg);
    }
}
