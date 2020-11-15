/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello3D.test;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.util.JmeFormatter;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import static java.awt.Event.UP;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import static javax.swing.SwingConstants.PREVIOUS;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Hasini
 */
public class Swing {

    private static JmeCanvasContext context;
    private static Canvas canvas;
    private static JFrame frame;
    private static Container viewer, hierarchy;
    private static Container currentPanel;
    //private static JTabbedPane tabbedPane;
//

    void swingHandler(HelloWorld appClass) {
        JmeFormatter formatter = new JmeFormatter();

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        Logger.getLogger("").removeHandler(Logger.getLogger("").getHandlers()[0]);
        Logger.getLogger("").addHandler(consoleHandler);
        //HelloWorld appClass = new HelloWorld();
        createCanvas(appClass);

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPopupMenu.setDefaultLightWeightPopupEnabled(false);

                createFrame(appClass);
                currentPanel.add(canvas, BorderLayout.CENTER);
                frame.pack();
                startApp(appClass);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }

    public static void createCanvas(HelloWorld appClass) {
        AppSettings settings = new AppSettings(true);
        settings.setWidth(640);
        settings.setHeight(480);

        appClass.setPauseOnLostFocus(false);
        appClass.setSettings(settings);
        appClass.createCanvas();
        appClass.startCanvas();

        context = (JmeCanvasContext) appClass.getContext();
        canvas = context.getCanvas();
        canvas.setSize(settings.getWidth(), settings.getHeight());
        
        
    }

    private static void createFrame(HelloWorld app) {
        frame = new JFrame("Code Champ");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                app.stop();
            }
        });

        createPanes();
        createMenu(app);
        
    }

    private static void createPanes() {
        JToolBar toolBar = new JToolBar("Still draggable");
        JButton newGameBtn = new JButton("New game");
        JButton regretBtn = new JButton("Regret move");
        toolBar.add(newGameBtn);
        toolBar.add(regretBtn);
        
        viewer = new JPanel();
        viewer.setLayout(new BorderLayout());

        hierarchy = new JPanel();
        hierarchy.setLayout(new BorderLayout());
        hierarchy.add(toolBar);
        
        

        frame.getContentPane().add(viewer);
        frame.getContentPane().add(hierarchy, BorderLayout.WEST);
        //frame.getContentPane().add(toolBar);
        currentPanel = viewer;
    }

    private static void createMenu(HelloWorld app) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu menuTortureMethods = new JMenu("File");
        menuBar.add(menuTortureMethods);

        final JMenuItem itemRemoveCanvas = new JMenuItem("Remove Canvas");
        menuTortureMethods.add(itemRemoveCanvas);
        itemRemoveCanvas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (itemRemoveCanvas.getText().equals("Remove Canvas")) {
                    currentPanel.remove(canvas);

                    itemRemoveCanvas.setText("Add Canvas");
                } else if (itemRemoveCanvas.getText().equals("Add Canvas")) {
                    currentPanel.add(canvas, BorderLayout.CENTER);

                    itemRemoveCanvas.setText("Remove Canvas");
                }
            }
        });

        final JMenuItem itemHideCanvas = new JMenuItem("Hide Canvas");
        menuTortureMethods.add(itemHideCanvas);
        itemHideCanvas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (itemHideCanvas.getText().equals("Hide Canvas")) {
                    canvas.setVisible(false);
                    itemHideCanvas.setText("Show Canvas");
                } else if (itemHideCanvas.getText().equals("Show Canvas")) {
                    canvas.setVisible(true);
                    itemHideCanvas.setText("Hide Canvas");
                }
            }
        });

        final JMenuItem itemSwitchTab = new JMenuItem("Switch to tab #2");
        menuTortureMethods.add(itemSwitchTab);
        itemSwitchTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (itemSwitchTab.getText().equals("Switch to tab #2")) {
                    viewer.remove(canvas);
                    hierarchy.add(canvas, BorderLayout.CENTER);
                    currentPanel = hierarchy;
                    itemSwitchTab.setText("Switch to tab #1");
                } else if (itemSwitchTab.getText().equals("Switch to tab #1")) {
                    hierarchy.remove(canvas);
                    viewer.add(canvas, BorderLayout.CENTER);
                    currentPanel = viewer;
                    itemSwitchTab.setText("Switch to tab #2");
                }
            }
        });

        JMenuItem itemSwitchLaf = new JMenuItem("Switch Look and Feel");
        menuTortureMethods.add(itemSwitchLaf);
        itemSwitchLaf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(frame);
                frame.pack();
            }
        });

        JMenuItem itemSmallSize = new JMenuItem("Set size to (0, 0)");
        menuTortureMethods.add(itemSmallSize);
        itemSmallSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension preferred = frame.getPreferredSize();
                frame.setPreferredSize(new Dimension(0, 0));
                frame.pack();
                frame.setPreferredSize(preferred);
            }
        });

        JMenuItem itemKillCanvas = new JMenuItem("Stop/Start Canvas");
        menuTortureMethods.add(itemKillCanvas);
        itemKillCanvas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                currentPanel.remove(canvas);
                app.stop(true);

                createCanvas(app);
                currentPanel.add(canvas, BorderLayout.CENTER);
                frame.pack();
                startApp(app);
            }
        });

        JMenuItem itemExit = new JMenuItem("Exit");
        menuTortureMethods.add(itemExit);
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.dispose();
                app.stop();
            }
        });
        
        createTaskBar(app);
    }

    public static void startApp(HelloWorld app) {
        app.startCanvas();
        app.enqueue(new Callable<Void>() {
            @Override
            public Void call() {
                if (app instanceof SimpleApplication) {
                    SimpleApplication simpleApp = (SimpleApplication) app;
                    simpleApp.getFlyByCamera().setDragToRotate(true);
                    simpleApp.getFlyByCamera().setMoveSpeed(50);
                }
                return null;
            }
        });

    }

    private static void createTaskBar(HelloWorld app) {
        //super(new BorderLayout());

        JToolBar toolBar = new JToolBar("Still draggable");
        addButtons(toolBar);

        //toolBar.setPreferredSize(new Dimension(450, 130));
        //toolBar.add(toolBar, BorderLayout.PAGE_START);
        //toolBar.add(scrollPane, BorderLayout.CENTER);
    }

    private static void addButtons(JToolBar toolBar) {
        JButton button = null;
        JButton button1 = new JButton();
        //first button
        button = makeNavigationButton("Back24", PREVIOUS,
                "Back to previous something-or-other",
                "Previous");
        toolBar.add(button1);

        //second button
        button = makeNavigationButton("Up24", UP,
                "Up to something-or-other",
                "Up");
        toolBar.add(button);
        
    }

    private static JButton makeNavigationButton(String imageName,
            int actionCommand,
            String toolTipText,
            String altText) {
        //Look for the image.
        String imgLocation = "images/"
                + imageName
                + ".gif";
        //URL imageURL = ToolBarDemo.class.getResource(imgLocation);
        URL imageURL = null;
        //Create and initialize the button.
        JButton button = new JButton();
        //button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        //button.addActionListener(this);

        if (imageURL != null) {                      //image found
            //button.setIcon(new ImageIcon(imageURL, altText));
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }

}
