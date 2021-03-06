package Controller;

import ImageProcessor.ImageProcessor;
import View.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Vladislav on 26.04.16.
 */
public class Controller {
    public static double DEFAULT_SIZE_MULTIPLIER = 0.2;
    private MainFrame mainFrame;
    private BufferedImage image;
    private double sizeMultiplier;

    public Controller() {
        this.mainFrame = new MainFrame();
        this.mainFrame.showFrame();

        this.loadImage("michael.jpg");

        this.sizeMultiplier = 1;

//        resizeImage(1000, 1000);
        this.mainFrame.addPlusButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = image.getWidth();
                int height = image.getHeight();
                sizeMultiplier += DEFAULT_SIZE_MULTIPLIER;
                System.out.println(sizeMultiplier);
                resizeImage((int)(width * sizeMultiplier), (int)(height * sizeMultiplier));
            }
        });
        this.mainFrame.addMinusButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = image.getWidth();
                int height = image.getHeight();
                sizeMultiplier -= DEFAULT_SIZE_MULTIPLIER;
                System.out.println(sizeMultiplier);
                resizeImage((int)(width * sizeMultiplier), (int)(height * sizeMultiplier));
            }
        });
        this.mainFrame.addRotateButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageProcessor imageProcessor = new ImageProcessor(image);
                imageProcessor.rotateImage(mainFrame.getAngle());
                mainFrame.setImage(imageProcessor.getImage());
            }
        });
    }

    public void loadImage(String imageName) {
        this.image = null;
        try
        {
            image = ImageIO.read(new File(imageName));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        this.mainFrame.setImage(this.image);
    }
    public void resizeImage(int newWidth, int newHeight) {
        ImageProcessor imageProcessor = new ImageProcessor(this.image);
        imageProcessor.resizeImage(newWidth, newHeight);
        this.mainFrame.setImage(imageProcessor.getImage());
    }
    public void rotateImage() {
        this.mainFrame.addMouseListener(new MouseAdapter() {
            int alpha = 0;
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON3) {
                    alpha -= 5;
                }
                if (e.getButton() == MouseEvent.BUTTON1) {
                    alpha += 5;
                }
                if (alpha >= 360 || alpha <= -360) {
                    alpha = Math.abs(alpha) - 360;
                }

                ImageProcessor imageProcessor = new ImageProcessor(image);
                imageProcessor.rotateImage(alpha);
                mainFrame.setImage(imageProcessor.getImage());
            }
        });
    }
}
