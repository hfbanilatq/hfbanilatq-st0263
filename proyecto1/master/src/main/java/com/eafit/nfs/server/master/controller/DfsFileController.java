package com.eafit.nfs.server.master.controller;

import com.eafit.nfs.server.master.service.DfsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("file")
public class DfsFileController {
    private DfsFileService dfsFileService;

    @Autowired
    public void setDfsFileService(DfsFileService dfsFileService) {
        this.dfsFileService = dfsFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        String message;

        try {
            // Aquí puedes procesar o almacenar el archivo...
            byte[] fileBytes = file.getBytes();
            String originalFilename = file.getOriginalFilename();
            List<byte[]> blocks = dfsFileService.splitFileIntoBlocks(fileBytes);

            message = "Uploaded the file successfully: " + originalFilename;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping("/get/{fileId}")
    public ResponseEntity<byte[]> obtainFileById(@PathVariable Long fileId) {
        return new ResponseEntity<>(null, HttpStatus.OK );
    }
}
