import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class XOX implements ActionListener {
    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JPanel footer_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    JButton resetButton = new JButton("Reset");
    boolean player1_turn;

    XOX() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Title panel and label
        textfield.setBackground(new Color(0, 0, 50));
        textfield.setForeground(new Color(255, 255, 255));
        textfield.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("XOX Game");
        textfield.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        //Button panel (game grid)
        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(Color.WHITE);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 100));
            buttons[i].setBackground(new Color(240, 240, 240));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }

        //Reset button
        resetButton.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
        resetButton.setBackground(new Color(200, 0, 0));
        resetButton.setForeground(Color.WHITE);
        resetButton.setFocusable(false);
        resetButton.setEnabled(false);
        resetButton.addActionListener(this);

        footer_panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footer_panel.add(resetButton);

        //add components to frame
        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel, BorderLayout.CENTER);
        frame.add(footer_panel, BorderLayout.SOUTH);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            resetGame();
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (buttons[i].getText().isEmpty()) {
                        buttons[i].setForeground(new Color(200, 0, 0)); // Red X
                        buttons[i].setText("X");
                        player1_turn = false;
                        textfield.setText("O's Turn");
                        check();
                    }
                }
                else {
                    if (buttons[i].getText().isEmpty()) {
                        buttons[i].setForeground(new Color(0, 0, 200)); // Blue O
                        buttons[i].setText("O");
                        player1_turn = true;
                        textfield.setText("X's Turn");
                        check();
                    }
                }
            }
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(1800);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X's Turn");
        }
        else {
            player1_turn = false;
            textfield.setText("O's Turn");
        }
    }

    public void check() {
        if (checkWinningCondition("X")) {
            xWins();
        }
        else if (checkWinningCondition("O")) {
            oWins();
        }
        else if (isDraw()) {
            draw();
        }
    }

    private boolean checkWinningCondition(String player) {
        return (buttons[0].getText().equals(player) && buttons[1].getText().equals(player) && buttons[2].getText().equals(player)) ||
                (buttons[3].getText().equals(player) && buttons[4].getText().equals(player) && buttons[5].getText().equals(player)) ||
                (buttons[6].getText().equals(player) && buttons[7].getText().equals(player) && buttons[8].getText().equals(player)) ||
                (buttons[0].getText().equals(player) && buttons[3].getText().equals(player) && buttons[6].getText().equals(player)) ||
                (buttons[1].getText().equals(player) && buttons[4].getText().equals(player) && buttons[7].getText().equals(player)) ||
                (buttons[2].getText().equals(player) && buttons[5].getText().equals(player) && buttons[8].getText().equals(player)) ||
                (buttons[0].getText().equals(player) && buttons[4].getText().equals(player) && buttons[8].getText().equals(player)) ||
                (buttons[2].getText().equals(player) && buttons[4].getText().equals(player) && buttons[6].getText().equals(player));
    }

    private boolean isDraw() {
        for (JButton button : buttons) {
            if (button.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void xWins() {
        textfield.setText("X Wins!");
        endGame();
    }

    public void oWins() {
        textfield.setText("O Wins!");
        endGame();
    }

    public void draw() {
        textfield.setText("It's a Draw!");
        endGame();
    }

    private void endGame() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        resetButton.setEnabled(true); // Enable reset button
    }

    public void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(new Color(240, 240, 240)); // Reset to default
        }
        textfield.setText("XOX Game");
        resetButton.setEnabled(false);
        firstTurn();
    }
}