package filestorage.controller;

import filestorage.model.FileInfo;
import filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/api/file")
@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.addFile(file);
    }

    @DeleteMapping("/{uuid}")
    public void deleteFile(@PathVariable UUID uuid) {
        fileService.deleteFile(uuid);
    }

    @GetMapping("/{uuid}")
    public HttpEntity<byte[]> downloadFile(@PathVariable UUID uuid) throws IOException {
        FileInfo fileInfo = fileService.getFile(uuid);
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Disposition", "attachment; filename=" + fileInfo.getName());
        header.setContentLength(fileInfo.getSize());
        return new HttpEntity<>(fileInfo.getFile(), header);
    }

    @GetMapping
    public HttpEntity<byte[]> downloadZip(@RequestParam UUID[] uuids) throws IOException {

        byte[] archive = fileService.getFilesAsArchive(uuids);

        HttpHeaders header = new HttpHeaders();
        header.set("Content-Disposition", "attachment; filename=Files.zip");
        header.setContentLength(archive.length);
        return new HttpEntity<>(archive, header);
    }
}






