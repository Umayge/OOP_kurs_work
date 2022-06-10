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
        for(int i = 0; i < fine.getLength(); i++) fine.item(i).setTextContent("");

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        Source source = new DOMSource(doc);
        Result result = new StreamResult(file);
        transformer.transform (source, result);
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


    }
}
