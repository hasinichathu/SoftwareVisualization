/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello3D.test;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.texture.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import static javax.swing.SwingConstants.PREVIOUS;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Hasini
 */
public class LayoutGenerator {

    private static JmeCanvasContext context;
    private static Canvas canvas;
    private static JFrame frame;
    private static Container viewer, hierarchy;
    private static Container currentPanel;
    private static JToolBar toolBar;
    private Island island;

    public LayoutGenerator(Island island) {
        this.island = island;
    }

    void swingHandler() {
        JmeFormatter formatter = new JmeFormatter();

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);

        Logger.getLogger("").removeHandler(Logger.getLogger("").getHandlers()[0]);
        Logger.getLogger("").addHandler(consoleHandler);
        //HelloWorld appClass = new HelloWorld();
        createCanvas(island);

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPopupMenu.setDefaultLightWeightPopupEnabled(false);

                createFrame(island);
                currentPanel.add(canvas, BorderLayout.CENTER);
                frame.pack();
                startApp(island);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }

    public static void createCanvas(Island appClass) {
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

    private static void createFrame(Island app) {
        frame = new JFrame("Code Champ");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                app.stop();
            }
        });

        createToolBar();
        createPanes();
        createMenu(app);

    }

    private static void createToolBar() {
        toolBar = new JToolBar("Still draggable");

        frame.add(toolBar, BorderLayout.NORTH);
        addButtons();
    }

    private static void createPanes() {

        viewer = new JPanel();
        viewer.setLayout(new BorderLayout());

        hierarchy = new JPanel();
        hierarchy.setLayout(new BorderLayout());
        createHierarchy();

        frame.getContentPane().add(viewer);
        frame.getContentPane().add(hierarchy, BorderLayout.WEST);

        currentPanel = viewer;
    }

    private static void createHierarchy() {
        Integer[] data = {10,20,30,4000};
        JList list = new JList(data); //data has type Object[]
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        hierarchy.add(list);
    }

    private static void createMenu(Island app) {
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
    }

    public static void startApp(Island app) {
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

    private static void addButtons() {
        JButton button1 = null;
        JButton button2 = null;
        JButton button3 = null;

        //first button2
        button1 = makeNavigationButton("reload",
                "Back to previous something-or-other",
                "Previous");
        toolBar.add(button1);

        //second button2
        button2 = makeNavigationButton("secrity2", 
                "Up to something-or-other",
                "Up");
        toolBar.add(button2);
        
        button3 = makeNavigationButton("reuse", 
                "Up to something-or-other",
                "Up");
        toolBar.add(button3);

    }

    private static JButton makeNavigationButton(String imageName,
            String toolTipText,
            String altText) {
        //Look for the image.
        String imgLocation = "F:/Uni/4th_year/project/icons/"
                + imageName
                + ".png";
        //URL imageURL = LayoutGenerator.class.getResource(imgLocation);
        //URL imageURL = null;
        //Create and initialize the button2.
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(40, 40));
        //button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        //button.addActionListener(this);

        if (imgLocation != null) {                      //image found
            ImageIcon imageIcon= new ImageIcon(imgLocation, altText);
            java.awt.Image newimg = imageIcon.getImage().getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);  
            button.setIcon(imageIcon);
        } else {                                     //no image found
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }

}
