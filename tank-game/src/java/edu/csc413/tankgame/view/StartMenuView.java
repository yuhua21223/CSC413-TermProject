package edu.csc413.tankgame.view;

import edu.csc413.tankgame.GameDriver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import edu.csc413.tankgame.GameDriver.*;


/**
 * StartMenuView is the view representing the start menu screen as well as the end menu screen. The two menu screens are
 * extremely similar, as they only have one minor difference -- the text of the first button showing as "Start Game" vs
 * "Restart Game".
 *
 * StartMenuView is responsible for setting up the start game and exit buttons and their corresponding listeners so that
 * clicking on the buttons will lead to the appropriate corresponding actions.
 */
public class StartMenuView extends JPanel {
    public static final Dimension SCREEN_DIMENSIONS = new Dimension(510, 550);
    private static final String START_MENU_IMAGE_FILE = "title.png";

    private static final Dimension BUTTON_SIZE = new Dimension(200, 100);
    private static final Font BUTTON_FONT = new Font("Consolas", Font.BOLD, 22);
    private static final Rectangle START_BUTTON_BOUNDS = new Rectangle(150, 300, 200, 50);
    private static final Rectangle EXIT_BUTTON_BOUNDS = new Rectangle(150, 400, 200, 50);

    public static final String START_BUTTON_ACTION_COMMAND = "start_ac";
    public static final String EXIT_BUTTON_ACTION_COMMAND = "exit_ac";

    private final BufferedImage menuBackground;

    // TODO: Implement.
    // You'll need to provide a way for GameDriver to respond to button presses in this view. Note that below, we add
    // null ActionListeners to the buttons, which don't do anything. How can we change that to be an ActionListener that
    // directs us back to the code in GameDriver?
    public StartMenuView(String startButtonText) {
        URL imageUrl = getClass().getClassLoader().getResource(START_MENU_IMAGE_FILE);
        if (imageUrl == null) {
            throw new RuntimeException("Unable to create an image URL from: " + START_MENU_IMAGE_FILE);
        }
        try {
            menuBackground = ImageIO.read(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setBackground(Color.BLACK);
        setLayout(null);

//        Example use of lister (Do not use listener object below
        //PrintListener1  listener1 = new PrintListener1();

        //GameDriver listener = new GameDriver();

        PrintListener listener = new PrintListener();


        addButton(startButtonText, START_BUTTON_BOUNDS, START_BUTTON_ACTION_COMMAND, listener);
        addButton("Exit", EXIT_BUTTON_BOUNDS, EXIT_BUTTON_ACTION_COMMAND, listener);
    }

    private void addButton(
            String buttonText, Rectangle buttonBounds, String buttonActionCommand, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setSize(BUTTON_SIZE);
        button.setFont(BUTTON_FONT);
        button.setBounds(buttonBounds);
        button.setActionCommand(buttonActionCommand);
        button.addActionListener(actionListener);
        add(button);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(menuBackground, 0, 0, null);
    }


    //This is the start menu class and should not be in control of of the exit menu,
    // Thus it should not be placed here

    //Example of how actionListener works
//    private static class PrintListener1 implements ActionListener {
//
//        //obersever pattern
//        @Override
//        public void actionPerformed(ActionEvent event) {
//            String actionCommand = event.getActionCommand();
//            if (actionCommand.equals(START_BUTTON_ACTION_COMMAND)) {
//                mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
//                runGame();
//                System.out.println("Start Button was pressed");
//            } else if (actionCommand.equals(EXIT_BUTTON_ACTION_COMMAND)) {
//                mainView.closeGame();
//                System.out.println("Exit button was pressed");
//            }
//
//        }
//    }

}
