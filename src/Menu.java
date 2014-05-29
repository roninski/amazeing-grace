import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * A menu. Can draw itself onto a GameDisplayer and wait for user input.
 *
 */
public class Menu extends JPanel implements ActionListener {
	private ArrayList <MenuItem> items = new ArrayList<MenuItem>();
	private String title;
	
	/**
	 * Creates a new menu.
	 */
	public Menu (String title) {
		this.title = title;
		this.setBackground(Color.BLACK);
	}
	
	/**
	 * Adds an item to the menu, with the given display text and key. Cannot be called after attach.
	 * @param display
	 * @param key
	 */
	public void addItem(String display, String key) {
		MenuItem newItem = new MenuItem(display, key);
		items.add(newItem);
	}
	
	
	/**
	 * Attaches oneself to the specified gameInterface for viewing.
	 * @param gameInterface
	 */
	public String getInput(Display gameInterface) {
		// Create layout
		this.setSize(gameInterface.getWidth(), gameInterface.getHeight());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// Create header image
		JLabel pic = new JLabel();
		if (title.equals("Amazeing")) pic.setIcon(new ImageIcon(Images.images.get(Images.LOGO)));
		else if (title.equals("You won!")) pic.setIcon(new ImageIcon(Images.images.get(Images.WIN)));
		else if (title.equals("Game over!")) pic.setIcon(new ImageIcon(Images.images.get(Images.LOSE)));
		pic.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(pic);
		
		// Create buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < items.size(); i++) {
			JButton jb = new JButton();
			jb.setText(items.get(i).getName());
			jb.setActionCommand(String.format("%d", i));
			jb.addActionListener(this);
			jb.setAlignmentX(Component.CENTER_ALIGNMENT);
			setDefaultButton(jb);
			
			buttonPanel.add(jb);
		}

		JScrollPane scrollable = new JScrollPane(buttonPanel);
		scrollable.setBackground(Color.BLACK);
		this.add(scrollable);
		// possess
		gameInterface.possess(this, null, null, null, null);
		//gameInterface.frame.add(this);
		this.setVisible(true);
		
		
		// wait for input
		while (result == null) {
			synchronized (resultlock) {
				try {
					resultlock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	private String result = null;
	private Object resultlock = new Object();
	private void register(String key) {
		result = key;
		synchronized (resultlock) {
			resultlock.notify();
		}
	}

	/**
	 * Handles a menu button click.
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int sender = Integer.parseInt(e.getActionCommand());
		
		register(items.get(sender).getKey());
		
	}
	
	public void setDefaultButton(JButton button) {
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setFont(new Font("Helvetica", Font.BOLD, 24));
	}
}

class MenuItem {
	private String name;
	private String key;
	
	public MenuItem (String name, String key) {
		this.name = name;
		this.key = key;
	}
	
	public String getName() {
		return name;
	}
	
	public String getKey() {
		return key;
	}
}
