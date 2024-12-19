package org.roy.homepage.application;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.roy.settings.listener.extend.ButtonKeyListener;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;

/**
 *
 * @author Raven
 */
public class LoginForm extends JPanel {

    private static final long serialVersionUID = -937610385211497032L;
    private JButton cmdLogin;
    private JLabel lbPass;
    private JLabel lbTitle;
    private JPanel login;
    private JPasswordField txtPass;
    private static final Map<String,String> USERS = Map.of("Jessica0326","潔西卡","Lucky1110","艾倫","Li631123","Rita","PanPan520520","Joy");

	public LoginForm() {
        initComponents();
        initLoginLayout();
        initLayout();
        init();
    }

    private void init() {

        setLayout(new LoginFormLayout());
        login.setLayout(new LoginLayout());
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");
        login.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Login.background;"
                + "arc:20;"
                + "border:30,40,50,30");

        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:1;"
                + "focusWidth:2");
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "請輸入密碼");
    }

    private void initComponents() {

        login = new JPanel();
        cmdLogin = new JButton();
        lbTitle = new JLabel();
        lbPass = new JLabel();
        txtPass = new JPasswordField();
        txtPass.addKeyListener(new ButtonKeyListener(cmdLogin));

        cmdLogin.setText("登入");
        cmdLogin.addActionListener(this::cmdLoginActionPerformed);

        lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lbTitle.setText("登入");

        lbPass.setText("密碼");

    }

    public void txtPassWordRequestFocus() {
    	txtPass.requestFocus();
    }

    private void cmdLoginActionPerformed(ActionEvent e) {
    	String password;
    	if(USERS.containsKey(password = String.valueOf(txtPass.getPassword()))) {
    		GlamChamber.login();
    		GlamChamber.userName = USERS.get(password);
    		txtPass.setText("");
    	}else {
    		JOptionPane.showMessageDialog(null,"密碼錯誤");
    	}
    }

	private void initLayout() {
		GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(204, 204, 204)
                .addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(319, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(login, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );
	}

	private void initLoginLayout() {
		GroupLayout loginLayout = new GroupLayout(login);
        loginLayout.setHorizontalGroup(
        	loginLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(loginLayout.createSequentialGroup()
        			.addGroup(loginLayout.createParallelGroup(Alignment.LEADING)
        				.addGroup(loginLayout.createSequentialGroup()
        					.addContainerGap()
        					.addGroup(loginLayout.createParallelGroup(Alignment.LEADING)
        						.addComponent(lbTitle, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        						.addGroup(loginLayout.createSequentialGroup()
        							.addGroup(loginLayout.createParallelGroup(Alignment.LEADING)
        								.addComponent(lbPass)
        								.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        							.addGap(0, 0, Short.MAX_VALUE))))
        				.addGroup(loginLayout.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(cmdLogin)))
        			.addContainerGap())
        );
        loginLayout.setVerticalGroup(
        	loginLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(loginLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lbTitle)
        			.addGap(0)
        			.addComponent(lbPass)
        			.addGap(5)
        			.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(50)
        			.addComponent(cmdLogin)
        			.addContainerGap())
        );
        login.setLayout(loginLayout);
	}

    private class LoginFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                int width = parent.getWidth();
                int height = parent.getHeight();
                int loginWidth = UIScale.scale(320);
                int loginHeight = login.getPreferredSize().height;
                int x = (width - loginWidth) / 2;
                int y = (height - loginHeight) / 2;
                login.setBounds(x, y, loginWidth, loginHeight);
            }
        }
    }

    private class LoginLayout implements LayoutManager {

        private final int titleGap = 10;
        private final int textGap = 10;
        private final int labelGap = 5;
        private final int buttonGap = 20;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int height = insets.top + insets.bottom;

                height += lbTitle.getPreferredSize().height;
                height += UIScale.scale(titleGap);
//                height += lbUser.getPreferredSize().height;
                height += UIScale.scale(labelGap);
//                height += txtUser.getPreferredSize().height;
                height += UIScale.scale(textGap);

                height += lbPass.getPreferredSize().height;
                height += UIScale.scale(labelGap);
                height += txtPass.getPreferredSize().height;
                height += UIScale.scale(buttonGap);
                height += cmdLogin.getPreferredSize().height;
                return new Dimension(0, height);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);

                lbTitle.setBounds(x, y, width, lbTitle.getPreferredSize().height);
                y += lbTitle.getPreferredSize().height + UIScale.scale(titleGap);

//                lbUser.setBounds(x, y, width, lbUser.getPreferredSize().height);
//                y += lbUser.getPreferredSize().height + UIScale.scale(labelGap);
//                txtUser.setBounds(x, y, width, txtUser.getPreferredSize().height);
//                y += txtUser.getPreferredSize().height + UIScale.scale(textGap);

                lbPass.setBounds(x, y, width, lbPass.getPreferredSize().height);
                y += lbPass.getPreferredSize().height + UIScale.scale(labelGap);
                txtPass.setBounds(x, y, width, txtPass.getPreferredSize().height);
                y += txtPass.getPreferredSize().height + UIScale.scale(buttonGap);

                cmdLogin.setBounds(x, y, width, cmdLogin.getPreferredSize().height);
            }
        }
    }

}
