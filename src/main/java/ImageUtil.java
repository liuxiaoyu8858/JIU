import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author liuxiaoyu
 * @Date 17/3/28.
 */
public class ImageUtil {
    /**
     * @desc 给定矩形框,将字符串垂直和水平都居中换行
     * @param width
     * @param height
     * @param fontHeight
     * @param word
     * @param fontTye
     * @param font
     * @param color
     * @param isCenter
     * @return
     */
    public static BufferedImage drawChineseFontPic(int width, int height, Integer fontHeight, String word, String fontTye, int font, Color color, Boolean isCenter) {
        try {
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();

            buffImg = gd.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            gd = buffImg.createGraphics();

            Font font1 = new Font(fontTye, font, fontHeight);
            gd.setFont(font1);

            if (color == null) {
                gd.setColor(Color.white);
            } else {
                gd.setColor(color);
            }
            // 锯齿
            gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (isCenter != null && isCenter) {

                int strWidth = gd.getFontMetrics().stringWidth(word);

                StringBuffer sb = new StringBuffer(word);
                if (strWidth <= width) {
                    gd.drawString(sb.toString(), (width - strWidth) / 2, height / 2 + fontHeight / 2);
                } else {

                    int lineNumber = strWidth % width == 0 ? strWidth / width : strWidth / width + 1;
                    int lineBeginWordPositionInString = 0;
                    int lineEndWordPositionInString = 0;

                    for (int i = 1; i <= lineNumber; i++) {
                        int linePacing = 0;
                        if (i > 1) {
                            linePacing = fontHeight * 3 / 10;
                        }
                        while (gd.getFontMetrics().stringWidth(sb.substring(lineBeginWordPositionInString, lineEndWordPositionInString)) < width) {
                            lineEndWordPositionInString++;
                            if (lineEndWordPositionInString >= sb.length()) {
                                lineEndWordPositionInString = sb.length();
                                break;
                            }

                        }
                        if (gd.getFontMetrics().stringWidth(sb.substring(lineBeginWordPositionInString, lineEndWordPositionInString)) > width) {
                            lineEndWordPositionInString--;
                        }

                        String lintString = sb.substring(lineBeginWordPositionInString, lineEndWordPositionInString);
                        int lineLengthCur = gd.getFontMetrics().stringWidth(lintString);
                        gd.drawString(lintString, (width - lineLengthCur) / 2, fontHeight * i + linePacing * (i - 1));
                        lineBeginWordPositionInString = lineEndWordPositionInString;
                    }
                }
            } else {
                gd.drawString(word, 0, fontHeight);
            }
            gd.dispose();
            return buffImg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
