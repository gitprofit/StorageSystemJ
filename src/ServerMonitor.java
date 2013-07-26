import java.awt.GridLayout;
import java.util.*;

import javax.swing.*;

class StorageInfo {
	private DataStorage storage;

	public JLabel name;
	public JProgressBar bar;
	public JLabel status;

	public StorageInfo(DataStorage ds) {
		storage = ds;

		name = new JLabel(storage.toString());
		bar = new JProgressBar(0, 100);
		status = new JLabel("");
	}

	public void update() {
		double barValue = 100 * storage.getGUIFillFactor();
		bar.setValue((int) barValue);

		status.setText(storage.getState().toString());
	}
}

public class ServerMonitor implements Runnable {

	private StorageSystem system;

	private JFrame window;
	private JPanel barsPanel;
	private JScrollPane leftPane;
	private JScrollPane rightPane;
	private JTextArea log;
	List<StorageInfo> storages;

	public ServerMonitor(StorageSystem system) {
		
		this.system = system;

		storages = new ArrayList<>();

		for (DataStorage ds : this.system.storages)
			storages.add(new StorageInfo(ds));
	}

	@Override
	public void run() {

		initGUI();

		for (;;) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}

			updateGUI();
		}
	}

	private void initGUI() {

		initBarsPanel();
		leftPane = new JScrollPane(barsPanel);
		leftPane.setBorder(BorderFactory.createTitledBorder("Data Storages"));
		initLog();
		rightPane = new JScrollPane(log);
		rightPane.setBorder(BorderFactory.createTitledBorder("Server log"));

		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.X_AXIS));
		contents.add(leftPane);
		contents.add(rightPane);

		window = new JFrame("Server Monitor");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.add(contents);
		window.pack();
		window.setVisible(true);
	}

	private void initBarsPanel() {
		barsPanel = new JPanel();
		barsPanel.setLayout(new GridLayout(storages.size(), 3));

		for (StorageInfo si : storages) {
			barsPanel.add(si.name);
			barsPanel.add(si.bar);
			barsPanel.add(si.status);
		}
	}

	private void initLog() {
		log = new JTextArea();
		log.setEditable(false);

		writeLog("Server Monitor, " + new Date().toString() + "\n");
	}

	private void writeLog(String message) {
		log.setText(String.format("%s%s%n", log.getText(), message));
	}

	private void updateGUI() {
		for (StorageInfo si : storages)
			si.update();
		
		/*
		Queue<String> messages = Logger.getInstance().grabMessages();

		while (!messages.isEmpty()) {
			writeLog(messages.poll());
			scrollLog();
		}*/
	}

	private void scrollLog() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JScrollBar bar = rightPane.getVerticalScrollBar();
				bar.setValue(bar.getMaximum());
			}
		});
	}
}