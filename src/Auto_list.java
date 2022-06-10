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



    public static void save_to_xml(Table_model model, int row_count, JTable info_table) throws TransformerFactoryConfigurationError, TransformerException { //метод, чтобы сохранять в xml файл

        File file = new File("fileData.xml");//берем файл
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document doc = null;
        Node new_el = null;
        try {
            doc = dbf.newDocumentBuilder().parse(file); //Парсим файл
        } catch(Exception e) {
            return;
        }
        NodeList root =  doc.getElementsByTagName("cinema_info");
        NodeList film =  root.item(0).getChildNodes(); //Берем все теги film из xml

        for(int i = 0; i < film.getLength(); i++) film.item(i).setTextContent("");

        for(int i = 0; i < row_count; i++) {
            Element film_new = doc.createElement("cinema_i");
            new_el = root.item(0).appendChild(film_new);
            Element film_name = doc.createElement("name_cinema");
            Element film_place = doc.createElement("rate");
            Element film_time = doc.createElement("capacity");
            Element film_prise = doc.createElement("time");
            Element film_genre = doc.createElement("hall");

            film_name.setTextContent((String) info_table.getValueAt(i, 0));
            film_place.setTextContent((String) info_table.getValueAt(i, 1));
            film_time.setTextContent((String) info_table.getValueAt(i, 2));
            film_prise.setTextContent((String) info_table.getValueAt(i, 3));
            film_genre.setTextContent((String) info_table.getValueAt(i, 4));

            new_el.appendChild(film_name);
            new_el.appendChild(film_place);
            new_el.appendChild(film_time);
            new_el.appendChild(film_prise);
            new_el.appendChild(film_genre);
        }

        film =  root.item(0).getChildNodes();
        for(int i = 0; i <film.getLength(); i++) {
            for(int j = 0; j < film.item(i).getChildNodes().getLength(); j++) {
                //System.out.println(film.item(i).getChildNodes().item(j).getTextContent());
                for(int z = 0; z < row_count; z++) {
                    if(Objects.equals(film.item(i).getChildNodes().item(j).getTextContent(), (String) info_table.getValueAt(z, 0))) {
                        NodeList rooter = doc.getElementsByTagName("root").item(0).getChildNodes();
                        int check = 0;
                        for(int w = 0; w < rooter.getLength(); w++) {
                            System.out.println((film.item(i).getChildNodes().item(j).getTextContent()).replaceAll(" ", ""));
                            System.out.println(rooter.item(w).getNodeName());
                            System.out.println("---");
                            if(Objects.equals((film.item(i).getChildNodes().item(j).getTextContent()).replaceAll(" ", ""), rooter.item(w).getNodeName())) check = 1;
                        }
                        if(check == 0) {
                            rooter = doc.getElementsByTagName("root");
                            Node test_2 = null;
                            Node test_3 = null;
                            Element test = doc.createElement((film.item(i).getChildNodes().item(j).getTextContent()).replaceAll(" ", ""));
                            test_2 = rooter.item(0).appendChild(test);
                            test = doc.createElement("monday");
                            test_3 = test_2.appendChild(test);
                            test = doc.createElement("cinema");
                            test.setTextContent("f");
                            test_3.appendChild(test);

                            test = doc.createElement("tuesday");
                            test_3 = test_2.appendChild(test);
                            test = doc.createElement("cinema");
                            test.setTextContent("f");
                            test_3.appendChild(test);

                            test = doc.createElement("wednesday");
                            test_3 = test_2.appendChild(test);
                            test = doc.createElement("cinema");
                            test.setTextContent("f");
                            test_3.appendChild(test);

                            test = doc.createElement("thursday");
                            test_3 = test_2.appendChild(test);
                            test = doc.createElement("cinema");
                            test.setTextContent("f");
                            test_3.appendChild(test);

                            test = doc.createElement("friday");
                            test_3 = test_2.appendChild(test);
                            test = doc.createElement("cinema");
                            test.setTextContent("f");
                            test_3.appendChild(test);

                            test = doc.createElement("saturday");
                            test_3 = test_2.appendChild(test);
                            test = doc.createElement("cinema");
                            test.setTextContent("f");
                            test_3.appendChild(test);

                            test = doc.createElement("sunday");
                            test_3 = test_2.appendChild(test);
                            test = doc.createElement("cinema");
                            test.setTextContent("f");
                            test_3.appendChild(test);
                        }
                        check = 0;
                    }
                }
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Source source = new DOMSource(doc);
        Result result = new StreamResult(file);
        transformer.transform (source, result); // Преобразовать XML ==> Источник в результат
    }

    public static void make_report(Table_model model){

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
        JButton go_to_cinema_list = new JButton("Посмотреть список штрафов");
        JButton go_to_cinema_window = new JButton("профиль выбранного автомобилиста");

        //Кнопки верхний ряд
        JButton save = new JButton("Сохранить");
        JButton change = new JButton("Изменить информацию");
        JButton make_new = new JButton("Добавить новую строку");
        JButton del = new JButton("Удалить выбранную строку");
        JButton save_pdf = new JButton("Сохранить отчет в pdf");

        //Кнопка для панели добавления нового фильма
        JButton new_cinema = new JButton("Внести новые данные");

        //Кнопка для панели поиска
        JButton button_for_search_wind = new JButton("Искать");


        //Текстовые поля(Для добавления нового фильма)
        JTextField name_cinema = new JTextField();
        JTextField rate = new JTextField();
        JTextField capacity = new JTextField();
        JTextField time = new JTextField();
        JTextField Hall = new JTextField();

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

        //parse_xml_film(model, info_table.getRowCount()); //Заносим инфу в таблицу

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
        button_panel.add(go_to_cinema_list);
        button_panel.add(go_to_cinema_window);

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
        table_new_el_panel.add(name_cinema);
        table_new_el_panel.add(pass_label);
        table_new_el_panel.add(rate);
        table_new_el_panel.add(number_label);
        table_new_el_panel.add(capacity);
        table_new_el_panel.add(mark_label);
        table_new_el_panel.add(time);
        table_new_el_panel.add(technical_inspection_label);
        table_new_el_panel.add(Hall);
        table_new_el_panel.add(Box.createHorizontalStrut(10));
        table_new_el_panel.add(new_cinema);

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
                    model.get_row_sel_del(info_table.getSelectedRow());
                }
                table_panel.add(scroll);
            }
        });;

        make_new.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                make_new_table_row.setTitle("Добавление нового водителя");
                name_cinema.setText("");
                rate.setText("");
                capacity.setText("");
                time.setText("");
                Hall.setText("");
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

        new_cinema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                make_new_table_row.setVisible(false);
                if(flag[0] != "1") {
                    String [] new_str = new String[6];
                    new_str[0] = name_cinema.getText();
                    new_str[1] = rate.getText();
                    new_str[2] = capacity.getText();
                    new_str[3] = time.getText();
                    new_str[4] = Hall.getText();;
                    model.addDate(new_str);
                    table_panel.add(scroll);
                }
                else if(flag[0] == "2") {
                    //parse_xml_film(model, info_table.getRowCount());
                    flag[0] = "-1";
                }
                else {
                    make_new_table_row.setTitle("Добавление нового водителя");
                    String [] new_str = new String[6];
                    new_str[0] = name_cinema.getText();
                    new_str[1] = rate.getText();
                    new_str[2] = capacity.getText();
                    new_str[3] = time.getText();
                    new_str[4] = Hall.getText();
                    model.get_row_sel_change(info_table.getSelectedRow(), new_str);
                    table_panel.add(scroll);
                    flag[0] = "-1";
                }
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
                        name_cinema.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 0));
                        rate.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 1));
                        capacity.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 2));
                        time.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 3));
                        Hall.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 4));
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

        save.addActionListener(new ActionListener() {
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
        });;

        go_to_cinema_window.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(info_table.getSelectedRow() != -1) {
                    auto_window.name_man = (info_table.getValueAt(info_table.getSelectedRow(), 0).toString());
                    auto_window.main(root);
                    main_frame.setVisible(false);
                }
            }
        });;

        go_to_cinema_list.addActionListener(new ActionListener() {
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