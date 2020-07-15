package com.hexaTech.application.Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import com.hexaTech.adapter.interfaceadapter.MyObserver;
import com.hexaTech.adapter.interfaceadapter.ViewManualController;
import com.hexaTech.adapter.interfaceadapter.ViewManualPresenter;
import com.hexaTech.adapter.interfaceadapter.design.DesignController;
import com.hexaTech.adapter.interfaceadapter.design.DesignPresenter;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopController;
import com.hexaTech.adapter.interfaceadapter.develop.DevelopPresenter;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverController;
import com.hexaTech.adapter.interfaceadapter.discover.DiscoverPresenter;
import com.hexaTech.application.config.SpringConfig;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

@Component
public class MainGui implements MyObserver {
    private JButton guideButton;
    private JButton developButton;
    private JButton designButton;
    private JButton discoverButton;
    private JPanel homePanel;

    private final DiscoverController discoverController;
    private final DesignController designController;
    private final DevelopController developController;
    private final DiscoverPresenter discoverPresenter;
    private final DesignPresenter designPresenter;
    private final DevelopPresenter developPresenter;
    private final ViewManualController viewManualController;
    private final ViewManualPresenter viewManualPresenter;

    private JFrame window;
    private DiscoverNavigation discoverNavigation;
    private DesignWindow designWindow;
    private DevelopWindow developWindow;

    private String stringManual;


