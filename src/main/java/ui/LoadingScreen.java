package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class LoadingScreen {

    private JLabel icon;
    private JLabel lbStatus;
    private JProgressBar bar;
    private JDialog loadingScreen;

    public LoadingScreen(Frame parent, boolean modal) {
        loadingScreen = new JDialog(parent, modal);
        bar = new JProgressBar();
        icon = new JLabel();
        lbStatus = new JLabel();
        initialize();
    }

    private void initialize() {
        loadingScreen.setTitle("Loading Screen");
        loadingScreen.setSize(600, 450);
        loadingScreen.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        loadingScreen.setLocationRelativeTo(null);
        loadingScreen.setLayout(new BorderLayout());

        loadingScreen.setUndecorated(true);
        loadingScreen.addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        bar.setPreferredSize(new Dimension(20, 25));
        bar.setBackground(Color.WHITE);
        bar.setForeground(Color.BLACK);
        bar.setStringPainted(true);

        ImageIcon logoIcon = new ImageIcon("src/main/java/com/example/src/ui/Java.png"); // Load image
        Image img = logoIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(img);
        icon.setIcon(logoIcon);

        lbStatus.setForeground(new Color(200, 200, 200));
        lbStatus.setHorizontalAlignment(SwingConstants.CENTER);
        lbStatus.setText("Status");

        GroupLayout layout = new GroupLayout(loadingScreen.getContentPane());
        loadingScreen.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        // Push icon right by adding a left gap inside this parallel group
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(100) // additional gap just for icon
                                                .addComponent(icon, GroupLayout.PREFERRED_SIZE, 300,
                                                        GroupLayout.PREFERRED_SIZE))
                                        // bar and label remain centered without extra gap
                                        .addComponent(bar, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lbStatus, GroupLayout.PREFERRED_SIZE, 300,
                                                GroupLayout.PREFERRED_SIZE))
                                .addGap(50)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(100)
                        .addComponent(icon)
                        .addGap(20)
                        .addComponent(bar, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(lbStatus)
                        .addGap(100));

    }

    public void dispose() {
        loadingScreen.dispose();
    }

    public void setVisible(boolean b) {
        loadingScreen.setVisible(b);
    }

    private void formWindowOpened(WindowEvent evt) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    doTask("Connecting To Database ...", 10);
                    doTask("Connected To Database ...", 20);
                    doTask("Task 1 ...", 30);
                    doTask("Task 2 ...", 40);
                    doTask("Task 3 ...", 50);
                    doTask("Task 4 ...", 60);
                    doTask("Task 5 ...", 70);
                    doTask("Task 6 ...", 80);
                    doTask("Task 7 ...", 90);
                    doTask("Task 8 ...", 100);
                    doTask("Done ...", 100);
                    dispose();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void doTask(String taskName, int progress) throws Exception {
        lbStatus.setText(taskName);
        bar.setValue(progress);
        Thread.sleep(250); // For Test
    }

    public static void main(String args[]) {
        /* Create and display the dialog */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoadingScreen dialog = new LoadingScreen(new JFrame(), true);
                dialog.loadingScreen.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
}
