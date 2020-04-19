package managers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NorthPanel extends JPanel
{
    private JButton BtnStart;
    private JButton BtnPause;
    private JButton BtnCheat;
    NorthListener Listener;
    GameManager ManagerRef;
    public NorthPanel(GameManager ref)
    {
        ManagerRef = ref;
        Listener = new NorthListener();
        setBackground(new Color(28, 87, 119));

        BtnStart = new JButton("Start");
        BtnPause = new JButton("Pause");
        BtnCheat = new JButton("Cheat");
        BtnStart.setFocusable(false);
        BtnPause.setFocusable(false);
        BtnCheat.setFocusable(false);
        BtnStart.addActionListener(Listener);
        BtnPause.addActionListener(Listener);
        BtnCheat.addActionListener(Listener);
        BtnStart.setBackground(new Color(188, 78, 76));
        BtnPause.setBackground(new Color(188, 78, 76));
        BtnCheat.setBackground(new Color(188, 78, 76));
        CheckButtonProperties();

        add(BtnStart);
        add(BtnPause);
        add(BtnCheat);

    }
    public void CheckButtonProperties()
    {
        if (ManagerRef.getAlive()) {
            BtnStart.setEnabled(false);
            BtnPause.setEnabled(true);
        }
        else {
            BtnStart.setEnabled(false);
            BtnPause.setEnabled(false);
        }
    }
    public class NorthListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Start"))
            {
                    ManagerRef.setAlive(true);
                    ManagerRef.RunGame();
                    BtnPause.setEnabled(true);
                    BtnStart.setEnabled(false);

            }
            if(e.getActionCommand().equals("Pause"))
            {
                ManagerRef.setAlive(false);
                ManagerRef.RunGame();
                BtnStart.setEnabled(true);
                BtnPause.setEnabled(false);
            }
            if(e.getActionCommand().equals("Cheat"))
            {
                if(ManagerRef.getCheat())
                {
                    ManagerRef.setCheat(false);
                }
                else
                    ManagerRef.setCheat(true);
            }
        }
    }

}