    @Autowired
    public MainGui(DiscoverController discoverController, DesignController designController,
                   DevelopController developController, ViewManualController viewManualController,
                   DiscoverPresenter discoverPresenter, DesignPresenter designPresenter,
                   DevelopPresenter developPresenter, ViewManualPresenter viewManualPresenter) throws IOException {

        this.discoverController = discoverController;
        this.designController = designController;
        this.developController = developController;
        this.viewManualController = viewManualController;

        this.discoverPresenter = discoverPresenter;
        this.designPresenter = designPresenter;
        this.developPresenter = developPresenter;
        this.viewManualPresenter = viewManualPresenter;

        this.discoverPresenter.addObserver(this);
        this.designPresenter.addObserver(this);
        this.developPresenter.addObserver(this);
        this.viewManualPresenter.addObserver(this);

        this.discoverNavigation = new DiscoverNavigation(this, discoverController, viewManualController, discoverPresenter, viewManualPresenter);
        this.designWindow = new DesignWindow(this, designController, designPresenter, viewManualController, viewManualPresenter);
        this.developWindow = new DevelopWindow(this, developController, developPresenter, viewManualController, viewManualPresenter);

        //JFrame.setDefaultLookAndFeelDecorated(true);

        this.window = new JFrame();
        window.setPreferredSize(new Dimension(800, 600));
        final URL url = Thread.currentThread().getContextClassLoader().getResource("style/logo.png");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        window.setFont(new FontUIResource("Verdana", Font.PLAIN, 22));
        window.pack();
        window.setLocationRelativeTo(null);

        /*this.homePanel = new JPanel();
        final URL url_background = Thread.currentThread().getContextClassLoader().getResource("style/logo.png");
        ImageIcon background = new ImageIcon(url_background);
        JLabel thumb = new JLabel();
        thumb.setIcon(background);*/

        discoverButton.setBackground(new Color(0x3a529f));
        designButton.setBackground(new Color(0x3a529f));
        developButton.setBackground(new Color(0x3a529f));
        guideButton.setBackground(new Color(0x3a529f));

        //window.add(thumb);
        window.add(homePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("NaturalAPI");
        window.pack();
        window.setVisible(true);

        discoverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                discoverButton.setBackground(new Color(27, 31, 234));
                //  discoverButton.setBorderPainted(false);
            }
        });
        discoverButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                discoverButton.setBackground(new Color(0x3a529f));
                //  discoverButton.setBorderPainted(true);
            }
        });

        discoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.add(discoverNavigation);
                homePanel.setVisible(false);
                discoverNavigation.setVisible(true);
            }
        });

        designButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                designButton.setBackground(new Color(27, 31, 234));
                // designButton.setBorderPainted(false);
            }
        });
        designButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                designButton.setBackground(new Color(0x3a529f));
                // designButton.setBorderPainted(true);
            }
        });

        designButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.add(designWindow);
                homePanel.setVisible(false);
                designWindow.setVisible(true);
                try {
                    designWindow.checkForSavedDocs();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        developButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                developButton.setBackground(new Color(27, 31, 234));
                // developButton.setBorderPainted(false);
            }
        });
        developButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                developButton.setBackground(new Color(0x3a529f));
                // developButton.setBorderPainted(true);
            }
        });

        developButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.add(developWindow);
                homePanel.setVisible(false);
                developWindow.setVisible(true);
                try {
                    developWindow.checkForSavedDocs();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        guideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                guideButton.setBackground(new Color(27, 31, 234));
                // guideButton.setBorderPainted(false);
            }
        });
        guideButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                guideButton.setBackground(new Color(0x5e78fa));
                // guideButton.setBorderPainted(true);
            }
        });

        guideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    viewManualController.openManual();
                    viewManualController.openManualSection("MAIN:");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(window,
                        stringManual);
            }
        });

    }

    ;

    @Override
    public void notifyMeDiscover() {

    }

    @Override
    public boolean notifyMeDoneDiscover() {
        return false;
    }

    @Override
    public void notifyMeDesign() {

    }

    @Override
    public boolean notifyMeDoneDesign() {
        return false;
    }

    @Override
    public void notifyMeDevelop() {

    }

    @Override
    public int notifyMeErrorDevelop() {
        return 0;
    }

    @Override
    public boolean notifyMeDoneDevelop() {
        return false;
    }

    @Override
    public void notifyMeManual() {
        stringManual = viewManualPresenter.getMessage();
    }


    public JFrame getHomeWindow() {
        return window;
    }

    public JPanel getHomePanel() {
        return homePanel;
    }

    public void showGUI() {
        window = new JFrame("MainGui");
        //AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        //window.setContentPane(context.getBean("mainGui", MainGui.class).homePanel);
        window.add(homePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
        /*
        //Create and set up the window.
        JFrame frame = new JFrame("MainGui");
        frame.setContentPane(new MainGui().homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        //frame.add(homePanel);
        frame.setPreferredSize(new Dimension(800,500));

        //set logo
        final URL url = Thread.currentThread().getContextClassLoader().getResource("images/logo.png");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        homePanel = new JPanel();
        homePanel.setLayout(new GridLayoutManager(5, 1, new Insets(0, 0, 0, 0), -1, -1));
        homePanel.setBackground(new Color(-1));
        guideButton = new JButton();
        guideButton.setBackground(new Color(-1));
        guideButton.setEnabled(true);
        guideButton.setForeground(new Color(-1));
        guideButton.setHorizontalTextPosition(0);
        guideButton.setText("Guide");
        homePanel.add(guideButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(5, -1), null, null, 0, false));
        developButton = new JButton();
        developButton.setBackground(new Color(-1));
        developButton.setEnabled(true);
        developButton.setForeground(new Color(-1));
        developButton.setHorizontalTextPosition(0);
        developButton.setText("Develop");
        homePanel.add(developButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(5, -1), null, null, 0, false));
        designButton = new JButton();
        designButton.setBackground(new Color(-1));
        designButton.setEnabled(true);
        designButton.setForeground(new Color(-1));
        designButton.setHorizontalTextPosition(0);
        designButton.setText("Design");
        homePanel.add(designButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(5, -1), null, null, 0, false));
        discoverButton = new JButton();
        discoverButton.setBackground(new Color(-1));
        discoverButton.setEnabled(true);
        discoverButton.setForeground(new Color(-1));
        discoverButton.setHorizontalTextPosition(0);
        discoverButton.setText("Discover");
        homePanel.add(discoverButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(5, -1), null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel1.setBackground(new Color(-6527012));
        homePanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return homePanel;
    }

}
