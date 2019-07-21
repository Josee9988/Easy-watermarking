package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class ImageController {
	private static ArrayList<String> paths = new ArrayList<String>();

	public ImageController(){
	}

	public ImageController(String originalImagePath, String waterImagePath) throws IOException {
		File dir = new File(this.removeFile(originalImagePath) + "Watermarked");
		dir.mkdir();
		BufferedImage img1 = ImageIO.read(new File(originalImagePath.toString()));
		BufferedImage img2 = ImageIO.read(new File(waterImagePath.toString()));

		BufferedImage img2Resized = ImageController.resize(img2, img1.getWidth(), img1.getHeight());
		BufferedImage joinedImg = ImageController.joinBufferedImage(img1, img2Resized);
		String path2File = "";
		if (System.getProperty("os.name").toLowerCase().indexOf("win") > 0) {
			path2File = dir.getAbsolutePath() + "\\" + this.getFileName(originalImagePath) + "." + "png".toString();
		} else {
			path2File = dir.getAbsolutePath() + "/" + this.getFileName(originalImagePath) + "." + "png".toString();
		}
		ImageController.paths.add(path2File);
		ImageIO.write(joinedImg, "png",
				new File((path2File)));
		img1.flush();
		img2.flush();
		img2Resized.flush();
		joinedImg.flush();
	}

	public ArrayList<String> CheckDifferentFolder() {
		ArrayList<String> pathsORpathS = new ArrayList<String>();
		pathsORpathS.add(this.removeFile(ImageController.paths.get(0))); // we add atleast the first
		for (String i : ImageController.paths) {
			for (String j : ImageController.paths) {
				if (!(this.removeFile(i).equals(this.removeFile(j)))) {
					pathsORpathS.add(this.removeFile(j));
					break;
				}
			}
		}
		// Removing duplicates
		Set<String> set = new HashSet<>(pathsORpathS);
		pathsORpathS.clear();
		pathsORpathS.addAll(set);

		return pathsORpathS;
	}

	public void clearPaths() {
		ImageController.paths.clear();
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


	private String removeFile(String path) {
		int pos2 = path.lastIndexOf("/");
		return path.substring(0, pos2 + 1);

	}

	private String getFileName(String path) {
		int pos = path.lastIndexOf(".");
		int pos2 = path.lastIndexOf("/");

		return path.substring(pos2 + 1, pos);
	}


}
