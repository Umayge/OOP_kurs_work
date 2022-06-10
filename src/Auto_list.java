import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//import com.itextpdf.text.Document;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

//import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class Auto_list {
    public static void make_table(Table_model model, int row_count,Root root)
    {
        int not_node_el = 0;
        for(int i = 0; i < row_count; i++) {
            model.get_row_sel_del(0);
        }
        for (int i=0;i<root.getPeople().size();i++)
        {
            String [] str = new String[7];
            str[0] = root.getPeople().get(i).getName();
            str[1] = root.getPeople().get(i).getPass();
            str[2] = root.getPeople().get(i).getGoss_number();
            str[3] = root.getPeople().get(i).getMark();
            str[4] = root.getPeople().get(i).getDate_techn_inspect();
            str[5] = String.valueOf(root.getPeople().get(i).getFines().size());
            str[6] = String.valueOf(root.getPeople().get(i).getKey()) ;
            model.addDate(str);
        }
    }
    public static void make_report(Table_model model){

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        PdfPTable t = new PdfPTable(6);

        try {

            PdfWriter.getInstance(document, new FileOutputStream("one.pdf"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        BaseFont bfComic = null;

        try {
            bfComic = BaseFont.createFont("PT-Astra-Serif_Regular.ttf" ,BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Font font1 = new Font(bfComic, 6);
        t.addCell(new PdfPCell(new Phrase("ФИО", font1)));
        t.addCell(new PdfPCell(new Phrase("Паспорт", font1)));
        t.addCell(new PdfPCell(new Phrase("Гос номер", font1)));
        t.addCell(new PdfPCell(new Phrase("Марка машины", font1)));
        t.addCell(new PdfPCell(new Phrase("Дата последнего тех. осмотра", font1)));
        t.addCell(new PdfPCell(new Phrase("Количество штрафов", font1)));
        for(int i = 0; i < model.getRowCount(); i++){

            t.addCell(new Phrase((String) model.getValueAt(i,0),font1));
            t.addCell(new Phrase((String) model.getValueAt(i,1),font1));
            t.addCell(new Phrase((String) model.getValueAt(i,2),font1));
            t.addCell(new Phrase((String) model.getValueAt(i,3),font1));
            t.addCell(new Phrase((String) model.getValueAt(i,4),font1));
            t.addCell(new Phrase((String) model.getValueAt(i,5),font1));
        }
        document.open();
        try {
            document.add(t);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    public static void main(Root root) {



        //Окна
        JFrame main_frame = new JFrame(); //Основное окно, в котором находится все остальное
        JFrame make_new_table_row = new JFrame(); //Окно добавления нового фильма в таблицу
        JFrame search_wind = new JFrame(); //Окно поиска


        //Панели
        JPanel button_panel = new JPanel(); //Панель с нижними кнопками
        JPanel button_panel_top = new JPanel(); //Панель с верхними кнопками
        JPanel table_panel = new JPanel(); //Панель с таблицей
        JPanel table_new_el_panel = new JPanel(); //Панель для добавления нового элемента
        JPanel search_panel = new JPanel(); //Панель для окна поиска

        //Кнопки
        //Кнопки нижний ряд
        JButton On_main = new JButton("На главную");
        JButton back = new JButton("Назад");
        JButton filter = new JButton("Сброс поиска");
        JButton search = new JButton("Поиск");
        JButton go_to_fine_list = new JButton("Посмотреть список штрафов");
        JButton go_to_auto_window = new JButton("профиль выбранного автомобилиста");

        //Кнопки верхний ряд
        JButton save = new JButton("Сохранить");
        JButton change = new JButton("Изменить информацию");
        JButton make_new = new JButton("Добавить новую строку");
        JButton del = new JButton("Удалить выбранную строку");
        JButton save_pdf = new JButton("Сохранить отчет в pdf");

        //Кнопка для панели добавления нового фильма
        JButton new_auto = new JButton("Внести новые данные");

        //Кнопка для панели поиска
        JButton button_for_search_wind = new JButton("Искать");


        //Текстовые поля(Для добавления нового фильма)
        JTextField name = new JTextField();
        JTextField pass = new JTextField();
        JTextField goss_number = new JTextField();
        JTextField mark = new JTextField();
        JTextField date_techn_inspect = new JTextField();

        //Текстовые поля(Для поиска)
        JTextField search_text_field = new JTextField();

        //Выпадащий список(Для поиска)
        String [] str_search = new String[5];
        str_search[0] = "ФИО";
        str_search[1] = "Паспорт";
        str_search[2] = "Гос номер";
        str_search[3] = "Марка машины";
        str_search[4] = "Дата последнего тех осмотра";
        JComboBox<Object> list_search = new JComboBox<Object>(str_search); //Зачем <Object> не знаю, eclipce попросил докинуть, чтобы не подчеркивалось х)

        //Подписи к текстовым полям(Для добавления нового фильма)
        JLabel name_auto_label = new JLabel("Введите ФИО:");
        JLabel pass_label = new JLabel("Введите Паспорт:");
        JLabel number_label = new JLabel("Введите Гос номер:");
        JLabel mark_label = new JLabel("Введите Марку машины:");
        JLabel technical_inspection_label = new JLabel("Введите Дату последнего тех осмотра:");

        //Подписи к текстовым полям(Для поиска)
        JLabel search_label = new JLabel("Выберете параметр для поиска:");


        //Таблица
        Table_model model = new Table_model();
        model.columnCount = 6;
        JTable info_table = new JTable(model);//Создаем таблицу
        JScrollPane scroll = new JScrollPane(info_table);//Задаем прокрутку нашей таблице
        scroll.setPreferredSize(new Dimension(950, 550)); //Задаем размеры окна

        make_table(model, info_table.getRowCount(),root); //Заносим инфу в таблицу

        //Параметры основного окна
        main_frame.setSize(1030, 700); //Размеры окна

        main_frame.setTitle("База автомобилистов"); //Заголово окна
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Что происходит при нажатии на крестик(В нашем случае закрытие)
        main_frame.setResizable(false);//Запрещаем дополнительно развертывать окно
        main_frame.setLocationRelativeTo(null);//Располагаем окно в центре экрана
        main_frame.setLayout(new BorderLayout());//Способ размещения внутри окна

        //Параметры окна для добавления фильма в таблицу
        make_new_table_row.setSize(400, 400); //Размеры окна

        make_new_table_row.setTitle("Добавление нового водителя"); //Заголово окна
        make_new_table_row.setResizable(false);//Запрещаем дополнительно развертывать окно
        make_new_table_row.setLocationRelativeTo(null);//Располагаем окно в центре экрана
        make_new_table_row.setLayout(new BorderLayout());//Способ размещения внутри окна

        //Параметры окна для поиска
        search_wind.setSize(250, 300); //Размеры окна

        search_wind.setTitle("Поиск"); //Заголово окна
        search_wind.setResizable(false);//Запрещаем дополнительно развертывать окно
        search_wind.setLocationRelativeTo(null);//Располагаем окно в центре экрана
        search_wind.setLayout(new BorderLayout());//Способ размещения внутри окна


        //Размещение элементов внутри окон
        main_frame.add(button_panel, BorderLayout.SOUTH);
        main_frame.add(table_panel, BorderLayout.CENTER);
        main_frame.add(button_panel_top, BorderLayout.NORTH);

        make_new_table_row.add(table_new_el_panel, BorderLayout.CENTER);

        search_wind.add(search_panel, BorderLayout.CENTER);

        //Размещение элементов внутри окна добавления фильма
        make_new_table_row.add(table_new_el_panel);

        //Размещение элементов внутри окна поиска
        search_wind.add(search_panel);

        //Параметры панелей
        button_panel.setLayout(new GridBagLayout());//Способ размещения внутри панели
        table_panel.setLayout(new GridBagLayout());//Способ размещения внутри панели
        button_panel_top.setLayout(new GridBagLayout());//Способ размещения внутри панели
        table_new_el_panel.setLayout(new GridLayout(12, 1));//Способ размещения внутри панели
        search_panel.setLayout(new GridLayout(6, 1));//Способ размещения внутри панели

        button_panel_top.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0)); //Делаем отступ сверху для верхней панели
        button_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0)); //Делаем отступ сверху для верхней панели
        table_new_el_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10)); //Делаем отступ сверху для верхней панели
        search_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10)); //Делаем отступ сверху для верхней панели


        //Добавляем элементы в панели
        button_panel.add(back);
        button_panel.add(On_main);
        button_panel.add(Box.createHorizontalStrut(30));
        button_panel.add(filter);
        button_panel.add(search);
        button_panel.add(Box.createHorizontalStrut(50));
        button_panel.add(go_to_fine_list);
        button_panel.add(go_to_auto_window);

        button_panel_top.add(save);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(change);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(make_new);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(del);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(save_pdf);

        table_new_el_panel.add(name_auto_label);
        table_new_el_panel.add(name);
        table_new_el_panel.add(pass_label);
        table_new_el_panel.add(pass);
        table_new_el_panel.add(number_label);
        table_new_el_panel.add(goss_number);
        table_new_el_panel.add(mark_label);
        table_new_el_panel.add(mark);
        table_new_el_panel.add(technical_inspection_label);
        table_new_el_panel.add(date_techn_inspect);
        table_new_el_panel.add(Box.createHorizontalStrut(10));
        table_new_el_panel.add(new_auto);

        search_panel.add(search_label);
        search_panel.add(list_search);
        search_panel.add(Box.createHorizontalStrut(10));
        search_panel.add(search_text_field);
        search_panel.add(Box.createHorizontalStrut(10));
        search_panel.add(button_for_search_wind);

        //Добавляем действия на кнопки

        String []  flag = new String[1];

        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(info_table.getSelectedRow() != -1) {
                    int k;
                    int a =info_table.getSelectedRow();
                    String b =model.getValueAt(a,6).toString();
                    for(k=0;k<root.getPeople().size();k++)
                    {

                       if(b.equals(String.valueOf(k)))
                       {
                           root.getPeople().remove(k);
                           for(int i=0;i<root.getFine().size();i++)
                               if(root.getFine().get(i).getKey()==k)
                                   root.getFine().remove(i);
                           break;
                       }

                    }
                    make_table(model,model.columnCount,root);
                }
                table_panel.add(scroll);

            }
        });;

        make_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                make_new_table_row.setTitle("Добавление нового водителя");
                name.setText("");
                pass.setText("");
                goss_number.setText("");
                mark.setText("");
                date_techn_inspect.setText("");
                make_new_table_row.setVisible(true);
            }
        });;

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin_window.main(root);
                main_frame.setVisible(false);
            }
        });;

        On_main.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main_window.main(null);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                main_frame.setVisible(false);
            }
        });;

        new_auto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                make_new_table_row.setVisible(false);
                if(flag[0] != "1") {
                    String [] new_str = new String[6];
                    new_str[0] = name.getText();
                    new_str[1] = pass.getText();
                    new_str[2] = goss_number.getText();
                    new_str[3] = mark.getText();
                    new_str[4] = date_techn_inspect.getText();;
                    model.addDate(new_str);
                    table_panel.add(scroll);
                    int max=0;
                    for(int i=0;i<root.getPeople().size();i++)
                    {
                        if(max<root.getPeople().get(i).getKey())
                            max=root.getPeople().get(i).getKey();
                    }
                    List<Fine> temp = new ArrayList<>();
                    People p = new People(name.getText(),pass.getText(),goss_number.getText(),mark.getText(),date_techn_inspect.getText(),temp,max+1);
                    root.getPeople().add(p);
                }
                else if(flag[0] == "2") {
                    //parse_xml_film(model, info_table.getRowCount());
                    flag[0] = "-1";
                }
                else
                {
                    make_new_table_row.setTitle("Добавление нового водителя");

                    //model.get_row_sel_change(info_table.getSelectedRow(), new_str);
                    table_panel.add(scroll);
                    flag[0] = "-1";

                    int k;
                    int a =info_table.getSelectedRow();
                    String b =model.getValueAt(a,6).toString();
                    for(k=0;k<root.getPeople().size();k++)
                    {
                        if(b.equals(String.valueOf(root.getPeople().get(k).getKey())))
                        {
                            root.getPeople().get(k).setName(name.getText());
                            root.getPeople().get(k).setPass(pass.getText());
                            root.getPeople().get(k).setGoss_number(goss_number.getText());
                            root.getPeople().get(k).setMark(mark.getText());
                            root.getPeople().get(k).setDate_techn_inspect(date_techn_inspect.getText());
                            for(int i=0;i<root.getFine().size();i++)
                            {
                                if(root.getFine().get(i).getKey()==k)
                                {
                                    root.getFine().get(i).setName(name.getText());
                                    root.getFine().get(i).setGoss(goss_number.getText());
                                }
                            }
                            break;
                        }
                    }
                    make_table(model,model.columnCount,root);
                }
            }});;

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(info_table.getSelectedRow() != -1) {
                    if(flag[0] != "2") {
                        make_new_table_row.setTitle("Изменение данных");
                        make_new_table_row.setVisible(true);
                        flag[0] = "1";
                        name.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 0));
                        pass.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 1));
                        goss_number.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 2));
                        mark.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 3));
                        date_techn_inspect.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 4));
                    }
                    else {
                        flag[0] = "-1";
                        //parse_xml_film(model, info_table.getRowCount());
                        table_panel.add(scroll);
                    }
                }
            }
        });;

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_wind.setVisible(true);
            }
        });;

        button_for_search_wind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search_wind.setVisible(false);
                flag[0] = "2";
                for(int i = 0; i < info_table.getRowCount(); i++) {
                    if(!Objects.equals((String) info_table.getValueAt(i, list_search.getSelectedIndex()), search_text_field.getText())) {
                        model.get_row_sel_del(i);
                        i -= 1;
                    }
                }
                table_panel.add(scroll);
            }
        });;

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag[0] = "-1";
                //parse_xml_film(model, info_table.getRowCount());
                table_panel.add(scroll);
            }
        });;

        /*save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    save_to_xml(model, info_table.getRowCount(), info_table);
                } catch (TransformerFactoryConfigurationError e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (TransformerException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });;*/

        go_to_auto_window.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(info_table.getSelectedRow() != -1) {
                    auto_window.name_man = (model.getValueAt(info_table.getSelectedRow(), 6).toString());
                    auto_window.main(root,Integer.parseInt(model.getValueAt(info_table.getSelectedRow(), 6).toString()));
                    main_frame.setVisible(false);
                }
            }
        });;

        go_to_fine_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fine_list.main(root);
                main_frame.setVisible(false);
            }
        });;

        save_pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                make_report(model);
            }
        });;

        table_panel.add(scroll);
        main_frame.setVisible(true); //Делаем осн окно видимым
    }

}