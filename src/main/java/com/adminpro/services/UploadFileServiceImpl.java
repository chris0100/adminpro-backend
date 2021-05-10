package com.adminpro.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService{

    private static final String DIRECTORIO_UPLOAD = "uploads";


    // retorna la foto de acuerdo al nombre que se le coloca
    @Override
    public Resource load(String nombreFoto) throws MalformedURLException {
        Path rutaArchivo = getPath(nombreFoto);
        Resource resource = new UrlResource(rutaArchivo.toUri());

        //Si no existe o no se puede leer tomara la foto por defecto
        if (!resource.exists() && !resource.isReadable()){
            rutaArchivo = Paths.get("src/main/resources/static/img").resolve("sin-foto.png").toAbsolutePath();
            resource = new UrlResource(rutaArchivo.toUri());
            log.info("retorna foto sin contenido");
        }
        return resource;
    }


    // carga en el servidor una foto
    @Override
    public String upload(MultipartFile archivo) throws IOException {
        //Asigna un nombre aleatorio
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace(" ", "-");

        //Obtiene la ruta del archivo
        Path rutaArchivo = getPath(nombreArchivo);

        //copia el archivo en la ruta
        Files.copy(archivo.getInputStream(), rutaArchivo);
        return nombreArchivo;
    }


    //Elimina la imagen
    @Override
    public boolean eliminar(String nombreFoto) {
        if (nombreFoto != null && nombreFoto.length() > 0){
            Path rutaFotoAnterior = getPath(nombreFoto);
            File archivoFotoAnterior = rutaFotoAnterior.toFile();

            if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
                archivoFotoAnterior.delete();
                return true;
            }
        }
        return false;
    }

    //Obtiene la ruta absoluta de acuerdo al nombre de la foto
    @Override
    public Path getPath(String nombreFoto) {
        return Paths.get(DIRECTORIO_UPLOAD).resolve(nombreFoto).toAbsolutePath();
    }
}
