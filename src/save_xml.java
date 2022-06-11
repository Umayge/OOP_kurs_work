import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class save_xml {


    public static void save_to_xml(Root roott)throws TransformerFactoryConfigurationError, TransformerException
    {
        File file = new File("FineData1.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document doc =null;
        try{
            doc = dbf.newDocumentBuilder().parse(file);
        }
        catch (Exception e)
        {
            return;
        }
        NodeList fine =  doc.getElementsByTagName("fine");
        NodeList fines = doc.getElementsByTagName("fines");
       // NodeList root =  doc.getElementsByTagName("root");
        for(int i = fine.getLength()-1; i >=0 ; i--)
        {
            fines.item(0).removeChild(fine.item(i));
        }


        for(int i = 0; i < roott.getFine().size(); i++) {
            Element fine_new = doc.createElement("fine");
            fines.item(0).appendChild(fine_new);
            Element key = doc.createElement("key");
            Element name = doc.createElement("name");
            Element goss = doc.createElement("goss");
            Element type = doc.createElement("type");
            Element date = doc.createElement("date");
            key.setTextContent( String.valueOf(roott.getFine().get(i).getKey()));
            name.setTextContent( String.valueOf(roott.getFine().get(i).getName()));
            goss.setTextContent( String.valueOf(roott.getFine().get(i).getGoss()));
            type.setTextContent( String.valueOf(roott.getFine().get(i).getType()));
            date.setTextContent( String.valueOf(roott.getFine().get(i).getDate()));

            fine.item(i).appendChild(key);
            fine.item(i).appendChild(name);
            fine.item(i).appendChild(goss);
            fine.item(i).appendChild(type);
            fine.item(i).appendChild(date);
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Source source = new DOMSource(doc);
        Result result = new StreamResult(file);
        transformer.transform (source, result);


        File file1 = new File("peopleData.xml");
        DocumentBuilderFactory dbf1 = DocumentBuilderFactory.newInstance();
        org.w3c.dom.Document doc1 =null;
        try{
            doc1 = dbf1.newDocumentBuilder().parse(file1);
        }
        catch (Exception e)
        {
            return;
        }
        NodeList people =  doc1.getElementsByTagName("people");
        NodeList peoples = doc1.getElementsByTagName("peoples");
        // NodeList root =  doc.getElementsByTagName("root");
        for(int i = people.getLength()-1; i >=0 ; i--)
        {
            peoples.item(0).removeChild(people.item(i));
        }


        for(int i = 0; i < roott.getPeople().size(); i++) {
            Element people_new = doc1.createElement("people");
            peoples.item(0).appendChild(people_new);

            Element key = doc1.createElement("key");
            Element name = doc1.createElement("name");
            Element pass = doc1.createElement("pass");
            Element goss = doc1.createElement("goss_number");
            Element mark = doc1.createElement("mark");
            Element date = doc1.createElement("date_techn_inspect");
            key.setTextContent( String.valueOf(roott.getPeople().get(i).getKey()));
            name.setTextContent( String.valueOf(roott.getPeople().get(i).getName()));
            pass.setTextContent( String.valueOf(roott.getPeople().get(i).getPass()));
            goss.setTextContent( String.valueOf(roott.getPeople().get(i).getGoss_number()));
            mark.setTextContent( String.valueOf(roott.getPeople().get(i).getMark()));
            date.setTextContent( String.valueOf(roott.getPeople().get(i).getDate_techn_inspect()));

            people.item(i).appendChild(key);
            people.item(i).appendChild(name);
            people.item(i).appendChild(pass);
            people.item(i).appendChild(goss);
            people.item(i).appendChild(mark);
            people.item(i).appendChild(date);
        }
        Transformer transformer1 = TransformerFactory.newInstance().newTransformer();
        Source source1 = new DOMSource(doc1);
        Result result1 = new StreamResult(file1);
        transformer1.transform (source1, result1);


    }
}
