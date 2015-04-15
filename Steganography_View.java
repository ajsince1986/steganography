/*
 *Import List
 */
import java.awt.Color;
import java.awt.Insets;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

/*
 *Class Steganography_View
 */
public class Steganography_View extends JFrame
{
	//sie variables for window
	private static int WIDTH  = 500;
	private static int HEIGHT = 400;
	
	//elements for JPanel
	private JTextField input_username;
	private JPasswordField input_password;
	private JScrollBar 	scroll,scroll2;
	private JButton		encodeImageButton, decodeButton, loginButton, secretButton;
	private JLabel		secret_image_input, image_input, username_label, password_label;
	
	//elements for Menu
	private JMenu 		file;
	private JMenuItem 	encode;
	private JMenuItem 	decode;
	private JMenuItem 	exit;
	
	/*
	 *Constructor for Steganography_View class
	 *@param name Used to set the title on the JFrame
	 */
	public Steganography_View(String name)
	{
		//set the title of the JFrame
		super(name);
		
		//Menubar
		JMenuBar menu = new JMenuBar();
		
		JMenu file = new JMenu("File");	file.setMnemonic('F');
		encode = new JMenuItem("Encode"); encode.setMnemonic('E'); file.add(encode);
		decode = new JMenuItem("Decode"); decode.setMnemonic('D'); file.add(decode);
		file.addSeparator();
		exit = new JMenuItem("Exit"); exit.setMnemonic('x'); file.add(exit);
		encode.setEnabled(false);
		decode.setEnabled(false);
		
		menu.add(file);
		setJMenuBar(menu);
		
		// display rules
		setResizable(true);						//allow window to be resized: true?false
		setBackground(Color.lightGray);			//background color of window: Color(int,int,int) or Color.name
		setLocation(100,100);					//location on the screen to display window
        		setDefaultCloseOperation(EXIT_ON_CLOSE);//what to do on close operation: exit, do_nothing, etc
        		setSize(WIDTH,HEIGHT);					//set the size of the window
        		setVisible(true);						//show the window: true?false
	}
	
	/*
	 *@return The menu item 'Encode'
	 */
	public JMenuItem	getEncode()		{ return encode;		}
	/*
	 *@return The menu item 'Decode'
	 */
	public JMenuItem	getDecode()		{ return decode;		}
	/*
	 *@return The menu item 'Exit'
	 */
	public JMenuItem	getExit()		{ return exit;			}
	/*
	 *@return The TextField containing the username to login
	 */
	public JTextField 	getUsername() 	{ return input_username; }
	/*
	 *@return The PasswordFiled containing the password to login
	 */
	public JPasswordField 	getPassword() 	{ return input_password; }
	/*
	 *@return The JLabel containing the secret image to encode
	 */
	public JLabel		getSecretImageInput() { return secret_image_input;	}
	/*
	 *@return The JLabel containing the image to decode text from
	 */
	public JLabel		getImageInput()	{ return image_input;		}
	/*
	 *@return The JPanel displaying the Login View
	 */
	public JPanel		getLoginPanel()		{ return new Login_Panel();	}
	/*
	 *@return The JPanel displaying the Encode Image View
	 */
	public JPanel		getEncodeImagePanel()	{ return new Encode_Image_Panel();	}
	/*
	 *@return The JPanel displaying the Decode View
	 */
	public JPanel		getImagePanel()	{ return new Image_Panel();	}
	/*
	 *@return The Login button
	 */
	public JButton		getLButton()		{ return loginButton;		}
	/*
	 *@return The secret image select button
	 */
	public JButton		getSButton()		{ return secretButton;		}
	/*
	 *@return The Encode Image button
	 */
	public JButton		getEIButton()		{ return encodeImageButton;		}
	/*
	 *@return The Decode button
	 */
	public JButton 		getDButton()		{  return decodeButton;		}
	
	/*
	 *Class Login_Panel
	 */
	private class Login_Panel extends JPanel
	{
		/*
		 *Constructor to enter login details
		 */
		public Login_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);

			username_label 	= new JLabel("Enter Username: ");
			password_label 	= new JLabel("Enter password: ");
			input_username	= new JTextField(20);
			input_password 	= new JPasswordField(20);
			loginButton 	= new JButton("Login");

