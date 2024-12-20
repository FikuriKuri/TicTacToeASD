/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231105 - Mirza Fathi Taufiqurrahman
 * 2 - 5026231209 - Adityo Rafi Wardhana
 * 3 - 5026231198 - Muhammad Fikri Khalilullah
 */

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display image icon (text or image) for the items.
 *   and define the related variable/constructor/getter.
 * To draw the image:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 *
 * Ideally, we should define two enums with inheritance, which is,
 *  however, not supported.
 */
public enum Seed {
    CROSS("X", "gif1.png"),
    NOUGHT("O", "gif2.jpg"),
    NO_SEED(" ", null);

    private String displayName;
    private Image img = null;

    Seed(String name, String imageFilename) {
        this.displayName = name;
        if (imageFilename != null) {
            URL imgURL = getClass().getClassLoader().getResource(imageFilename);
            if (imgURL != null) {
                img = new ImageIcon(imgURL).getImage();
            } else {
                System.err.println("Image not found for: " + imageFilename);
            }
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public Image getImage() {
        return img;
    }
}