package top.dc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * 把转入的图片资源设置到剪切板上
     */
    //类名.setImageClipboard(img);  //给剪切板设置图片型内容
    public static void setImage(String imgPath) throws IOException {
        File pathToFile = new File(imgPath);
        Image image = ImageIO.read(pathToFile);
        Images imgSel = new Images(image);
        //设置
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);
    }

    public static void main(String[] args) throws IOException {
        setImage("C:\\Users\\dccmm\\code\\go\\gif_to_clip\\1.gif");
    }

}
 class Images implements Transferable {
    private Image image; //得到图片或者图片流
    public Images(Image image) {this.image = image;}

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if (!DataFlavor.imageFlavor.equals(flavor)) {throw new UnsupportedFlavorException(flavor);}
        return image;
    }
}
