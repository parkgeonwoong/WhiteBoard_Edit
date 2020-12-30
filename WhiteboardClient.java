package whiteboard;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class WhiteboardClient extends JFrame implements ActionListener {
	
	private WhiteboardCanvas canvas;
	private JButton line, oval, clear, rect;
	
	public WhiteboardClient(String host, int port) {
		super("화이트 보드 클라이언트");
		
		JPanel EndPage = new JPanel();
		EndPage.setBounds(0, 0, 800, 600);
		getContentPane().add(EndPage);
		EndPage.setLayout(null);
		
		JPanel StartPage = new JPanel();
		StartPage.setBounds(0, 0, 800, 600);
		getContentPane().add(StartPage);
		StartPage.setLayout(null);
		
		EndPage.setVisible(false);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Panel");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("WhiteBoard");
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Chat");
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Save");
		
		mnNewMenu.add(mntmNewMenuItem_1);
		mnNewMenu.add(mntmNewMenuItem_2);
		mnNewMenu.add(mntmNewMenuItem_3);
		
		
		
		JToolBar tools = new JToolBar();
		StartPage.add(tools);
		line = new JButton("   선   ");
		line.addActionListener(this);
		oval = new JButton("   원   ");
		oval.addActionListener(this);
		rect = new JButton(" 사각형 ");
		rect.addActionListener(this);
		clear = new JButton(" 지우기 ");
		clear.addActionListener(this);
		tools.add(line);
		tools.add(oval);
		tools.add(rect);
		tools.addSeparator();
		tools.add(clear);
		
		canvas = new WhiteboardCanvas (host, port);
		canvas.setBackground(Color.white);
		StartPage.add(canvas);
		getContentPane().add("North", tools);
		getContentPane().add("Center", canvas);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
		
		
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StartPage.setVisible(true);
				EndPage.setVisible(false);
				tools.setVisible(true);
				canvas.setVisible(true);
			}
		});
		
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EndPage.setVisible(true);
				StartPage.setVisible(false);
				tools.setVisible(false);
				canvas.setVisible(false);
			}
		});
		
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.Save();
			}
		});
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object c = e.getSource();
		if(c == line) {
			canvas.setMode(WhiteboardCanvas.LINE);
		} else if (c == oval) {
			canvas.setMode(WhiteboardCanvas.CIRCLE);
		} else if (c == rect) {
			canvas.setMode(WhiteboardCanvas.RECT);
		} else if (c == clear) {
			canvas.send("!x");
		}
	}
	
	public static void main(String[] args) {
		new WhiteboardClient("localhost", 9850);
	}
}
