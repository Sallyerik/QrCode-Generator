
import java.io.File;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Dimension;


/**
 * @author Sallyerik
 * Created: 06/05/2019 
 */
 
public class QRcode {
	
	// static values
	static String user = System.getProperty("user.name");
	static String keyId = user + "@aula.campusciberseguridad.com";
	static String Filepath = "/tmp/";
	
	
	
	public static void main(String[] args) {
		
		//Generate secret
		String secret = generateBase32Secret();
		// Generate Qrimage base on KeyID + secret and store it within the URL
		String Qrcode = qrImageUrl(keyId, secret);
		// System.out.println("URl is: "+ Qrcode);
		// Download remote png file to local from generated URL
		ImageDownloader(Qrcode);
		
	}
 
	public static String qrImageUrl(String keyId, String secret) {
		
		StringBuilder sb = new StringBuilder(256);
		sb.append("https://chart.googleapis.com/chart");
		sb.append("?chs=400x400&cht=qr&chl=400x400&chld=M|0&cht=qr&chl=");
		sb.append("otpauth://totp/").append(keyId).append("%3Fsecret%3D").append(secret);										  
												  
		
	
		return sb.toString();
	}
	
	public static String generateBase32Secret() {
		StringBuilder sb = new StringBuilder();
		Random random = new SecureRandom();
		for (int i = 0; i < 16; i++) {
			int val = random.nextInt(32);
			if (val < 26) {
				sb.append((char) ('A' + val));
			} else {
				sb.append((char) ('2' + (val - 26)));
			}
		}
		return sb.toString();
	}
	
	public static void ImageDownloader(String urlString) {    
	        BufferedImage image =null;
	        try{
	     
	            URL url =new URL(urlString);
	            // get the url
	           image = ImageIO.read(url);
	            
	            ImageIO.write(image, "png",new File(Filepath + "have_a_new_Qrcode.png"));
	 
	        }catch(IOException e){
	            e.printStackTrace();
	        }
	        img(Filepath);
	    }
	
	public static void img(String Filepath){

	    JFrame frame = new JFrame(); //generate jframe f
	    
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Get your screen size

	    frame.setUndecorated(true); //removes the surrounding border

	    ImageIcon image = new ImageIcon((Filepath+"have_a_new_Qrcode.png")); //Save the image

	    JLabel lbl = new JLabel(image); //puts the image into a jlabel

	    frame.getContentPane().add(lbl); //puts label inside the jframe

	    frame.setSize(image.getIconWidth(), image.getIconHeight()); //gets h and w of image and sets jframe to the size

	    int x = (screenSize.width - frame.getSize().width)/2; //get size of the image
	    int y = (screenSize.height - frame.getSize().height)/2;// center the image

	    frame.setLocation(x, y); //sets jframe position
	    
	    frame.setVisible(true); //pop up the jframe 
	    
	    try {
			TimeUnit.SECONDS.sleep(60);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //exit after time specified
	    System.exit(0);
	}
	
}