			layoutConstraints.insets 	= new Insets(10,10,10,10);
			layoutConstraints.anchor 	= GridBagConstraints.WEST;

			layoutConstraints.gridx 	= 0;
			layoutConstraints.gridy 	= 0; 
			add(username_label, layoutConstraints);

			layoutConstraints.gridx		= 1;
			add(input_username, layoutConstraints);

			layoutConstraints.gridx		= 0;
			layoutConstraints.gridy 	= 1;
			add(password_label, layoutConstraints);

			layoutConstraints.gridx 	= 1;
			add(input_password, layoutConstraints);

			layoutConstraints.gridx 	= 0;
        	layoutConstraints.gridy 	= 2;
        	layoutConstraints.gridwidth = 2;
        	layoutConstraints.anchor 	= GridBagConstraints.CENTER;
        	add(loginButton, layoutConstraints);

        	setBackground(Color.lightGray);
        	setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Login"));
		}
	}

	/*
	 *Class Encode_Image_Panel
	 */
	private class Encode_Image_Panel extends JPanel
	{
		/*
		 *Constructor to select secret image to be encoded
		 */
		public Encode_Image_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);

			secretButton = new JButton("Select Secret Image");

			layoutConstraints.insets 	= new Insets(10,10,10,10);
			layoutConstraints.anchor 	= GridBagConstraints.WEST;

			layoutConstraints.gridx 	= 0;
        	layoutConstraints.gridy 	= 0;
        	layoutConstraints.gridwidth = 1;
        	layoutConstraints.anchor 	= GridBagConstraints.CENTER;
        	add(secretButton, layoutConstraints);

        	secret_image_input = new JLabel();
        	layoutConstraints.gridx 	= 0;
        	layoutConstraints.gridy 	= 1;
        	layoutConstraints.anchor 	= GridBagConstraints.CENTER;
        	add(secret_image_input, layoutConstraints);

        	encodeImageButton = new JButton("Encode Image Now");
        	layoutConstraints.gridx 	= 0;
        	layoutConstraints.gridy 	= 2;
        	layoutConstraints.gridwidth = 1; 
        	layoutConstraints.gridheight = 1; 
        	layoutConstraints.fill 		= GridBagConstraints.BOTH; 
        	layoutConstraints.weightx 	= 1.0; 
        	layoutConstraints.weighty = 1.0;
        	layoutConstraints.anchor = GridBagConstraints.PAGE_END;
        	layoutConstraints.fill = GridBagConstraints.CENTER;
			layout.setConstraints(encodeImageButton,layoutConstraints);
	    	add(encodeImageButton);
	    	encodeImageButton.setVisible(false);

        	setBackground(Color.lightGray);
        	setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Encode"));

		}
	}
	
	/*
	 *Class Image_Panel
	 */
	private class Image_Panel extends JPanel
	{
		/*
		 *Constructor for displaying an image to be decoded
		 */
		public Image_Panel()
		{
			//setup GridBagLayout
			GridBagLayout layout = new GridBagLayout(); 
			GridBagConstraints layoutConstraints = new GridBagConstraints(); 
			setLayout(layout);
			
			image_input = new JLabel();
			layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 0; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,0,0,0); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 50.0;
			JScrollPane scroll2 = new JScrollPane(image_input,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
			layout.setConstraints(scroll2,layoutConstraints);
			scroll2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			image_input.setHorizontalAlignment(JLabel.CENTER);
	    		add(scroll2);
	    	
	    		decodeButton = new JButton("Decode Now");
	    		layoutConstraints.gridx 	= 0; layoutConstraints.gridy = 1; 
			layoutConstraints.gridwidth = 1; layoutConstraints.gridheight = 1; 
			layoutConstraints.fill 		= GridBagConstraints.BOTH; 
			layoutConstraints.insets 	= new Insets(0,-5,-5,-5); 
			layoutConstraints.anchor 	= GridBagConstraints.CENTER; 
			layoutConstraints.weightx 	= 1.0; layoutConstraints.weighty = 1.0;
			layout.setConstraints(decodeButton,layoutConstraints);
	    		add(decodeButton);
	    	
	    		//set basic display
			setBackground(Color.lightGray);
			setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Decode"));
		 }
	 }
}