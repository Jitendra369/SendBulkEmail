package com.sendhrresume.batch.pdf;

import com.sendhrresume.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class PdfTableReader implements ItemReader<User> {

    private final Iterator<User> iterator;

    public PdfTableReader(String filePath) {
        List<User> users = new ArrayList<>();

        File file = new File(filePath);

//         read the data from the table
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
                        log.info("read hr data from file " + user.toString());
                    }
                }
            }
            document.close();
        } catch (Exception e) {
            log.error("Error reading PDF file: {}", filePath, e);

            throw new RuntimeException(
                    "Failed to process PDF: " + filePath, e);
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
