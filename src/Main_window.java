//import com.itextpdf.text.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

public class Main_window {
    public static Root parse_xml() throws Exception { //метод, чтобы парсить xml файл
        Node peoplesNode =null;
        Node finesNode =null;
        Root root = new Root();
        Document doc = buildDocument("FineData.xml");
        Node rootNode = doc.getFirstChild();
        NodeList rootChilds = rootNode.getChildNodes();
        for(int i = 0; i< rootChilds.getLength();i++)
        {
            if(rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE)
                continue;
            if ("fines".equals(rootChilds.item(i).getNodeName()))
                finesNode = rootChilds.item(i);
        }
        if (finesNode == null)
            return root;
        List<Fine> fineList =new ArrayList<>();
        NodeList finesChilds = finesNode.getChildNodes();
        for(int i = 0; i< finesChilds.getLength();i++)
        {
            if(finesChilds.item(i).getNodeType() != Node.ELEMENT_NODE)
                continue;
            if(!finesChilds.item(i).getNodeName().equals("fine"))
                continue;
            int key = 0;
            String name = null;
            String goss = null;
            String type = null;
            String date = null;

            NodeList fineChilds = finesChilds.item(i).getChildNodes();
            for(int j =0; j< fineChilds.getLength();j++)
            {
                if(fineChilds.item(j).getNodeType() != Node.ELEMENT_NODE)
                    continue;
                switch (fineChilds.item(j).getNodeName())
                {
                    case "key":
                        key = Integer.valueOf(fineChilds.item(j).getTextContent());
                    case "name":
                        name = fineChilds.item(j).getTextContent();
                    case "goss":
                        goss = fineChilds.item(j).getTextContent();
                    case "type":
                        type = fineChilds.item(j).getTextContent();
                    case "date":
                        date = fineChilds.item(j).getTextContent();
                }
            }
            Fine fine = new Fine(name,goss,type,date,key);
            fineList.add(fine);
        }
        root.setFine(fineList);
        doc = buildDocument("peopleData.xml");
        rootNode = doc.getFirstChild();
        rootChilds = rootNode.getChildNodes();
        for(int i = 0; i< rootChilds.getLength();i++)
        {
            if(rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE)
                continue;
            if ("peoples".equals(rootChilds.item(i).getNodeName()))
                peoplesNode = rootChilds.item(i);
        }
        if (peoplesNode == null)
            return root;
        NodeList peoplesChilds = peoplesNode.getChildNodes();
        List<People> peopleList= new ArrayList<>();
        for(int i = 0; i<peoplesChilds.getLength();i++)
        {
            if(peoplesChilds.item(i).getNodeType() != Node.ELEMENT_NODE)
                continue;
            if(!peoplesChilds.item(i).getNodeName().equals("people"))
                continue;
            int key=0;
            String name=null;
            String pass=null;
            String goss_number=null;
            String mark=null;
            String date=null;
            NodeList peopleChilds = peoplesChilds.item(i).getChildNodes();
            for(int j=0;j<peopleChilds.getLength();j++)
            {
                if(peopleChilds.item(j).getNodeType() != Node.ELEMENT_NODE)
                    continue;
                switch (peopleChilds.item(j).getNodeName())
                {
                    case "key":
                        key =Integer.valueOf(peopleChilds.item(j).getTextContent());
                    case "name":
                        name = peopleChilds.item(j).getTextContent();
                    case "pass":
                        pass = peopleChilds.item(j).getTextContent();
                    case "goss_number":
                        goss_number = peopleChilds.item(j).getTextContent();
                    case "mark":
                        mark = peopleChilds.item(j).getTextContent();
                    case"date_techn_inspect":
                        date =peopleChilds.item(j).getTextContent();;
                }
            }
            List<Fine> temp = new ArrayList<>();
            for(int j=0;j<fineList.size();j++)
            {
                if(fineList.get(j).getKey()==key)
                    temp.add(fineList.get(j));

            }
            People people =new People(name,pass,goss_number,mark,date,temp,key);
            peopleList.add(people);
        }
        root.setPeople(peopleList);

        return root;
    }
    private static Document buildDocument(String file) throws Exception
    {
        File filexml = new File(file);//берем файл
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        doc = (Document) dbf.newDocumentBuilder().parse(filexml); //Парсим файл
        return doc;
    }
    public static void main(String[] args) throws Exception {
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
                try {
                    admin_window.main(parse_xml());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                main_frame.setVisible(false);
            }
        });;
        main_frame.setVisible(true);
    }
}