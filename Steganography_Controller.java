/*
 *Import List
 */
import java.io.File;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.util.Arrays;

/*
 *Steganography_Controller Class
 */
public class Steganography_Controller
{
	//Program Variables
	private Steganography_View	view;
	private Steganography		model;
	
	//Panel Displays
	private JPanel		decode_panel;
	private JPanel		login_panel;
	private JPanel		encode_image_panel;
	//Panel Variables
	private JTextField 	username;
	private JPasswordField password;
	private JButton		encodeImageButton, decodeButton, loginButton, secretImageButton;
	private JLabel		secret_image_input, image_input;
	//Menu Variables
	private JMenuItem 	encode;
	private JMenuItem 	decode;
	private JMenuItem 	exit;
	
	//action event classes
	private Encode			enc;
	private Decode			dec;
	private LoginButton 	logButton;
	private SecretImageButton secImageButton;
	private EncodeImageButton encImageButton;
	private DecodeButton	decButton;
	
	//encode variable
	private String 			secret_stat_ext  = "";
	private String 			secret_stat_path = "";
	private String 			secret_stat_name = "";
	//decode variable
	private String			stat_path = "";
	private String			stat_name = "";
	
	/*
	 *Constructor to initialize view, model and environment variables
	 *@param aView  A GUI class, to be saved as view
	 *@param aModel A model class, to be saved as model
	 */
	public Steganography_Controller(Steganography_View aView, Steganography aModel)
	{
		//program variables
		view  = aView;
		model = aModel;
		
		//assign View Variables
		
		login_panel 	= view.getLoginPanel();
		encode_image_panel = view.getEncodeImagePanel();
		decode_panel	= view.getImagePanel();
		
		username 		= view.getUsername();
		password		= view.getPassword();
		secret_image_input = view.getSecretImageInput();
		image_input		= view.getImageInput();
		
		loginButton 	= view.getLButton();
		secretImageButton = view.getSButton();
		encodeImageButton = view.getEIButton();
		decodeButton	= view.getDButton();
		
		//menu
		encode			= view.getEncode();
		decode			= view.getDecode();
		exit			= view.getExit();
		
		//assign action events
		enc = new Encode();
		encode.addActionListener(enc);
		dec = new Decode();
		decode.addActionListener(dec);
		exit.addActionListener(new Exit());
		logButton = new LoginButton();
		loginButton.addActionListener(logButton);
		secImageButton = new SecretImageButton();
		secretImageButton.addActionListener(secImageButton);
		encImageButton = new EncodeImageButton();
		encodeImageButton.addActionListener(encImageButton);
		decButton = new DecodeButton();
		decodeButton.addActionListener(decButton);
		
		//login view as default
		login_view();
	}

	/*
	 *Updates the single panel to display the Login View
	 */
	private void login_view()
	{
		update();
		view.setContentPane(login_panel);
		view.setVisible(true);
	}

	/*
	 *Updates the single panel to display the Encode View.
	 */
	private void encode_image_view()
	{
		update();
		view.setContentPane(encode_image_panel);
		view.setVisible(true);
	}
	
	/*
	 *Updates the single panel to display the Decode View.
	 */
	private void decode_view()
	{
		update();
		view.setContentPane(decode_panel);
		view.setVisible(true);
	}
	
