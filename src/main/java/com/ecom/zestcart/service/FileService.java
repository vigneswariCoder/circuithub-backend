package com.ecom.zestcart.service;

import com.ecom.zestcart.model.FileMaster;
import com.ecom.zestcart.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    public List<FileMaster> fileUpload(List<MultipartFile> files, String fileCategory) {
        List<FileMaster> fileMasterList = new ArrayList<>();
        if (Objects.nonNull(files) && !files.isEmpty()) {
            for (MultipartFile file : files) {
                FileMaster findFileMaster = this.fileRepository.findByFileCategory(fileCategory).orElse(new FileMaster());
                String fileName = file.getOriginalFilename();
                findFileMaster.setDocumentName(fileName);
                String extension = fileName.substring(fileName.lastIndexOf("."));
                findFileMaster.setExtention(extension);
                findFileMaster.setFileCategory(fileCategory);
                findFileMaster.setDocumentPath("fileUploads/" + fileCategory + "/" + fileName);
                fileMasterList.add(fileRepository.save(findFileMaster));

                try {
                    String savedFileName = "fileUploads" + File.separator + fileCategory;
                    Path uploadPath = Paths.get(savedFileName);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("File Not Found");
        }
        return fileMasterList;
    }
}
