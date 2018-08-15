package br.com.acme.ang.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import br.com.acme.ang.domain.ListPagingTO;

public interface IFileStorageService  {

    public String find(String fileName);

    public String copy(String fileName);

    public String delete(String fileName);

    public String upload(MultipartFile file);

    public String rename(String oldFName, String newFName);

    public ListPagingTO listPaging(String token);
}
