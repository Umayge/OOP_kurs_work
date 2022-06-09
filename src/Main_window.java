import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import org.w3c.dom.Document;
import java.awt.GridBagLayout;


public class Main_window {
    public static void main(String[] args) {
        ImageIcon img = new ImageIcon("../ico/gbdd.png");
        JFrame main_frame = new JFrame();
        JPanel button_panel = new JPanel();
        JPanel text_panel = new JPanel();
        JButton work = new JButton("Начать смену");;
        JLabel main_text_label = new JLabel("<html><div style='text-align: center;'>" + " ПК для сотрудников ГАИ <br> <br><br>Начать смену?" + "</div></html>");
        main_frame.setSize(427, 427);
        main_frame.setTitle("База ГИБДД");
        main_frame.setIconImage(img.getImage()); //Ставим иконку окна
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setLocationRelativeTo(null);
        main_frame.setLayout(new BorderLayout());
        button_panel.setLayout(new GridBagLayout());
        text_panel.setLayout(new GridBagLayout());

        main_frame.add(button_panel, BorderLayout.SOUTH);
        main_frame.add(text_panel, BorderLayout.CENTER);


        button_panel.add(work);

        text_panel.add(main_text_label);

        work.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin_window.main(args);
                main_frame.setVisible(false);
            }
        });;


        main_frame.setVisible(true);
    }
}