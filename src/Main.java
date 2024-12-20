import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: AI vs Human Graphic version.
 * The Board and Cell classes are separated in their own classes.
 */
public class Main extends JPanel {
    private static final long serialVersionUID = 1L;

    // Constants for drawing graphics
    public static final String TITLE = "Tic Tac Toe AI vs Human";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // Game components
    private Board board; // Game board
    private State currentState; // Current state of the game
    private Seed currentPlayer; // Current player
    private JLabel statusBar; // Status bar to display messages

    private AIPlayer aiPlayer; // AI Player

    private int crossWins = 0;
    private int noughtWins = 0;


    /** Constructor to setup the UI and game components */
    public Main() {
        // Add mouse listener for player interaction
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (currentPlayer == Seed.CROSS) { // Manusia
                        if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                                && board.cells[row][col].content == Seed.NO_SEED) {
                            currentState = board.stepGame(currentPlayer, row, col);
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                            repaint();
                            SoundEffect.chick.play(); //Suara player 1

                            // Trigger AI move if game is not over
                            if (currentState == State.PLAYING) {
                                aiMove();
                                SoundEffect.dog_barking.play();
                            }
                        }
                    }
                } else {
                    newGame();
                }
                repaint();
            }
        });

        // Set up status bar
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END);
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2));

        initGame();
        newGame();
    }

    /** Initialize the game components */
    private void initGame() {
        board = new Board();
        aiPlayer = new AIPlayerMinimax(board);
        aiPlayer.setSeed(Seed.NOUGHT); // AI plays as O
    }

    /** Start a new game */
    private void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED;
            }
        }
        currentPlayer = Seed.CROSS; // Human starts as X
        currentState = State.PLAYING;
    }

    /** Perform the AI's move */
    private void aiMove() {
        int[] move = aiPlayer.move();
        if (move != null) {
            currentState = board.stepGame(Seed.NOUGHT, move[0], move[1]);
            currentPlayer = Seed.CROSS;
        }
    }

    /** Custom painting for the game board */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG);
        board.paint(g);
        updateStatusBar();
    }

    private void updateStatusBar() {
        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "Chick's Turn" : "Dog's Turn (AI)");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
            SoundEffect.game_draw.play();
        } else {
            statusBar.setForeground(Color.RED);
            statusBar.setText((currentState == State.CROSS_WON) ? "'Chick' Won! Click to play again." : "'Dog' Won (AI)! Click to play again.");
            SoundEffect.game_over.play();
        }
        statusBar.setText(String.format("'X' Wins: %d | 'O' Wins: %d | %s",
                crossWins, noughtWins, (currentState == State.PLAYING ?
                        ((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn") :
                        (currentState == State.DRAW ? "It's a Draw! Click to play again." :
                                ((currentState == State.CROSS_WON) ? "'X' Won!" : "'O' Won!")))));


        if (currentState == State.CROSS_WON) {
            crossWins++;
        } else if (currentState == State.NOUGHT_WON) {
            noughtWins++;
        }

    }

    /** Main method to launch the game */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new Main());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
