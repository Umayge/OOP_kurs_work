import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
import java.util.Date;
public class auto_window {


    public static void make_table(Table_model model, int row_count,Root root,String name_man)
    {

        int not_node_el = 0;
        for(int i = 0; i < row_count; i++) {
            model.get_row_sel_del(0);
        }
        for(int j =0; j<root.getPeople().size();j++)
        {
            if(name_man.equals(String.valueOf(root.getPeople().get(j).getKey())))
            {
                for (int i=0;i<root.getPeople().get(j).getFines().size();i++)
                {
                    String [] str = new String[5];
                    str[0] = root.getPeople().get(j).getFines().get(i).getName();
                    str[1] = root.getPeople().get(j).getFines().get(i).getGoss();
                    str[2] = root.getPeople().get(j).getFines().get(i).getType();
                    str[3] = root.getPeople().get(j).getFines().get(i).getDate();
                    str[4] = String.valueOf(root.getPeople().get(j).getFines().get(i).getKey()) ;
                    model.addDate(str);
                }
                break;
            }
        }

    }

    public static void make_report(Table_model model,String date1, String date2){

        LocalDate ldate1 = LocalDate.parse(date1);
        LocalDate ldate2 = LocalDate.parse(date2);
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        PdfPTable t = new PdfPTable(4);

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

        Font font1 = new Font(bfComic, 4);
        t.addCell(new PdfPCell(new Phrase("ФИО", font1)));
        t.addCell(new PdfPCell(new Phrase("Гос номер", font1)));
        t.addCell(new PdfPCell(new Phrase("Тип нарушения", font1)));
        t.addCell(new PdfPCell(new Phrase("Дата", font1)));
        for(int i = 0; i < model.getRowCount(); i++){

            if(ldate1.isBefore(LocalDate.parse((String) model.getValueAt(i,3))) && ldate2.isAfter(LocalDate.parse((String) model.getValueAt(i,3)) ))
            {
                t.addCell(new Phrase((String) model.getValueAt(i,0),font1));
                t.addCell(new Phrase((String) model.getValueAt(i,1),font1));
                t.addCell(new Phrase((String) model.getValueAt(i,2),font1));
                t.addCell(new Phrase((String) model.getValueAt(i,3),font1));
            }

        }
        document.open();
        try {
            document.add(t);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }


    public static String name_man;

    public static void main(Root root, int key) {

        String save_name = null;

        ImageIcon img = new ImageIcon("img/Dark_min.jpg"); //Трехлистник из тьмы))

        //Окна
        JFrame main_frame = new JFrame(); //Основное окно, в котором находится все остальное
        JFrame make_new_table_row = new JFrame(); //Окно добавления нового фильма в таблицу
        JFrame search_wind = new JFrame(); //Окно поиска
        JFrame report_window = new JFrame();//окно для отчетов


        //Панели
        JPanel button_panel = new JPanel(); //Панель с нижними кнопками
        JPanel button_panel_top = new JPanel(); //Панель с верхними кнопками
        JPanel table_panel = new JPanel(); //Панель с таблицей
        JPanel table_new_el_panel = new JPanel(); //Панель для добавления нового элемента
        JPanel search_panel = new JPanel(); //Панель для окна поиска
        JPanel report_panel =new JPanel();
        //Кнопки
        //Кнопки нижний ряд
        JButton back = new JButton("Назад");
        JButton On_main = new JButton("На главную");
        JButton filter = new JButton("Сброс поиска");
        JButton search = new JButton("Поиск");
        JButton go_to_cinema_list = new JButton("Посмотреть список штрафов");
        JButton go_to_genre_list = new JButton("Посмотреть базу автомобилистов");

        //Кнопки верхний ряд
        JButton save = new JButton("Сохранить");
        JButton change = new JButton("Изменить информацию");
        JButton make_new = new JButton("Добавить новую строку");
        JButton del = new JButton("Удалить выбранную строку");
        JButton save_pdf = new JButton("Сохранить отчет в pdf");
        JButton save_pdf_form =new JButton("Сохранить");

        //Кнопка для панели добавления нового фильма
        JButton new_fine = new JButton("Внести новые данные");

        //Кнопка для панели поиска
        JButton button_for_search_wind = new JButton("Искать");


        //Текстовые поля(Для добавления нового фильма)
        JTextField type_violation = new JTextField();
        JTextField date = new JTextField();
        JTextField characteristic = new JTextField();
        JTextField price = new JTextField();
        JTextField duration = new JTextField();
        JTextField age = new JTextField();

        //Текстовые поля(Для поиска)
        JTextField search_text_field = new JTextField();
        JTextField date1 =new JTextField();
        JTextField date2 = new JTextField();
        
        //Выпадащий список(Для поиска)
        String [] str_search = new String[4];
        str_search[0] = "Тип нарушения";
        str_search[1] = "характеристика";
        str_search[2] = "Цена";
        str_search[3] = "статус";
        JComboBox<Object> list_search = new JComboBox<Object>(str_search); //Зачем <Object> не знаю, eclipce попросил докинуть, чтобы не подчеркивалось х)


        //Подписи к текстовым полям(Для добавления нового штрафа)
        JLabel type_violation_label = new JLabel("Введите тип нарушения:");
        JLabel date_label = new JLabel("Введите дату нарушения:");
        JLabel date1_label =new JLabel("Введите первую дату");
        JLabel date2_label = new JLabel("Введите вторую дату");

        //Подписи к текстовым полям(Для поиска)
        JLabel search_label = new JLabel("Выберете параметр для поиска:");


        //Таблица
        Table_model model = new Table_model();
        model.columnCount = 4;
        JTable info_table = new JTable(model);//Создаем таблицу
        JScrollPane scroll = new JScrollPane(info_table);//Задаем прокрутку нашей таблице
        scroll.setPreferredSize(new Dimension(1050, 550)); //Задаем размеры окна

        make_table(model, info_table.getRowCount(),root,name_man); //Заносим инфу в таблицу

        //Параметры основного окна
        main_frame.setSize(1090, 700); //Размеры окна
        main_frame.setIconImage(img.getImage()); //Ставим иконку окна
        main_frame.setTitle(save_name); //Заголово окна
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Что происходит при нажатии на крестик(В нашем случае закрытие)
        main_frame.setResizable(false);//Запрещаем дополнительно развертывать окно
        main_frame.setLocationRelativeTo(null);//Располагаем окно в центре экрана
        main_frame.setLayout(new BorderLayout());//Способ размещения внутри окна

        //Параметры окна для добавления фильма в таблицу
        make_new_table_row.setSize(350, 400); //Размеры окна
        make_new_table_row.setIconImage(img.getImage()); //Ставим иконку окна
        make_new_table_row.setTitle("Добавление нового фильма"); //Заголово окна
        make_new_table_row.setResizable(false);//Запрещаем дополнительно развертывать окно
        make_new_table_row.setLocationRelativeTo(null);//Располагаем окно в центре экрана
        make_new_table_row.setLayout(new BorderLayout());//Способ размещения внутри окна

        //Параметры окна для поиска
        search_wind.setSize(250, 300); //Размеры окна
        //search_wind.setIconImage(img.getImage()); //Ставим иконку окна
        search_wind.setTitle("Поиск"); //Заголово окна
        search_wind.setResizable(false);//Запрещаем дополнительно развертывать окно
        search_wind.setLocationRelativeTo(null);//Располагаем окно в центре экрана
        search_wind.setLayout(new BorderLayout());//Способ размещения внутри окна

        //параметры окна ртчетов
        report_window.setSize(250,300);
        report_window.setTitle("Отчет нарушений");
        report_window.setResizable(false);
        report_window.setLocationRelativeTo(null);
        report_window.setLayout(new BorderLayout());


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

        report_window.add(report_panel);


        //Параметры панелей
        button_panel.setLayout(new GridBagLayout());//Способ размещения внутри панели
        table_panel.setLayout(new GridBagLayout());//Способ размещения внутри панели
        button_panel_top.setLayout(new GridBagLayout());//Способ размещения внутри панели
        table_new_el_panel.setLayout(new GridLayout(16, 1));//Способ размещения внутри панели
        search_panel.setLayout(new GridLayout(6, 1));//Способ размещения внутри панели
        report_panel.setLayout(new GridLayout(16,1));

        button_panel_top.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 0)); //Делаем отступ сверху для верхней панели
        button_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0)); //Делаем отступ сверху для верхней панели
        table_new_el_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10)); //Делаем отступ сверху для верхней панели
        search_panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10)); //Делаем отступ сверху для верхней панели


        //Добавляем элементы в панели

        button_panel.add(back);
        button_panel.add(On_main);
        button_panel.add(Box.createHorizontalStrut(40));
        button_panel.add(filter);
        button_panel.add(search);
        button_panel.add(Box.createHorizontalStrut(45));
        button_panel.add(Box.createHorizontalStrut(50));
        button_panel.add(go_to_cinema_list);
        button_panel.add(go_to_genre_list);

        button_panel_top.add(save);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(change);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(make_new);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(del);
        button_panel_top.add(Box.createHorizontalStrut(10));
        button_panel_top.add(save_pdf);

        table_new_el_panel.add(type_violation_label);
        table_new_el_panel.add(type_violation);
        table_new_el_panel.add(date_label);
        table_new_el_panel.add(date);


        table_new_el_panel.add(Box.createHorizontalStrut(10));
        table_new_el_panel.add(new_fine);

        search_panel.add(search_label);
        search_panel.add(list_search);
        search_panel.add(Box.createHorizontalStrut(10));
        search_panel.add(search_text_field);
        search_panel.add(Box.createHorizontalStrut(10));
        search_panel.add(button_for_search_wind);


        report_panel.add(date1_label);
        report_panel.add(date1);
        report_panel.add(Box.createHorizontalStrut(10));
        report_panel.add(date2_label);
        report_panel.add(date2);
        report_panel.add(Box.createHorizontalStrut(10));
        report_panel.add(save_pdf_form);
        //Добавляем действия на кнопки

        String []  flag = new String[1];


        del.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(info_table.getSelectedRow() != -1) {
                    for(int i=0;i<root.getFine().size();i++)
                    {
                        if( model.getValueAt(info_table.getSelectedRow(),4).equals(String.valueOf(root.getFine().get(i).getKey()))  && root.getFine().get(i).getType().equals(model.getValueAt(info_table.getSelectedRow(),2)) && root.getFine().get(i).getDate().equals(model.getValueAt(info_table.getSelectedRow(),3)) )
                        {
                            root.getFine().remove(i);
                            break;
                        }
                    }
                    for(int i=0;i<root.getPeople().size();i++)
                    {
                        if( model.getValueAt(info_table.getSelectedRow(),4).equals(String.valueOf(root.getPeople().get(i).getKey()) ))
                            for(int j=0;j<root.getPeople().get(i).getFines().size();j++)
                                if(root.getPeople().get(i).getFines().get(j).getType().equals(model.getValueAt(info_table.getSelectedRow(),2)) && root.getPeople().get(i).getFines().get(j).getDate().equals(model.getValueAt(info_table.getSelectedRow(),3)) )
                                {
                                    root.getPeople().get(i).getFines().remove(j);
                                    break;
                                }
                    }
                    model.get_row_sel_del(info_table.getSelectedRow());
                }
                table_panel.add(scroll);
            }
        });;

        make_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type_violation.setText("");
                date.setText("");
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

        new_fine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int k=0;
                for(k=0;k<root.getPeople().size();k++)
                {
                    if(root.getPeople().get(k).getKey()==key)
                        break;
                }
                make_new_table_row.setVisible(false);
                    String [] new_str = new String[4];
                    new_str[0] = root.getPeople().get(k).getName();
                    new_str[1] = root.getPeople().get(k).getGoss_number();
                    new_str[2] = type_violation.getText();
                    new_str[3] = date.getText();

                    Fine fine = new Fine(root.getPeople().get(k).getName(),root.getPeople().get(k).getGoss_number(),type_violation.getText(),date.getText(),k);
                    root.getFine().add(fine);
                    root.getPeople().get(k).getFines().add(fine);
                    model.addDate(new_str);
                    table_panel.add(scroll);
                if(info_table.getSelectedRow() != -1) {
                    for(int i=0;i<root.getFine().size();i++)
                    {
                        if( model.getValueAt(info_table.getSelectedRow(),4).equals(String.valueOf(root.getFine().get(i).getKey()))  && root.getFine().get(i).getType().equals(model.getValueAt(info_table.getSelectedRow(),2)) && root.getFine().get(i).getDate().equals(model.getValueAt(info_table.getSelectedRow(),3)) )
                        {
                            root.getFine().remove(i);
                            break;
                        }
                    }
                    for(int i=0;i<root.getPeople().size();i++)
                    {
                        if( model.getValueAt(info_table.getSelectedRow(),4).equals(String.valueOf(root.getPeople().get(i).getKey()) ))
                            for(int j=0;j<root.getPeople().get(i).getFines().size();j++)
                                if(root.getPeople().get(i).getFines().get(j).getType().equals(model.getValueAt(info_table.getSelectedRow(),2)) && root.getPeople().get(i).getFines().get(j).getDate().equals(model.getValueAt(info_table.getSelectedRow(),3)) )
                                {
                                    root.getPeople().get(i).getFines().remove(j);
                                    break;
                                }
                    }
                    model.get_row_sel_del(info_table.getSelectedRow());
                }
                table_panel.add(scroll);
            }
        });;

        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(info_table.getSelectedRow() != -1) {
                    if(flag[0] != "2") {
                        make_new_table_row.setTitle("Изменение данных");
                        make_new_table_row.setVisible(true);
                        flag[0] = "1";
                        type_violation.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 2));
                        date.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 3));
                    }
                    else {
                        flag[0] = "-1";

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
                //parse_xml_film(model, info_table.getRowCount(), list_day.getSelectedIndex(), name_cinema);
                table_panel.add(scroll);
            }
        });;

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //save_to_xml(model, info_table.getRowCount(), info_table, list_day.getSelectedIndex(), name_cinema);
                } catch (TransformerFactoryConfigurationError e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } //catch (TransformerException e1) {
                    // TODO Auto-generated catch block
                    //e1.printStackTrace();
               // }
            }
        });;

        save_pdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                date1.setText("");
                date2.setText("");
                report_window.setVisible(true);
            }
        });;
        save_pdf_form.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                make_report(model,date1.getText(),date2.getText());
                report_window.setVisible(false);
            }
                      });;

        go_to_cinema_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fine_list.main(root);
                main_frame.setVisible(false);
            }
        });;

        go_to_genre_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auto_list.main(root);
                main_frame.setVisible(false);
            }
        });;


        table_panel.add(scroll);
        main_frame.setVisible(true); //Делаем осн окно видимым
    }

}