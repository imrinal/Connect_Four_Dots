import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour extends JFrame {
    private final int ROWS = 6;
    private final int COLS = 7;
    private JButton[] buttons;
    private JPanel boardPanel;
    private int[][] board;
    private boolean player1Turn = true;

    public ConnectFour() {
        setTitle("Connect Four");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buttons = new JButton[COLS];
        board = new int[ROWS][COLS];

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, COLS));
        for (int i = 0; i < COLS; i++) {
            final int col = i;
            buttons[i] = new JButton("Drop");
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dropDisc(col);
                }
            });
            buttonPanel.add(buttons[i]);
        }

        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));
        for (int i = 0; i < ROWS * COLS; i++) {
            boardPanel.add(new JLabel());
        }

        add(buttonPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
    }

    private void dropDisc(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col] == 0) {
                board[row][col] = player1Turn ? 1 : 2;
                updateBoard();
                if (checkWin()) {
                    JOptionPane.showMessageDialog(this, "Player " + (player1Turn ? "1" : "2") + " wins!");
                    resetBoard();
                }
                player1Turn = !player1Turn;
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Column is full!");
    }

    private void updateBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JLabel label = (JLabel) boardPanel.getComponent(row * COLS + col);
                if (board[row][col] == 1) {
                    label.setOpaque(true);
                    label.setBackground(Color.RED);
                } else if (board[row][col] == 2) {
                    label.setOpaque(true);
                    label.setBackground(Color.YELLOW);
                } else {
                    label.setOpaque(false);
                    label.setBackground(null);
                }
            }
        }
        boardPanel.repaint();
    }

    private boolean checkWin() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] != 0) {
                    if (checkDirection(row, col, 1, 0) ||
                        checkDirection(row, col, 0, 1) ||
                        checkDirection(row, col, 1, 1) ||
                        checkDirection(row, col, 1, -1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol) {
        int count = 0;
        int player = board[row][col];
        for (int i = 0; i < 4; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS && board[newRow][newCol] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

    private void resetBoard() {
        board = new int[ROWS][COLS];
        updateBoard();
        player1Turn = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectFour().setVisible(true);
            }
        });
    }
}