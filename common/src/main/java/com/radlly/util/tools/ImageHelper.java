package com.radlly.util.tools;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
 


public class ImageHelper {
	//裁剪
	  public static BufferedImage cutImage(InputStream in,int x,int y,int w,int h) throws IOException{   
	        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");   
	        ImageReader reader = (ImageReader)iterator.next();   
	        ImageInputStream iis = ImageIO.createImageInputStream(in);   
	        reader.setInput(iis, true);   
	        ImageReadParam param = reader.getDefaultReadParam();   
	        Rectangle rect = new Rectangle(x, y, w,h);    
	        param.setSourceRegion(rect);   
	        BufferedImage bi = reader.read(0,param);  
	        return bi;          

	 }   
	  //压缩 、裁剪
	    public static BufferedImage resizeFix(InputStream in,int x,int y,int w, int h) throws IOException {  
	    	 Image  img = ImageIO.read(in);      // 构造Image对象  
	        int  width = img.getWidth(null);    // 得到源图宽  
	        int  height = img.getHeight(null);  // 得到源图长  
	        if (width / height > w / h) {  
	        	h = (int) (height * w / width);  
	        } else {  
	        	 w = (int) (width * h / height);    
	        }  
	        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
	        image.getGraphics().drawImage(img, x, y, w, h, null); // 绘制缩小后的图  
	        return image;
	    }  
	  
	    public static InputStream BufferedImageToInputStream(BufferedImage image,String name){
	    	ByteArrayOutputStream os = new ByteArrayOutputStream();  
	    	try {
				ImageIO.write(image, name, os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    	InputStream is = new ByteArrayInputStream(os.toByteArray()); 
	    	return is;
	    }
	 
}
