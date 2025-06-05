
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class miamiDade {
    private static Map<String, List<String>> signIns = new HashMap<>();
    public static void main(String[] args){
        int width = 360;
        int height = 780;

        JFrame frame = new JFrame("Miami-Dade");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height); 
        frame.getContentPane().setBackground(Color.black);

        // Custom JPanel to paint background image
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File("C:\\Users\\1954b\\Desktop\\personal coding projects\\miami dade app\\background.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setOpaque(true);
        frame.setContentPane(backgroundPanel); // use our custom panel

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        JButton signInButton = new JButton("Sign In");
        signInButton.setPreferredSize(new Dimension(120, 40));
        signInButton.setFocusPainted(false);
        signInButton.setBounds(6, 430, 80, 90);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setOpaque(false);
        buttonPanel.add(signInButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        signInButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.dispose();

            // Get current day and time
            String day = new SimpleDateFormat("EEEE").format(new Date());
            String time = new SimpleDateFormat("h:mm a").format(new Date());
            String entry = "Signed in at " + time;

            // Access or create the sign-in list
            List<String> logs = signIns.getOrDefault(day, new ArrayList<>());

            // Check if already 5 sign-ins
            if (logs.size() >= 5) {
                JOptionPane.showMessageDialog(null, "Maximum of 5 sign-ins reached for today.");
                miamiDade.main(null); // Go back to main screen
                return;
            }

            // Add new sign-in and store back
            logs.add(entry);
            signIns.put(day, logs);

            new PageOne(signIns);
        }
    });

        frame.setVisible(true);
    }
}
