package br.com.acme.ang.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.acme.ang.config.AmazonConfigTest;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = {FileStorageService.class, AmazonConfigTest.class})
@TestPropertySource("classpath:/amazon.properties")
public class FileStorageServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceTest.class);

    private static final String TEST_DATA = "test.txt";

    @Before
    public void prepare() throws IOException {
        File file = new File(TEST_DATA);

        if (file.createNewFile()) {
            System.out.println("File is created!");

            FileWriter writer = new FileWriter(file);
            writer.write("Test data");
            writer.close();

        } else {
            System.out.println("File already exists.");
        }
    }

    @Autowired
    private FileStorageService service;

    @Test
    @Ignore
    public void uploadFile() throws IOException {
        service.upload(new File(TEST_DATA));
    }

    @Test
    @Ignore
    public void deleteFile() throws IOException {
        service.delete(TEST_DATA);
    }

    @Test
    @Ignore
    public void listFile() throws IOException {
        Integer i = service.list().size();

        logger.debug("test list.size: {}", i);
    }


    @Test
    public void listPagingFile() throws IOException {
        Integer i = service.listPaging("").getListFileName().size();

        logger.debug("test list.size: {}", i);
    }

}