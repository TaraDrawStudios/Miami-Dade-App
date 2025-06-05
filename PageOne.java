import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PageOne extends JFrame {
    private final Map<String, List<String>> signIns;

    public PageOne(Map<String, List<String>> signIns) {
        this.signIns = signIns;

        setTitle("Page One");
        setSize(360, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //
        // 1) Create and set a custom JPanel that paints your background image
        //
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            private Image backgroundImage;

            {
                try {
                    backgroundImage = ImageIO.read(new File(
                        "C:\\Users\\1954b\\Desktop\\personal coding projects\\miami dade app\\app.jpg"
                    ));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                if (backgroundImage != null) {
                    // Draw the image stretched to fill the entire panel:
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        backgroundPanel.setOpaque(true);
        setContentPane(backgroundPanel);

        //
        // 2) Build the “center” section with absolute positioning
        //
        JPanel centerPanel = new JPanel(null);
        centerPanel.setOpaque(false);


        // b) The “Time Sheet” button at a fixed location
        JButton appButton = new JButton("Time Sheet");
        appButton.setFont(new Font("Arial", Font.BOLD, 8));

        // We want a 90×100 button, so we fix all three sizes:
        Dimension squareSize = new Dimension(80, 90);
        appButton.setPreferredSize(squareSize);
        appButton.setMinimumSize(squareSize);
        appButton.setMaximumSize(squareSize);
        appButton.setFocusPainted(false);

        // Place at x=30, y=80, width=90, height=100
        appButton.setBounds(6, 430, 80, 90);
        centerPanel.add(appButton);

        // Add centerPanel into the center of backgroundPanel
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // When “Time Sheet” is clicked, open the next window:
        appButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // close PageOne
                new timeSheet(signIns); // pass the same signIns map forward
            }
        });

        //
        // 3) Build the “Back” button at the bottom
        //
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFocusPainted(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();            // close PageOne
                miamiDade.main(null); // go back to the main screen
            }
        });

        buttonPanel.add(backButton);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        //
        // 4) Finally, show the frame
        //
        setVisible(true);
    }
}
