package FinalProject;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartFrame extends JFrame {
	public StartFrame() throws Exception{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,350);
		setLocation(500,150);
		setTitle("Mrello");

		setUndecorated(true);
		JLabel startLogo = new JLabel(new ImageIcon(ImageIO.read(new File("MainLogo.png"))));
		add(startLogo);
		setVisible(true);
		Thread.sleep(500);
		setVisible(false);
	}
}

//�� ó�� ���������� ���α׷� �ΰ� ȭ�鿡 ����Ѵ�.