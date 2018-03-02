package Vue;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class FooterComponent extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private final static MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
	
	private JProgressBar progressBarMemory;
	
	public static int nbMaj = 0;
	
	protected FooterComponent() {
		this.initGUI();
		
		Thread t = new Thread() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(5000);
						majMemory();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		};
		
		t.start();
	}
	
	private void majMemory() {
		long MaxKilobyte = FooterComponent.memoryBean.getHeapMemoryUsage().getMax() / 1024;
		long MaxMegabyte = MaxKilobyte / 1024;
		
		long UsedKilobyte = FooterComponent.memoryBean.getHeapMemoryUsage().getUsed() / 1024;
		long UsedMegabyte = UsedKilobyte / 1024;
		
		int max = (MaxKilobyte >= Integer.MAX_VALUE) ? (int) MaxMegabyte : (int) MaxKilobyte;
		char unit = (MaxKilobyte >= Integer.MAX_VALUE) ? 'M' : 'K';
		int used = (MaxKilobyte >= Integer.MAX_VALUE) ? (int) UsedMegabyte : (int) UsedKilobyte;
		
		this.progressBarMemory.setMaximum(max);
		this.progressBarMemory.setValue(used);
		this.progressBarMemory.setToolTipText(used+" / "+max+" "+unit);
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		
		JPanel panTmp = new JPanel(new BorderLayout());
		panTmp.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.progressBarMemory = new JProgressBar();
		this.progressBarMemory.setStringPainted(true);
		this.progressBarMemory.setBorder(new EmptyBorder(new Insets(0, 10, 0, 10)));
		
		panTmp.add(this.progressBarMemory, BorderLayout.CENTER);
		
		this.add(panTmp, BorderLayout.EAST);
	}
}
