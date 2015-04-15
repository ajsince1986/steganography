/*
 *import list
 */
import java.io.File;

import java.awt.Point;
import java.awt.Graphics2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/*
 *Class Steganography
 */
public class Steganography
{
	
	/*
	 *Steganography Empty Constructor
	 */
	public Steganography()
	{
	}
	
	/*
	 *Encrypt an image with text, the output file will be of type .png
	 *@param path         	The path (folder) containing the image to modify
	 *@param original    	The name of the image to modify
	 *@param ext1          	The extension type of the image to modify (jpg, png)
	 *@param stegan      	The output name of the file
	 *@param sec_path  		The path of the secret image
	 *@param sec_orignal	The name of the secret image
	 *@param sec_ext1		The extension type of secret image (jpg, png)
	 */
	public boolean encode_image(String path, String original, String ext1, String stegan, String sec_path, String sec_orignal, String sec_ext1)
	{
		String			sec_file_name = image_path(sec_path, sec_orignal, sec_ext1);
		BufferedImage	sec_image_orig  = getImage(sec_file_name);

		String			file_name 	= image_path(path,original,ext1);
		BufferedImage 	image_orig	= getImage(file_name);
		
		BufferedImage sec_image = user_space(sec_image_orig);
		BufferedImage image = user_space(image_orig);
		image = add_img(image, sec_image);

		return(setImage(image,new File(image_path(path,stegan,"png")),"png"));
	}

