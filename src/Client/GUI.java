package Client;


import javax.swing.*;
import java.awt.*;

public class GUI {
    JFrame frame;
    JPanel mainPanel;
    JPanel topPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    JTextArea textArea;
    JScrollPane scrollPane;
    JButton disconnectButton;
    JTextField textField;
    String username;

    public GUI(String username) {
        this.username = username;
        frame = new JFrame();
        mainPanel = new JPanel();
        topPanel = new JPanel();
        centerPanel = new JPanel();
        bottomPanel = new JPanel();
        textArea  = new JTextArea();
        scrollPane  = new JScrollPane(textArea);
        disconnectButton = new JButton("Disconnect");
        textField = new JTextField();
    }

    public void init() {
        frame.setSize(600,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(mainPanel);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        topPanel.setLayout(new GridLayout(1,1));
        topPanel.add(disconnectButton);

        centerPanel.setLayout(new GridLayout(1,1));
        centerPanel.add(scrollPane);


        bottomPanel.setLayout(new GridLayout(1,1));
        bottomPanel.add(textField);

    }

    public JTextField getTextField() {
        return textField;
    }

    public JTextArea getTextArea() {
        return textArea;
    }



}