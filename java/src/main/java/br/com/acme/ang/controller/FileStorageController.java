package br.com.acme.ang.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.acme.ang.domain.ListPagingTO;
import br.com.acme.ang.domain.UploadFileResponseTO;
import br.com.acme.ang.exceptions.NotValidFileException;
import br.com.acme.ang.service.IFileStorageService;

@Controller
@RequestMapping(value = "/file")
public class FileStorageController {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageController.class);

    @Autowired
    private IFileStorageService service;

    @GetMapping
    public @ResponseBody ListPagingTO listFile (@RequestParam(required=false) String token) {

        ListPagingTO result = service.listPaging(token);

        logger.debug("listFile - result: {}", result);

        return result;
    }

    @GetMapping("/delete/{fileName}")
    public @ResponseBody ResponseEntity<?> delete(@PathVariable("fileName") String fileName) {

        String result = service.delete(fileName);

        logger.debug("listFile - result: {}", result);

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @GetMapping("/rename")
    public @ResponseBody ResponseEntity<?> rename(@RequestParam String oldFname, @RequestParam String newFname) {

        String result = service.rename(oldFname, newFname);

        logger.debug("listFile - result: {}", result);

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }


    @PostMapping("/upload")
    public @ResponseBody ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String result = service.upload(file);

        logger.debug("uploadFile - result: {}", result);

        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource;
		try {

            resource = new UrlResource(service.find(fileName));
            
		} catch (MalformedURLException e) {
			throw new NotValidFileException(e);
		}

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}