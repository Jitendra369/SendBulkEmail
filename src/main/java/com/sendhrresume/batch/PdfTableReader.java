package com.sendhrresume.batch;

import com.sendhrresume.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class PdfTableReader implements ItemReader<User> {

    private final Iterator<User> iterator;

    public PdfTableReader() {
        List<User> users = new ArrayList<>();

        // todo : correct pdf Upload
        File file = new File("D:\\boot-project\\files\\hr_demo.pdf");
//        try {
//            PDDocument document = Loader.loadPDF(file);
//            PDFTextStripper pdfTextStripper = new PDFTextStripper();
//            String text = pdfTextStripper.getText(document);
//            document.close();
//
//            String[] lines = text.split("\\r?\\n");
//            for (int i = 1; i < lines.length; i++) {
//                String line = lines[i];
//                if (line.isEmpty()) {
//                    continue;
//                }
//
//                String[] data = line.split("\\|");
//                if (data.length >= 2) {
//                    User user = new User();
////                    String name =  data[1] + data[2];
////                    String email = data[3];
//
//                    user.setName(data[0]);
//                    user.setEmail(data[1]);
//                    user.setTitle(data[2]);
//                    user.setCompany(data[3]);
//                    users.add(user);
//                }
//            }
//            this.iterator = users.iterator();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        try {
            PDDocument document = Loader.loadPDF(file);
            ObjectExtractor extractor = new ObjectExtractor(document);
            SpreadsheetExtractionAlgorithm algo = new SpreadsheetExtractionAlgorithm();
            PageIterator pages = extractor.extract();

            while (pages.hasNext()) {
                Page page = pages.next();
                List<Table> tables = algo.extract(page);
                for (Table table : tables) {
                    List<List<RectangularTextContainer>> rows = table.getRows();
                    for (int i = 1; i < rows.size(); i++) {
                        List<RectangularTextContainer> cells = rows.get(i);
                        if (cells.size() < 5) {
                            continue;
                        }
                        User user = new User();
                        user.setName(cells.get(1).getText().trim());
                        user.setEmail(cells.get(2).getText().trim());
                        user.setTitle(cells.get(3).getText().trim());
                        user.setCompany(cells.get(4).getText().trim());
                        users.add(user);
                        log.info("read hr data "+ user.toString());

                    }
                }
            }
            document.close();

        } catch (Exception e) {

        }
        this.iterator = users.iterator();
    }

    @Override
    public User read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.hasNext()) {
            User user = iterator.next();
            System.out.println("READ USER = " + user.getName());
            return user;
        }
        return null;
    }
}