	/*
	 *Encode Class - handles the Encode menu item
	 */
	private class Encode implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			encode_image_view(); //show the encode view
		}
	}
	
	/*
	 *Decode Class - handles the Decode menu item
	 */
	private class Decode implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			decode_view(); //show the decode view
			
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File directory = chooser.getSelectedFile();
				try
				{
					String image = directory.getPath();
					stat_name = directory.getName();
					stat_path = directory.getPath();
					stat_path = stat_path.substring(0,stat_path.length()-stat_name.length()-1);
					stat_name = stat_name.substring(0, stat_name.length()-4);
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
				}
				catch(Exception except) {
				//msg if opening fails
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	/*
	 *Exit Class - handles the Exit menu item
	 */
	private class Exit implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0); //exit the program
		}
	}

	/*
	 *Login Button Class - handles the Login Button item
	 */
	private class LoginButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			String user = username.getText();
			char[] pass = password.getPassword();
			if (user.equals("manali")) {
				char [] correctPass = {'a','d','m','i','n','1','2','3'};
				if (correctPass.length == pass.length) {
					if (Arrays.equals(pass, correctPass)) {
						encode_image_view();
						encode.setEnabled(true);
						decode.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(view,  "Wrong Username or Password", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(view,  "Wrong Username or Password", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(view,  "Wrong Username or Password", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	/*
	 *Secret Button Class - handles the Secret Button item
	 */
	private class SecretImageButton implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File directory = chooser.getSelectedFile();
				try
				{
					String image = directory.getPath();
					secret_stat_ext  = Image_Filter.getExtension(directory);
					secret_stat_name = directory.getName();
					secret_stat_path = directory.getPath();
					secret_stat_path = secret_stat_path.substring(0,secret_stat_path.length()-secret_stat_name.length()-1);
					secret_stat_name = secret_stat_name.substring(0, secret_stat_name.length()-4);
					secret_image_input.setIcon(new ImageIcon(ImageIO.read(new File(image))));
					secretImageButton.setVisible(false);
					encodeImageButton.setVisible(true);
				}
				catch(Exception except) {
				//msg if opening fails
				JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		}
	}

	/*
	 *Encode Image Button Class - handles the Encode Image Button item
	 */
	private class EncodeImageButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			//start path of displayed File Chooser
			JFileChooser chooser = new JFileChooser("./");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setFileFilter(new Image_Filter());
			int returnVal = chooser.showOpenDialog(view);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File directory = chooser.getSelectedFile();
				try{
					String ext  = Image_Filter.getExtension(directory);
					String name = directory.getName();
					String path = directory.getPath();
					path = path.substring(0,path.length()-name.length()-1);
					name = name.substring(0, name.length()-4);

					System.out.println(path);
					System.out.println(name);
					System.out.println(ext);
					
					String stegan = JOptionPane.showInputDialog(view,
							"Enter output file name:", "File name",
							JOptionPane.PLAIN_MESSAGE);

					if(model.encode_image(path, name, ext, stegan, secret_stat_path, secret_stat_name, secret_stat_ext))
					{
						JOptionPane.showMessageDialog(view, "The Image was encoded Successfully!", 
							"Success!", JOptionPane.INFORMATION_MESSAGE);
					}					
					else
					{
						JOptionPane.showMessageDialog(view, "The Image could not be encoded!", 
							"Error!", JOptionPane.INFORMATION_MESSAGE);
					}
					//display the new image
					decode_view();
					image_input.setIcon(new ImageIcon(ImageIO.read(new File(path + "/" + stegan + ".png"))));
				}
				catch(Exception except) {
					//msg if opening fails
					JOptionPane.showMessageDialog(view, "The File cannot be opened!", 
						"Error!", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
	
	/*
	 *Decode Button Class - handles the Decode Button item
	 */
	private class DecodeButton implements ActionListener
	{
		/*
		 *handles the click event
		 *@param e The ActionEvent Object
		 */
		public void actionPerformed(ActionEvent e)
		{
			// String message = model.decode(stat_path, stat_name);
			BufferedImage dec_sec_image = model.decode_image(stat_path, stat_name);
			System.out.println(stat_path + ", " + stat_name);
			System.out.println(dec_sec_image);
			if(dec_sec_image != null)
			{
				//encode_image_view();
				JOptionPane.showMessageDialog(view, "The Image was decoded Successfully!", 
					"Success!",  JOptionPane.INFORMATION_MESSAGE);
				image_input.setIcon(new ImageIcon(dec_sec_image));
				decodeButton.setVisible(false);
				// try {
				// 	File outputfile = new File("test_output.png");
				// 	ImageIO.write(dec_sec_image, "png", outputfile);
				// }
				// catch(Exception er) {
				// 	System.out.println(er);
				// }
			}
			else
			{
				JOptionPane.showMessageDialog(view, "The Image could not be decoded!", 
					"Error!", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	/*
	 *Updates the variables to an initial state
	 */
	public void update()
	{
		image_input.setIcon(null);	//clear image
		stat_path = "";			//clear path
		stat_name = "";			//clear name
	}

	public static void main(String[] args) {
		Steganography_View sv = new Steganography_View("My Project");
		Steganography sm = new Steganography();
		Steganography_Controller sc = new Steganography_Controller(sv, sm);
	}
}