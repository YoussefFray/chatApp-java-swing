import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class ChatServer {

	private JFrame frame;
	private JTextField textField;
	private static JTextArea textArea ;
	private JButton btnNewButton ;
	static ServerSocket server ;
	static Socket con ;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServer window = new ChatServer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 server= new ServerSocket(5025);
		 con=server.accept();	
		 while(true)
		 {
		 try {
			DataInputStream input = new DataInputStream(con.getInputStream());
			 String string = input.readUTF();
			 textArea.setText(textArea.getText()+"\n "+"Client: "+string);
		} catch (IOException e) {
			 textArea.setText(textArea.getText()+"\n "+"error");
		}
		 try {
			 Thread.sleep(2000);
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 }
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public ChatServer() throws IOException {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 526, 357);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(20, 45, 291, 39);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		 btnNewButton = new JButton("send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DataOutputStream output = new DataOutputStream(con.getOutputStream());
					output.writeUTF(textField.getText());
				} catch (IOException e1) {
 					try {
						Thread.sleep(2000);
						System.exit(0);
					} catch (InterruptedException e2) {
						e2.printStackTrace();
					}
				}
				textField.setText("");
			}
		});
		btnNewButton.setBounds(354, 45, 110, 39);
		frame.getContentPane().add(btnNewButton);
		
	    textArea = new JTextArea();
		textArea.setBounds(20, 134, 291, 161);
		frame.getContentPane().add(textArea);
	}
}
