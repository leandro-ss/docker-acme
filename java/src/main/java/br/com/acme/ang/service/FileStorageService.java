package br.com.acme.ang.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.acme.ang.domain.ListPagingTO;
import br.com.acme.ang.exceptions.NotValidFileException;

@Service
public class FileStorageService implements IFileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.endpointUrl}")
    private String endpointUrl;
    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public String find(String fileName) {
        logger.debug("find fileName: {}, bucketName: {}, endpointUrl: {}", fileName, bucketName, endpointUrl);

        this.amazonS3.getObject(new GetObjectRequest(bucketName, fileName));

        return this.endpointUrl + "/" + this.bucketName + "/" + fileName;
    }

    @Override
    public String delete(String fileName) {
        logger.debug("delete fileName: {}, bucketName: {}, endpointUrl: {}", fileName, bucketName, endpointUrl);

        this.amazonS3.deleteObject(new DeleteObjectRequest(bucketName, fileName));

        return this.endpointUrl + "/" + this.bucketName + "/" + fileName;
    }

    @Override
    public String copy(String filename) {
        logger.debug("copy oldFileName: {}",filename);

        return this.copy(filename, "copy_"+filename);
    }

    public String rename(String oldFileName, String newFileName) {
        logger.debug("rename oldFileName: {}, newFileName: {}",oldFileName,newFileName);
        logger.debug("copy bucketName: {}",bucketName);

        this.copy(oldFileName, newFileName);
        this.delete(oldFileName);

        return this.endpointUrl + "/" + this.bucketName + "/" + newFileName;
    }

    public ListPagingTO listPaging(String token) {

        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName)
            .withMaxKeys(ListPagingTO.PAGING_SIZE);

        if(token != null && ! token.isEmpty()){
            request.withContinuationToken(token);
        }

        ListPagingTO to = new ListPagingTO();

        ListObjectsV2Result result = amazonS3.listObjectsV2(request);

        result.getObjectSummaries().forEach(s -> to.getListFileName().add(s.getKey()));
        to.setNextContinuationToken(result.getNextContinuationToken());

        return to;
    }

    public String upload(MultipartFile multiFile) {

        File file = new File(multiFile.getOriginalFilename());

        try (FileOutputStream fos = new FileOutputStream(file)){

            fos.write(multiFile.getBytes());

		} catch (IOException e) {
            
			throw new NotValidFileException(e);
        }
        
        String result = upload(file);

        file.delete();

        return result;
    }

    public String upload(File file) {
        logger.debug("upload file: {}, bucketName: {}, endpointUrl: {}", file, bucketName, endpointUrl);

        String fileName = this.generateFileName(file);

        this.amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));

        return this.endpointUrl + "/" + this.bucketName + "/" + fileName;
    }

    private String generateFileName(File file) {
        String result = file.getName().replace(" ", "_");

        logger.debug("generateFileName return: {}", result);

        return result;
    }

    private String copy(String oldFName, String newFName) {
        logger.debug("copy oldFileName: {}, newFileName: {}",oldFName,newFName);
        logger.debug("copy bucketName: {}",bucketName);

        String b = bucketName;

        this.amazonS3.copyObject( new CopyObjectRequest(b,oldFName,b,newFName));

        return this.endpointUrl + "/" + this.bucketName + "/" + newFName;
    }
}