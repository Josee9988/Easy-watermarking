/**
 * @author Jose_Gracia_Berenguer
 * @version Jul 21, 2019
 * @param args Receives the arguments of the program.
 */
package controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;

/**
 * ImageController class that resizes and joins the two images.
 *
 * @author Jose Gracia
 *
 */
public class ImageController implements Runnable {
	private static ArrayList<String> paths;
	private static String OS;
	private static String waterImagePath;
	private static ObservableList<Label> originalImagePath;
	private int turn;
	private static ArrayList<String> pathsORpathS = new ArrayList<String>();
	private BufferedImage img1;
	private BufferedImage img2;
	private BufferedImage img2Resized;
	private BufferedImage joinedImg;
	private static Object object = new Object();


	// Default constructor
	public ImageController() {
		ImageController.paths = new ArrayList<String>();
	}

	// Default constructor
	public ImageController(int turn) {
		this.turn = turn;
	}

	/**
	 * ImageController constructor used for creating the threads and calling
	 * imageParse which is the one that does all the work for creating the new image
	 *
	 * @param OS             the name of the operating system
	 * @param items          all the labels with the text
	 * @param waterImagePath the path of the watermark
	 */
	public ImageController(String OS, ObservableList<Label> items, String waterImagePath) {
		ImageController.OS = OS;
		ImageController.originalImagePath = items;
		ImageController.waterImagePath = waterImagePath;
		ImageController.paths = new ArrayList<String>();
		ExecutorService pool = Executors.newFixedThreadPool(items.size());

		for (int i = 0; i < ImageController.originalImagePath.size(); i++) {
			Runnable runnable = new ImageController(i);
			pool.execute(runnable);
		}

		pool.shutdown();
		while (!pool.isTerminated())
			;

	}

	@Override
	public void run() {
		try {
			this.imageParse();
		} catch (IOException e) {
			System.out.println(e.toString());
		}

	}


	/**
	 * imageParse principal method that calls the necessary methods to resize the
	 * image and join them in only one. It also saves and creates the folder
	 *
	 * @param originalImagePath receives a String with the path of the image to
	 *                          watermark
	 * @throws IOException if there has been an exception while performing the task
	 *                     of the image
	 */
	public void imageParse() throws IOException {
		File dir = new File(
				this.removeFile(ImageController.originalImagePath.get(this.turn).getText()) + "Watermarked");
		dir.mkdir();
		synchronized (ImageController.object) {
			this.img1 = ImageIO.read(new File(ImageController.originalImagePath.get(this.turn).getText()));
		}
		this.img2 = ImageIO.read(new File(ImageController.waterImagePath));

		this.img2Resized = this.resize(this.img2, this.img1.getWidth(), this.img1.getHeight());
		this.joinedImg = this.joinBufferedImage(this.img1, this.img2Resized);
		String path2File = "";
		if (System.getProperty("os.name").toLowerCase().indexOf("win") > 0) {
			path2File = dir.getAbsolutePath() + "\\"
					+ this.getFileName(ImageController.originalImagePath.get(this.turn).getText()) + "."
					+ "png".toString();
		} else {
			path2File = dir.getAbsolutePath() + "/"
					+ this.getFileName(ImageController.originalImagePath.get(this.turn).getText()) + "."
					+ "png".toString();
		}
		ImageController.paths.add(path2File.toString());
		ImageIO.write(this.joinedImg, "png", new File((path2File)));
		this.img1.flush();
		this.img2.flush();
		this.img2Resized.flush();
		this.joinedImg.flush();
	}



	/**
	 * CheckDifferentFolder checks the different save paths that and return them
	 * into an arraylist
	 *
	 * @return ArrayList<String> with the paths of the folders (without filenames)
	 */
	public ArrayList<String> CheckDifferentFolder() {
		ImageController.pathsORpathS.add(this.removeFile(ImageController.paths.get(0))); // we add atleast the first
		for (String i : ImageController.paths) {
			for (String j : ImageController.paths) {
				if (!(this.removeFile(i).equals(this.removeFile(j)))) {
					ImageController.pathsORpathS.add(this.removeFile(j));
					break;
				}
			}
		}
		// Removing duplicates
		Set<String> set = new HashSet<>(ImageController.pathsORpathS);
		ImageController.pathsORpathS.clear();
		ImageController.pathsORpathS.addAll(set);

		return ImageController.pathsORpathS;
	}

	/**
	 * clearPaths clears all the ArrayList of paths
	 */
	public void clearPaths() {
		ImageController.paths.clear();
	}

	/**
	 * resize resice the watermark to get the width and height of the original image
	 * to perform the watermark
	 *
	 * @param img    the image of the watermark
	 * @param width  the width of the image to perform the watermark
	 * @param height the height of the image to perform the watermark
	 * @return BufferedImage with the image resized
	 */
	private BufferedImage resize(BufferedImage img, int width, int height) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	/**
	 * joinBufferedImage joins the two images, the img2 above the img1
	 *
	 * @param img1 Image to perform the watermark
	 * @param img2 Image with the watermark
	 * @return BufferedImage with the new image joined
	 */
	private BufferedImage joinBufferedImage(BufferedImage img1, BufferedImage img2) {
		BufferedImage newImage = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, 0, 0);
		g2.dispose();
		return newImage;
	}

	/**
	 * removeFile removes the file and file extension and gets only the path
	 *
	 * @param path receives an absolute path of an image.
	 * @return String with the path without the file
	 */
	private String removeFile(String path) {
		int pos2 = 0;
		if (ImageController.OS.indexOf("nix") >= 0 || ImageController.OS.indexOf("nux") >= 0
				|| ImageController.OS.indexOf("aix") > 0) {
			pos2 = path.lastIndexOf("/");
		} else {
			pos2 = path.lastIndexOf("\\");
		}
		return path.substring(0, pos2 + 1);

	}

	/**
	 * getFileName receives a path and returns the filename
	 *
	 * @param path receives an absolute path of an image.
	 * @return String with the filename
	 */
	private String getFileName(String path) {
		int pos = 0, pos2 = 0;
		if (ImageController.OS.indexOf("nix") >= 0 || ImageController.OS.indexOf("nux") >= 0
				|| ImageController.OS.indexOf("aix") > 0) {
			pos2 = path.lastIndexOf("/");
		} else {
			pos2 = path.lastIndexOf("\\");
		}
		pos = path.lastIndexOf(".");

		return path.substring(pos2 + 1, pos);

	}


}
