package ch.makery.address.view;

import java.io.File;
import java.io.IOException;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
 
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class TableBuchungAuslesen
{
    private String tableName;
 
    private String windowTitle;
 
    private String focusColor;
 
    private NodeList tableNameNodeList;
 
    private NodeList titleNodeList;
 
    private NodeList colorNodeList;
 
    // Liest alle relevanten Daten aus der tableConfig.xml aus
    public void readtableConfig()
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("tableConfig.xml"));
 
            tableNameNodeList = document.getElementsByTagName("tableName");
            tableName = null;
 
            if (tableNameNodeList.getLength() > 0)
            {
                tableName = tableNameNodeList.item(0).getTextContent().toString();
                setTableName(tableName);
            }
 
            titleNodeList = document.getElementsByTagName("title");
            windowTitle = null;
 
            if (titleNodeList.getLength() > 0)
            {
                windowTitle = titleNodeList.item(0).getTextContent().toString();
                setWindowTitle(windowTitle);
            }
 
            colorNodeList = document.getElementsByTagName("color");
            focusColor = null;
 
            if (colorNodeList.getLength() > 0)
            {
                focusColor = colorNodeList.item(0).getTextContent().toString();
                setFocusColor(focusColor);
            }
        }
 
        catch (IOException e)
        {
            System.out.println("IO Fehler");
        }
 
        catch (ParserConfigurationException e)
        {
            System.out.println("Parser Fehler");
        }
 
        catch (SAXException e)
        {
            System.out.println("SAX Fehler");
        }
    }
 
    public void writetableConfig()
    {
        try
        {
            // Erzeugung eines DocumentBuilderFactory Objekts
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("tableConfig.xml"));
 
            tableNameNodeList = document.getElementsByTagName("tableName");
 
            if (tableNameNodeList.getLength() > 0)
            {
                tableNameNodeList.item(0).setTextContent(tableName);
            }
 
            titleNodeList = document.getElementsByTagName("title");
 
            if (titleNodeList.getLength() > 0)
            {
                titleNodeList.item(0).setTextContent(windowTitle);
            }
 
            colorNodeList = document.getElementsByTagName("color");
 
            if (colorNodeList.getLength() > 0)
            {
                colorNodeList.item(0).setTextContent(focusColor);
            }
        }
 
        catch (IOException e)
        {
            System.out.println("IO Fehler");
        }
 
        catch (ParserConfigurationException e)
        {
            System.out.println("Parser Fehler");
        }
 
        catch (SAXException e)
        {
            System.out.println("SAX Fehler");
        }
    }
 
    public String getFocusColor()
    {
        return focusColor;
    }
 
    public void setFocusColor(String focusColor)
    {
        this.focusColor = focusColor;
    }
 
    public String getTableName()
    {
        return tableName;
    }
 
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
 
    public String getWindowTitle()
    {
        return windowTitle;
    }
 
    public void setWindowTitle(String windowTitle)
    {
        this.windowTitle = windowTitle;
    }
}






//package ch.makery.address.view;
//
//import javafx.fxml.FXML;
//import java.io.FileOutputStream;
//import java.io.IOException;
// 
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.TransformerFactoryConfigurationError;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
// 
//import org.w3c.dom.Document;
//import org.xml.sax.SAXException;
//
//import ch.makery.address.MainApp;
//
//public class TableBuchungAuslesen {
//	
////	 private MainApp mainApp;
//	
//	
//	TableBuchungAuslesen() {
//	       
//        final String XMLFILENAME = "tableConfig.xml";
//       
//        try {
//           
//           
//           
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.parse(XMLFILENAME);
//           
//            String tableName = document.getElementsByTagName("tableName").item(0).getTextContent();
//            String title = document.getElementsByTagName("title").item(0).getTextContent();
//            String color = document.getElementsByTagName("color").item(0).getTextContent();
//           
//            System.out.println("Old Values:");
//            System.out.println(tableName);
//            System.out.println(title);
//            System.out.println(color);
//           
//            tableName = "neueTabelle";
//            title = "Hallo Universum";
//            color = "giftgelb";
//           
//            System.out.println("\nNew Values:");
//            System.out.println(tableName);
//            System.out.println(title);
//            System.out.println(color);
//       
//            document.getElementsByTagName("tableName").item(0).setTextContent(tableName);
////            document.getElementsByTagName("title").item(0).setTextContent(title);
////            document.getElementsByTagName("color").item(0).setTextContent(color);
//           
//            TransformerFactory.newInstance().newTransformer().transform(
//                    new DOMSource(document), new StreamResult(new FileOutputStream(XMLFILENAME)));
//           
//        } catch (ParserConfigurationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (SAXException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TransformerConfigurationException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TransformerException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (TransformerFactoryConfigurationError e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//
//}
