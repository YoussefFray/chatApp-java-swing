import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
 import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatClient {

	private JFrame frame;
	private JTextField textField;
	public static JTextArea textArea ;
	static Socket con ;
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClient window = new ChatClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		 
		con = new Socket("127.0.0.1",5032);
		 while(true)
		 {
		 try {
			DataInputStream input = new DataInputStream(con.getInputStream());
			 String string = input.readUTF();
			 System.out.println(string);
 			 textArea.setText(textArea.getText()+"\n "+"server : "+string);
		} catch (IOException e) {
			 textArea.setText(textArea.getText()+"\n "+"error");
			 try {
				 Thread.sleep(2000);
				System.exit(0);
			} catch (InterruptedException e2) {
				e.printStackTrace();
			}
		}

		 }
	}



	public ChatClient() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("Button.darkShadow"));
		frame.setBounds(100, 100, 526, 357);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(20, 45, 291, 39);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		 
				try {
					DataOutputStream output = new DataOutputStream(con.getOutputStream());
					output.writeUTF(textField.getText());
				} catch (IOException e1) {
					textArea.setText(textArea.getText()+"\n "+ "error");
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
