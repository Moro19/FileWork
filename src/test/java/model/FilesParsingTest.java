package model;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FilesParsingTest {
    ClassLoader cl = FilesParsingTest.class.getClassLoader();
    @Test
    void zipParseTest() throws Exception {
        try (
                InputStream resource = cl.getResourceAsStream("example/qa_guru_files.zip");
                ZipInputStream zis = new ZipInputStream(resource)
        ) {
            ZipEntry entry;
            String name;
            long size;

            while((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                size=entry.getSize();
                System.out.println("//////////////////////////////////////////");
                System.out.printf("File name: %s \t File size: %d\n", name, size);

                if (entry.getName().endsWith(".pdf")) {
                    PDF content = new PDF(zis);
                    assertThat(content.title).contains("PDF Test File");
                } else if (entry.getName().contains(".xls")) {


                    XLS content = new XLS(zis);
                    assertThat(content.excel.getSheetAt(0).getRow(8).getCell(2).getStringCellValue()).contains("test28");
                } else if (entry.getName().endsWith(".csv")) {
                    CSVReader reader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = reader.readAll();
                    assertThat(content.get(4)[0]).contains("5;E");
                }
            }
        }
    }
}
