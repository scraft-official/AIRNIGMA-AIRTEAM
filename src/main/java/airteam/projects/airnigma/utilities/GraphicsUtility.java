package airteam.projects.airnigma.utilities;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GraphicsUtility {
	public static void setGradientPaint(Graphics2D g, Color startColor, Color endColor, int w, int h) {
		g.setPaint(new GradientPaint(0, 0, startColor, w, h, endColor));
	}
	
	public static void drawFadedBorder(Graphics2D g, Color shadowColor, int shadowSize, int x, int y, int w, int h) {
		for (int i = 0; i < shadowSize; i++) {
      g.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), ((80 / shadowSize) * i)));
      g.drawRect(x+i+1, y+i+1, w - ((i * 2) + 1), h - ((i * 2) + 1));
		}
	}
	
	public static void drawRoundFadedBorder(Graphics2D g, Color shadowColor, int shadowSize, int x, int y, int w, int h, int borderRadius) {
		for (int i = 0; i < shadowSize; i++) {
      g.setColor(new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), ((80 / shadowSize) * i)));
      g.drawRoundRect(x+i+1, y+i+1, w - ((i * 2) + 1), h - ((i * 2) + 1), borderRadius, borderRadius);
		}
	}
	
	public static BufferedImage getScaledImage(BufferedImage image, double scale) {
		BufferedImage tmpImage = new BufferedImage((int) Math.round(image.getWidth() * scale), (int) Math.round(image.getHeight() * scale), 2);
		tmpImage.getGraphics().drawImage(image.getScaledInstance((int) Math.round(image.getWidth() * scale), (int) Math.round(image.getHeight() * scale), Image.SCALE_SMOOTH), 0, 0, null);
		tmpImage.getGraphics().dispose();
		return tmpImage;
	}
	
	public static Image getSizedImage(BufferedImage image, int width, int height) {
		final Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		final BufferedImage resized = new BufferedImage(width, height, 2);
		final Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(temp, 0, 0, null);
		g2d.dispose();
		return resized;
	}
	
	public static BufferedImage getInternalIcon(String imagePath) {
		try{
			return ImageIO.read(GraphicsUtility.class.getClassLoader().getResource(imagePath));
	  } catch(Exception e) { LogUtility.logError("Wystapil blad przy pobieraniu ikonki: " + e.getMessage(), "Path do ikonki: " + "/airteam/projects/atarilogo/resources/" + imagePath); }
		
		LogUtility.logError("Zwrocono pustwa ikonke! ( /airteam/projects/atarilogo/resources/" + imagePath + " )");
		return null;
	}
	
	public static BufferedImage getTintedImage(BufferedImage img, Color color) {
		if(color.getAlpha() == 0) return img;
		return getTintedImage(img, color.getRed(), color.getGreen(), color.getBlue());
	}
	public static BufferedImage getTintedImage(BufferedImage img, int red, int green, int blue) {
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				Color pixelColor = new Color(img.getRGB(x, y), true);
        int r = (pixelColor.getRed() + red) / 2;
        int g = (pixelColor.getGreen() + green) / 2;
        int b = (pixelColor.getBlue() + blue) / 2;
        int a = pixelColor.getAlpha();
        int rgba = (a << 24) | (r << 16) | (g << 8) | b;
        img.setRGB(x, y, rgba);
			}
		}
		return img;
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    return bimage;
	}
	
}
