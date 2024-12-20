/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231105 - Mirza Fathi Taufiqurrahman
 * 2 - 5026231209 - Adityo Rafi Wardhana
 * 3 - 5026231198 - Muhammad Fikri Khalilullah
 */

import java.awt.*;

/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    public static final int SIZE = 120;
    public static final int PADDING = SIZE / 5;
    public static final int SEED_SIZE = SIZE - PADDING * 2;

    Seed content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    public void paint(Graphics g) {
        int x1 = col * SIZE + PADDING;
        int y1 = row * SIZE + PADDING;
        if (content != null && content.getImage() != null) {
            g.drawImage(content.getImage(), x1, y1, SEED_SIZE, SEED_SIZE, null);
        }
    }
}
