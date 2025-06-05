import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class timeSheet extends JFrame {
    private final String[] days = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday",
        "Monday (Week 2)", "Tuesday (Week 2)", "Wednesday (Week 2)", "Thursday (Week 2)",
        "Friday (Week 2)", "Saturday (Week 2)", "Sunday (Week 2)"
    };
    private int currentDayIndex;
    private JLabel dayLabel;
    private JTextArea timeSheetBox;
    private final Map<String, List<String>> signIns;

    public timeSheet(Map<String, List<String>> signIns) {
        this.signIns = signIns;

        currentDayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
        if (currentDayIndex < 0) currentDayIndex = 6;

        setTitle("Page One");
        setSize(360, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setBackground(Color.BLACK);
        contentPane.setLayout(new BorderLayout());

        // Title only in top panel
        JLabel titleLabel = new JLabel("Time Sheet", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        contentPane.add(topPanel, BorderLayout.NORTH);

        // Center content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.BLACK);

        JPanel dayContainerPanel = new JPanel();
        dayContainerPanel.setLayout(new BoxLayout(dayContainerPanel, BoxLayout.Y_AXIS));
        dayContainerPanel.setBackground(Color.BLACK);

        JPanel dayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        dayPanel.setBackground(Color.BLACK);

        JButton leftArrow = new JButton("<");
        JButton rightArrow = new JButton(">");

        dayLabel = new JLabel(days[currentDayIndex]);
        dayLabel.setForeground(Color.WHITE);
        dayLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        leftArrow.addActionListener(e -> {
            currentDayIndex = (currentDayIndex - 1 + days.length) % days.length;
            updateDay();
        });

        rightArrow.addActionListener(e -> {
            currentDayIndex = (currentDayIndex + 1) % days.length;
            updateDay();
        });

        dayPanel.add(leftArrow);
        dayPanel.add(dayLabel);
        dayPanel.add(rightArrow);

        timeSheetBox = new JTextArea();
        timeSheetBox.setFont(new Font("Monospaced", Font.PLAIN, 14));
        timeSheetBox.setForeground(Color.WHITE);
        timeSheetBox.setBackground(Color.BLACK);
        timeSheetBox.setEditable(false);
        timeSheetBox.setFocusable(false);
        timeSheetBox.setHighlighter(null);
        timeSheetBox.setLineWrap(true);
        timeSheetBox.setWrapStyleWord(true);
        timeSheetBox.setOpaque(false);
        timeSheetBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        dayContainerPanel.add(dayPanel);
        dayContainerPanel.add(timeSheetBox);
        centerPanel.add(dayContainerPanel);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with Logout (left) and Back (right)
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(90, 30));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            dispose();
            miamiDade.main(null);
        });

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            dispose();
            new PageOne(signIns);
        });

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(logoutButton, BorderLayout.WEST);
        buttonPanel.add(backButton, BorderLayout.EAST);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        updateDay();
        setVisible(true);
    }

    private void updateDay() {
        dayLabel.setText(getDayLabel(currentDayIndex));
        String currentDay = days[currentDayIndex];
        List<String> logs = signIns.getOrDefault(currentDay, new ArrayList<>());

        if (logs.isEmpty()) {
            timeSheetBox.setText("No sign-ins yet.");
        } else {
            timeSheetBox.setText(String.join("\n ", logs));
        }
    }

    private String getDayLabel(int index) {
        Calendar cal = Calendar.getInstance();
        int todayIndex = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 2;
        if (todayIndex < 0) todayIndex = 6;

        int diff = index - todayIndex;
        cal.add(Calendar.DAY_OF_MONTH, diff);
        String formattedDate = new java.text.SimpleDateFormat("M/dd/yyyy").format(cal.getTime());
        return days[index] + " " + formattedDate;
    }
}
