package entity;
//CreateTime: 2024-11-10 11:35 p.m.

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ImageConstants {
    public static final Image BLACKBISHOP =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/BishopBlack.png"))).getImage();
    public static final Image WHITEBISHOP =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/BishopWhite.png"))).getImage();
    public static final Image BLACKKING =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/KingBlack.png"))).getImage();
    public static final Image WHITEKING =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/KingWhite.png"))).getImage();
    public static final Image BLACKKNIGHT =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/KnightBlack.png"))).getImage();
    public static final Image WHITEKNIGHT =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/KnightWhiteResized.png"))).getImage();
    public static final Image BLACKPAWN =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/PawnBlack.png"))).getImage();
    public static final Image WHITEPAWN =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/PawnWhite.png"))).getImage();
    public static final Image BLACKQUEEN =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/QueenBlack.png"))).getImage();
    public static final Image WHITEQUEEN =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/QueenWhite.png"))).getImage();
    public static final Image BLACKROOK =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/RookBlack.png"))).getImage();
    public static final Image WHITEROOK =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/RookWhite.png"))).getImage();
    public static final Image PAUSEBUTTON =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/PauseButton.png"))).getImage();
    public static final Image STARTBUTTON =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/StartButton.png"))).getImage();
    public static final Image RESTARTBUTTON =
            new ImageIcon(Objects.requireNonNull(ImageConstants.class.getClassLoader().getResource(".images/RestartButton.png"))).getImage();
}