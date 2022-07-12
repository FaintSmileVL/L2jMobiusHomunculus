package org.l2jmobius.gameserver.data;

import lombok.Getter;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.regex.Pattern;

/**
 * @author : Alice
 * @date : 22.12.2021
 * @time : 17:38
 */
public class XMLWriter {
    public static Pattern skillPattern = Pattern.compile("(step(\\d{1}))[\\_]?(skill(\\d{2}))");
    public static Pattern actionPattern = Pattern.compile("(step(\\d{1}))[\\_]?(action(\\d{2}))");

    @Getter(lazy = true)
    private static final XMLWriter instance = new XMLWriter();

    private Document xmlDoc = null;
    public Element getRootElement() {
        return xmlDoc.getRootElement();
    }

    public void init() {
        if (xmlDoc != null) {
            xmlDoc = null;
        }
        // Создаем документ
        xmlDoc = new Document();
        // Создаем корневой элемент
        Element list = new Element("list");
        // Добавляем корневой элемент в документ
        xmlDoc.setRootElement(list);
    }

    public void writeToXML(String fileName) {
        File file = new File("./l2data/out/", fileName);
        boolean file_exists = file.exists();
        if (file_exists) {
            file.delete();
        }
        file = new File("l2data/out/" + fileName);

        try {
            // Получаем "красивый" формат для вывода XML
            // с переводами на новую строку и отступами
            Format fmt = Format.getPrettyFormat();
            fmt.setIndent("\t");

            // Выводим созданный XML как поток байт на стандартный
            // вывод и в файл, используя подготовленный формат
            XMLOutputter serializer = new XMLOutputter(fmt);
            // распечатка результата в консоль
            //serializer.output(xmlDoc, System.out);
            serializer.output(xmlDoc, new FileOutputStream(file, file_exists));
        } catch (IOException e) {
            System.err.println(e);
        }

        try {
            File f = new File("./l2data/out/", fileName);
            StringBuilder buffer = new StringBuilder();
            BufferedReader fin = new BufferedReader(new FileReader(f));
            String line;
            while ((line = fin.readLine()) != null) {
                if (!line.isEmpty()) {
                    buffer.append(line).append("\n");
                }
            }
            FileWriter fstream1 = new FileWriter("l2data/out/" + fileName);
            BufferedWriter out1 = new BufferedWriter(fstream1);
            out1.write(buffer.toString());
            out1.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static boolean isNumber(Object obj) {
        if (obj instanceof Float || obj instanceof Integer) {
            return true;
        }
        return false;
    }
}
