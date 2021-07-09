package com.adminpro.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface UploadFileService {

    Resource load(String nombreFoto) throws MalformedURLException;

    String upload(MultipartFile archivo) throws IOException;

    void eliminar(String nombreFoto);

    Path getPath(String nombreFoto);
 }
