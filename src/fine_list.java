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

public class fine_list {

    public static void parse_xml_film(Table_model model, int row_count) { //метод, чтобы доставать фильмы из xml файла
        int not_node_el = 0;
        for(int i = 0; i < row_count; i++) {
            model.get_row_sel_del(0);
        }
        File file = new File("fileData.xml");//берем файл
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document doc = null;
        try {
            doc = dbf.newDocumentBuilder().parse(file); //Парсим файл
        } catch(Exception e) {
            return;
        }
        NodeList film =  doc.getElementsByTagName("film"); //Берем все теги film из xml
        for(int i = 0; i < film.getLength(); i++) {
            String [] str = new String[6];
            NodeList film_child = film.item(i).getChildNodes();
            if(film_child.getLength() >= 6) {
                for(int j = 0; j < film_child.getLength(); j++){
                    if(film_child.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        str[j - not_node_el] = film_child.item(j).getTextContent(); //Загоняем инфу в массив
                    }
                    else not_node_el += 1;
                }
                model.addDate(str);//добавляем строку в таблицу с фильмами
                not_node_el = 0;
            }
        }
    }

    public static void save_to_xml(Table_model model, int row_count, JTable info_table) throws TransformerFactoryConfigurationError, TransformerException { //метод, чтобы сохранять в xml файл

        File file = new File("fileData.xml");//берем файл
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document doc = null;
        try {
            doc = dbf.newDocumentBuilder().parse(file); //Парсим файл
        } catch(Exception e) {
            return;
        }
        NodeList film =  doc.getElementsByTagName("film"); //Берем все теги film из xml
        NodeList root =  doc.getElementsByTagName("root");

        for(int i = 0; i < film.getLength(); i++) film.item(i).setTextContent("");

        for(int i = 0; i < row_count; i++) {
            Element film_new = doc.createElement("film");
            root.item(0).appendChild(film_new);
            Element film_name = doc.createElement("film_name");
            Element film_place = doc.createElement("film_place");
            Element film_time = doc.createElement("film_time");
            Element film_prise = doc.createElement("film_prise");
            Element film_genre = doc.createElement("film_genre");
            Element film_duration = doc.createElement("film_duration");

            film_name.setTextContent((String) info_table.getValueAt(i, 0));
            film_place.setTextContent((String) info_table.getValueAt(i, 1));
            film_time.setTextContent((String) info_table.getValueAt(i, 2));
            film_prise.setTextContent((String) info_table.getValueAt(i, 3));
            film_genre.setTextContent((String) info_table.getValueAt(i, 4));
            film_duration.setTextContent((String) info_table.getValueAt(i, 5));

            film.item(i).appendChild(film_name);
            film.item(i).appendChild(film_place);
            film.item(i).appendChild(film_time);
            film.item(i).appendChild(film_prise);
            film.item(i).appendChild(film_genre);
            film.item(i).appendChild(film_duration);
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Source source = new DOMSource(doc);
        Result result = new StreamResult(file);
        transformer.transform (source, result); // Преобразовать XML ==> Источник в результат
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
            bfComic = BaseFont.createFont("/Windows/Fonts/Arial.ttf" ,BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        Font font1 = new Font(bfComic, 9);
        t.addCell(new PdfPCell(new Phrase("Название", font1)));
        t.addCell(new PdfPCell(new Phrase("Место проката", font1)));
        t.addCell(new PdfPCell(new Phrase("Время", font1)));
        t.addCell(new PdfPCell(new Phrase("Цена", font1)));
        t.addCell(new PdfPCell(new Phrase("Жанр", font1)));
        t.addCell(new PdfPCell(new Phrase("Длительность", font1)));
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

        //html
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("one.html"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.println("<TABLE BORDER><TR><TH>Название фильма<TH>Место проката<TH>Время<TH>Цена<TH>Жанр<TH>Длительность<TH></TR>");
        for(int i = 0; i < model.getRowCount(); i++) {
            pw.println("<TR><TD>" + (String) model.getValueAt(i,0) + "<TD>" + (String) model.getValueAt(i,1) + "<TD>" + (String) model.getValueAt(i,2) + "<TD>" + (String) model.getValueAt(i,3)+ "<TD>" + (String) model.getValueAt(i,4)+ "<TD>" + (String) model.getValueAt(i,5));
        }
        pw.close();
    }


    public static void main(String[] args) {
        /**
         * @param args
         * @return
         * @throws TransformerException
         */
        ImageIcon img = new ImageIcon("img/Dark_min.jpg"); //Трехлистник из тьмы))

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
        JButton back = new JButton("Назад");
        JButton On_main = new JButton("На главную");
        JButton filter = new JButton("Сброс поиска");
        JButton search = new JButton("Поиск");
        JButton go_to_cinema_list = new JButton("Посмотреть список кинотеатров");
        JButton go_to_genre_list = new JButton("Перейти в место проката выбранного фильма");

        //Кнопки верхний ряд
        JButton save = new JButton("Сохранить");
        JButton change = new JButton("Изменить информацию");
        JButton make_new = new JButton("Добавить новую строку");
        JButton del = new JButton("Удалить выбранную строку");
        JButton save_pdf = new JButton("Сохранить отчет в pdf");

        //Кнопка для панели добавления нового фильма
        JButton new_film = new JButton("Внести новые данные");

        //Кнопка для панели поиска
        JButton button_for_search_wind = new JButton("Искать");


        //Текстовые поля(Для добавления нового фильма)
        JTextField name_film = new JTextField();
        JTextField place = new JTextField();
        JTextField time = new JTextField();
        JTextField price = new JTextField();
        JTextField duration = new JTextField();

        //Текстовые поля(Для поиска)
        JTextField search_text_field = new JTextField();


        //Выпадащий список(Для добавления нового фильма)
        String [] str_list = new String[7];
        str_list[0] = "Фэнтези";
        str_list[1] = "Фантастика";
        str_list[2] = "Детектив";
        str_list[3] = "Триллер";
        str_list[4] = "Драма";
        str_list[5] = "Боевик";
        str_list[6] = "Мультфильм";
        JComboBox<Object> list = new JComboBox<Object>(str_list); //Зачем <Object> не знаю, eclipce попросил докинуть, чтобы не подчеркивалось х)

        //Выпадащий список(Для поиска)
        String [] str_search = new String[6];
        str_search[0] = "Название фильма";
        str_search[1] = "Место проката";
        str_search[2] = "Время";
        str_search[3] = "Цена";
        str_search[4] = "Жанр";
        str_search[5] = "Длительность";
        JComboBox<Object> list_search = new JComboBox<Object>(str_search); //Зачем <Object> не знаю, eclipce попросил докинуть, чтобы не подчеркивалось х)


        //Подписи к текстовым полям(Для добавления нового фильма)
        JLabel name_film_label = new JLabel("Введите название фильма:");
        JLabel place_label = new JLabel("Введите место проката фильма:");
        JLabel time_label = new JLabel("Введите время начала фильма:");
        JLabel price_label = new JLabel("Введите цену фильма:");
        JLabel genre_label = new JLabel("Введите жанр фильма:");
        JLabel duration_label = new JLabel("Введите продолжительность фильма:");

        //Подписи к текстовым полям(Для поиска)
        JLabel search_label = new JLabel("Выберете параметр для поиска:");


        //Таблица
        Table_model model = new Table_model();
        model.columnCount = 7;
        JTable info_table = new JTable(model);//Создаем таблицу
        JScrollPane scroll = new JScrollPane(info_table);//Задаем прокрутку нашей таблице
        scroll.setPreferredSize(new Dimension(950, 550)); //Задаем размеры окна

        parse_xml_film(model, info_table.getRowCount()); //Заносим инфу в таблицу

        //Параметры основного окна
        main_frame.setSize(1030, 700); //Размеры окна
        main_frame.setIconImage(img.getImage()); //Ставим иконку окна
        main_frame.setTitle("Список фильмов"); //Заголово окна
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
        search_wind.setIconImage(img.getImage()); //Ставим иконку окна
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
        table_new_el_panel.setLayout(new GridLayout(14, 1));//Способ размещения внутри панели
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

        table_new_el_panel.add(name_film_label);
        table_new_el_panel.add(name_film);
        table_new_el_panel.add(place_label);
        table_new_el_panel.add(place);
        table_new_el_panel.add(time_label);
        table_new_el_panel.add(time);
        table_new_el_panel.add(price_label);
        table_new_el_panel.add(price);
        table_new_el_panel.add(genre_label);
        table_new_el_panel.add(list);
        table_new_el_panel.add(duration_label);
        table_new_el_panel.add(duration);
        table_new_el_panel.add(Box.createHorizontalStrut(10));
        table_new_el_panel.add(new_film);

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
                make_new_table_row.setTitle("Добавление нового кинотеатра");
                name_film.setText("");
                place.setText("");
                time.setText("");
                price.setText("");
                list.setSelectedItem("Фэнтези");
                duration.setText("");
                make_new_table_row.setVisible(true);
            }
        });;

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admin_window.main(args);
                main_frame.setVisible(false);
            }
        });;

        On_main.addActionListener(new ActionListener() {
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

        new_film.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                make_new_table_row.setVisible(false);
                if(flag[0] != "1") {
                    String [] new_str = new String[6];
                    new_str[0] = name_film.getText();
                    new_str[1] = place.getText();
                    new_str[2] = time.getText();
                    new_str[3] = price.getText();
                    new_str[4] = (String) list.getSelectedItem();
                    new_str[5] = duration.getText();;
                    //System.out.println(list.getSelectedItem());
                    model.addDate(new_str);
                    table_panel.add(scroll);
                }
                else if(flag[0] == "2") {
                    parse_xml_film(model, info_table.getRowCount());
                    flag[0] = "-1";
                }
                else {
                    make_new_table_row.setTitle("Добавление нового фильма");
                    String [] new_str = new String[6];
                    new_str[0] = name_film.getText();
                    new_str[1] = place.getText();
                    new_str[2] = time.getText();
                    new_str[3] = price.getText();
                    new_str[4] = (String) list.getSelectedItem();
                    new_str[5] = duration.getText();;
                    //System.out.println(list.getSelectedItem());
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
                        name_film.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 0));
                        place.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 1));
                        time.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 2));
                        price.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 3));
                        list.setSelectedItem((String) info_table.getValueAt(info_table.getSelectedRow(), 4));
                        duration.setText((String) info_table.getValueAt(info_table.getSelectedRow(), 5));
                    }
                    else {
                        flag[0] = "-1";
                        parse_xml_film(model, info_table.getRowCount());
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
                parse_xml_film(model, info_table.getRowCount());
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

        go_to_genre_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(info_table.getSelectedRow() != -1) {
                    auto_window.name_cinema = (info_table.getValueAt(info_table.getSelectedRow(), 1).toString());
                    auto_window.main(args);
                    main_frame.setVisible(false);
                }
            }
        });;

        go_to_cinema_list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auto_list.main(args);
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