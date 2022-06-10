import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;

public class admin_window {
    public static void main(String[] args) {
        ImageIcon img = new ImageIcon("../ico/gbdd.png");
        JFrame main_frame = new JFrame();
        JPanel button_panel = new JPanel();
        JPanel text_panel = new JPanel();
        JButton auto_list = new JButton("База атомобилей");
        JButton violation_list = new JButton("Список нарушений");
        JButton on_main = new JButton("Завершить смену");

        JLabel main_text_label = new JLabel("<html><div style='text-align: center;'><br>Помните!<br>Не стоит злоупотреблять служебным положением!<br><br>Используй информацию </html>");
        main_text_label.setSize(400,300);
        text_panel.setSize(400,300);
        main_frame.setSize(455, 427);
        main_frame.setTitle("База ГИБДД");
        main_frame.setIconImage(img.getImage()); //Ставим иконку окна
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setLocationRelativeTo(null);
        main_frame.setLayout(new BorderLayout());
        button_panel.setLayout(new GridBagLayout());
        text_panel.setLayout(new GridBagLayout());

        main_frame.add(button_panel, BorderLayout.SOUTH);
        main_frame.add(text_panel, BorderLayout.CENTER);

        button_panel.add(on_main);
        button_panel.add(auto_list);
        button_panel.add(violation_list);

        text_panel.add(main_text_label);



        auto_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auto_list.main(args);
                main_frame.setVisible(false);
            }
        });;

        violation_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fine_list.main(args);
                main_frame.setVisible(false);
            }
        });;

        on_main.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main_window.main(args);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                main_frame.setVisible(false);
            }
        });;


        main_frame.setVisible(true);
    }
}