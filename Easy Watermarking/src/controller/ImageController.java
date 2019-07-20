package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageController {

	public ImageController(){

	}

	public ImageController(String originalImagePath, String waterImagePath) throws IOException {
		File dir = new File(this.removeFile(originalImagePath) + "Watermarked");
		dir.mkdir();
		System.out.println(dir.getAbsolutePath());
		BufferedImage img1 = ImageIO.read(new File(originalImagePath.toString()));
		BufferedImage img2 = ImageIO.read(new File(waterImagePath.toString()));

		BufferedImage img2Resized = ImageController.resize(img2, img1.getWidth(), img1.getHeight());
		BufferedImage joinedImg = ImageController.joinBufferedImage(img1, img2Resized);

		ImageIO.write(joinedImg, "png",
				new File((dir.getAbsolutePath() + "/" + this.getFileName(originalImagePath) + "." + "png").toString()));
		img1.flush();
		img2.flush();
		img2Resized.flush();
		joinedImg.flush();


	}

	private static BufferedImage resize(BufferedImage img, int width, int height) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	private static BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {
		BufferedImage newImage = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, 0, 0);
		g2.dispose();
		return newImage;
	}



	public String removeFile(String path) {
		int pos2 = path.lastIndexOf("/");
		return path.substring(0, pos2 + 1);

	}

	private String getFileName(String path) {
		int pos = path.lastIndexOf(".");
		int pos2 = path.lastIndexOf("/");

		return path.substring(pos2 + 1, pos);
	}


}
