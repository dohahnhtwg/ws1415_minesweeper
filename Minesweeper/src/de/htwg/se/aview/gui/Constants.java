/* This file is part of Minesweeper.
 * 
 * Minesweeper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Minesweeper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Minesweeper.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.htwg.se.aview.gui;

import javax.swing.ImageIcon;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.Image;

/**
 * A class which provides all Constants needed by the GUI.
 * @author Dominik Hahn & Pavel Kravetskiy
 *
 */
public final class Constants {
    public static final int DEF_BUT_SIZEX = 100;
    public static final int DEF_BUT_SIZEY = 50;
    public static final int DEFBUTTONSIZE = 35;
    public static final int DEFDIMENSIONX = 10;
    public static final int DEFDIMENSIONY = 10;
    public static final int FONTSIZE = 20;
    public static final int LARGESIZE = 30;
    public static final int LARGEMINES = 99;
    public static final int MEDIUMSIZE = 16;
    public static final int MEDIUMMINES = 40;
    public static final int ONE = 1;
    public static final int SMALLSIZE = 9;
    public static final int SMALLMINES = 10;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int ZERO = 0;
    public static final int BOUNDS = 300;
    private static ImageIcon[] icons = null;
    private static ImageIcon mineIcon;
    private static ImageIcon exploded_mineIcon;
    private static String[] imgPaths = {
            "/images/flag.png",
            "/images/question_mark.png",
            null};

    private Constants() {}

    static {
        icons = new ImageIcon[imgPaths.length];
        for (int i= 0; i < imgPaths.length - 1; i++) {
            ImageIcon icon = new ImageIcon(Constants.class.getResource(imgPaths[i]));
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(Constants.DEFBUTTONSIZE - 10,
                    Constants.DEFBUTTONSIZE - 10,
                    SCALE_SMOOTH);
            icons[i] = new ImageIcon(newimg);
        }

        ImageIcon icon = new ImageIcon(Constants.class.getResource("/images/minesweepericon.png"));
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(Constants.DEFBUTTONSIZE - 10,
                Constants.DEFBUTTONSIZE - 10,
                SCALE_SMOOTH);
        mineIcon = new ImageIcon(newimg);

        icon = new ImageIcon(Constants.class.getResource("/images/minesweepericon_exploded.png"));
        img = icon.getImage();
        newimg = img.getScaledInstance(Constants.DEFBUTTONSIZE - 10,
                Constants.DEFBUTTONSIZE - 10,
                SCALE_SMOOTH);
        exploded_mineIcon = new ImageIcon(newimg);
    }

    static ImageIcon getIcon(int index) {
        if (index == imgPaths.length) {
            return null;
        }
        return Constants.icons[index % imgPaths.length];
    }

    static ImageIcon getMineIcon() {
        return mineIcon;
    }

    public static ImageIcon getExplodedMineIcon() {
        return exploded_mineIcon;
    }
}