	/*
	 *Decrypt assumes the image being used is of type .png, extracts the hidden image from an image
	 *@param path   The path (folder) containing the image to extract the image from
	 *@param name The name of the image to extract the image from
	 *@param type integer representing either basic or advanced encoding
	 */
	public BufferedImage decode_image(String path, String name)
	{
		byte[] decode;
		try
		{
			//user space is necessary for decrypting
			BufferedImage image  = user_space(getImage(image_path(path,name,"png")));
			decode = decode_data(get_byte_data(image));
			return get_image_byte(decode);
		}
		catch(Exception e)
        {
			JOptionPane.showMessageDialog(null, 
				"There is no hidden image in this image!",
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
        }
	}
    
	/*
	 *Returns the complete path of a file, in the form: path\name.ext
	 *@param path   The path (folder) of the file
	 *@param name The name of the file
	 *@param ext      The extension of the file
	 *@return A String representing the complete path of a file
	 */
	private String image_path(String path, String name, String ext)
	{
		return path + "/" + name + "." + ext;
	}
	
	/*
	 *Get method to return an image file
	 *@param f The complete path name of the image.
	 *@return A BufferedImage of the supplied file path
	 *@see    Steganography.image_path
	 */
	private BufferedImage getImage(String f)
	{
		BufferedImage 	image	= null;
		File 		file 	= new File(f);
		
		try
		{
			image = ImageIO.read(file);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, 
				"Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	/*
	 *Set method to save an image file
	 *@param image The image file to save
	 *@param file      File  to save the image to
	 *@param ext      The extension and thus format of the file to be saved
	 *@return Returns true if the save is succesful
	 */
	private boolean setImage(BufferedImage image, File file, String ext)
	{
		try
		{
			file.delete(); //delete resources used by the File
			ImageIO.write(image,ext,file);
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"File could not be saved!","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	/*
	 *Handles the addition of image into an image
	 *@param image The image to add hidden image to
	 *@param sec_image     The image to hide in the image
	 *@return Returns the image with the sec_image embedded in it
	 */
	private BufferedImage add_img(BufferedImage image, BufferedImage sec_image)
	{
		//convert all items to byte arrays: image, sec_image, sec_image length
		byte img[] = get_byte_data(image);
		byte sec_img[] = get_sec_byte_data(sec_image);
		byte len[] = bit_conversion(sec_img.length);
		try
		{
			encode_data(img, len, 0);
			encode_data(img, sec_img, 32);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	/*
	 *Creates a user space version of a Buffered Image, for editing and saving bytes
	 *@param image The image to put into user space, removes compression interferences
	 *@return The user space version of the supplied image
	 */
	private BufferedImage user_space(BufferedImage image)
	{
		//create new_img with the attributes of image
		BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D    graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose(); //release all allocated memory for this image
		return new_img;
	}
	
	/*
	 *Gets the image from byte array of image
	 *@param image_byte The data byte to be converted to image
	 *@return Returns the BufferedImage of the supplied data byte
	 */
	private BufferedImage get_image_byte(byte[] image_byte)
	{
		InputStream bais = new ByteArrayInputStream(image_byte);
    	try {
	        return ImageIO.read(bais);
    	} catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Error decoding image",
				"Error", JOptionPane.ERROR_MESSAGE);
			return null;
    	}
	}

	/*
	 *Gets the byte array of secret image
	 *@param img The BufferedImage to be converted to byte[]
	 *@return Returns the byte[] of supplied image
	 */
	private byte[] get_sec_byte_data(BufferedImage img) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			return baos.toByteArray();
		}
		catch(Exception e) {
			return null;
		}
	}

	/*
	 *Gets the byte array of an image
	 *@param image The image to get byte data from
	 *@return Returns the byte array of the image supplied
	 *@see Raster
	 *@see WritableRaster
	 *@see DataBufferByte
	 */
	private byte[] get_byte_data(BufferedImage image)
	{
		WritableRaster raster   = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
	
	/*
	 *Gernerates proper byte format of an integer
	 *@param i The integer to convert
	 *@return Returns a byte[4] array converting the supplied integer into bytes
	 */
	private byte[] bit_conversion(int i)
	{
		//originally integers (ints) cast into bytes
		//byte byte7 = (byte)((i & 0xFF00000000000000L) >>> 56);
		//byte byte6 = (byte)((i & 0x00FF000000000000L) >>> 48);
		//byte byte5 = (byte)((i & 0x0000FF0000000000L) >>> 40);
		//byte byte4 = (byte)((i & 0x000000FF00000000L) >>> 32);
		
		//only using 4 bytes
		byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
		byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
		byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
		byte byte0 = (byte)((i & 0x000000FF)       );
		//{0,0,0,byte0} is equivalent, since all shifts >=8 will be 0
		return(new byte[]{byte3,byte2,byte1,byte0});
	}

	/*
	 *Encode an array of bytes into another array of bytes at a supplied offset
	 *@param image     Array of data representing an image
	 *@param addition Array of data to add to the supplied image data array
	 *@param offset      The offset into the image array to add the addition data
	 *@return Returns data Array of merged image and addition data
	 */
	private byte[] encode_data(byte[] image, byte[] addition, int offset)
	{
		//check that the data + offset will fit in the image
		if(addition.length + offset > image.length)
		{
			throw new IllegalArgumentException("File not long enough!");
		}
		//loop through each addition byte
		for(int i=0; i<addition.length; ++i)
		{
			//loop through the 8 bits of each byte
			int add = addition[i];
			for(int bit=7; bit>=0; --bit, ++offset) //ensure the new offset value carries on through both loops
			{
				//assign an integer to b, shifted by bit spaces AND 1
				//a single bit of the current byte
				int b = (add >>> bit) & 1;
				//assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
				//changes the last bit of the byte in the image to be the bit of addition
				image[offset] = (byte)((image[offset] & 0xFE) | b );
			}
		}
		return image;
	}

	/*
	 *Retrieves hidden image from an image
	 *@param image Array of data, representing an image
	 *@return Array of data which contains the hidden image
	 */
	private byte[] decode_data(byte[] image)
	{
		int length = 0;
		int offset  = 32;
		//loop through 32 bytes of data to determine text length
		for(int i=0; i<32; ++i) //i=24 will also work, as only the 4th byte contains real data
		{
			length = (length << 1) | (image[i] & 1);
		}

		byte[] result = new byte[length];
		
		//loop through each byte of text
		for(int b=0; b<result.length; ++b )
		{
			//loop through each bit within a byte of text
			for(int i=0; i<8; ++i, ++offset)
			{
				//assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
				result[b] = (byte)((result[b] << 1) | (image[offset] & 1));

			}
		}
		return result;
	}
}