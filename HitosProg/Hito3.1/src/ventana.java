import javax.swing.*;
import java.awt.*;

public class ventana extends JFrame {
    public ventana() {
        JFrame frame = new JFrame("Hito 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10,10));


        JPanel panelN = new JPanel();
        panelN.setLayout(new FlowLayout());

        JPanel panelE = new JPanel();
        panelE.setSize(250,0);
        panelE.setLayout(new BoxLayout(panelE,BoxLayout.Y_AXIS));

        JPanel panelS = new JPanel();
        panelS.setLayout(new BoxLayout(panelS,BoxLayout.X_AXIS));

        JPanel panelC = new JPanel();
        panelC.setLayout(new GridLayout(2,2));

        JCheckBox checkBox1 = new JCheckBox("Katniss");
        JCheckBox checkBox2 = new JCheckBox("Peeta");
        panelN.add(checkBox1);
        panelN.add(checkBox2);

        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel(new ImageIcon("Hito3.1\\src\\Payasin.jpg"));
            panelC.add(label);
        }

        JRadioButton radio1 = new JRadioButton("OPT 1");
        JRadioButton radio2 = new JRadioButton("OPT 2");
        JRadioButton radio3 = new JRadioButton("OPT 3");
        JRadioButton[] radioButtons = {radio1,radio2,radio3};
        ButtonGroup buttonGroup = new ButtonGroup();

        panelE.add(Box.createVerticalGlue());
        for (int i = 0; i < radioButtons.length; i++) {
            buttonGroup.add(radioButtons[i]);
            panelE.add(radioButtons[i]);
        }
        panelE.add(Box.createVerticalGlue());
        radioButtons[0].setSelected(true);

        JButton boton1 = new JButton("But 1");
        JButton boton2 = new JButton("But 2");
        panelS.add(boton1);
        panelS.add(boton2);

        frame.add(panelN, BorderLayout.NORTH);
        frame.add(panelE,BorderLayout.EAST);
        frame.add(panelS, BorderLayout.SOUTH);
        frame.add(panelC, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.pack();

    }
}
