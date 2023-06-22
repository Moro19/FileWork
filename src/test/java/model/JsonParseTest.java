package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JsonParseTest {


    ClassLoader cl = FilesParsingTest.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void jsonParseTest() throws Exception {
        System.setProperty("chromeoptions.args", "--remote-allow-origins=*");

        try (
                InputStream resource = cl.getResourceAsStream("example/qa_guru_files_json.json");
                InputStreamReader reader = new InputStreamReader(resource)
        ) {
            ManJson manJson = objectMapper.readValue(reader, ManJson.class);
            assertThat(manJson.name).isEqualTo("Nikolay");
            assertThat(manJson.email).isEqualTo("amaru89@gmail.com");
            assertThat(manJson.subjects).contains("English", "Maths", "Arts");
        }
    }
    }




