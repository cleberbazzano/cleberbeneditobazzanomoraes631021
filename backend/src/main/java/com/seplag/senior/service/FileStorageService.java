package com.seplag.senior.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FileStorageService {

    private final MinioClient minioClient;
    private final String bucketName;

    public FileStorageService(MinioClient minioClient, @Value("${minio.bucket-name}") String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    // Metodo para efetuar o upload e retornar o nome unico do ficheiro
    public String uploadFile(MultipartFile file) {
        try {
            // Gera um identificador unico para evitar nomes duplicados
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o ficheiro para o MinIO", e);
        }
    }

    // Requisito Senior: Gerar Presigned URL com expiracao de 30 minutos
    public String getPresignedUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(30, TimeUnit.MINUTES)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar a URL segura do ficheiro", e);
        }
    }
}