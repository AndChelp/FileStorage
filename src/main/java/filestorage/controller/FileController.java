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
        return new HttpEntity<>(
                fileInfo.getFile(),
                crateHeader(fileInfo.getName(), (int) fileInfo.getSize()));
    }

    @GetMapping
    public HttpEntity<byte[]> downloadZip(@RequestParam UUID[] uuids) throws IOException {
        byte[] archive = fileService.getFilesAsArchive(uuids);
        return new HttpEntity<>(
                archive,
                crateHeader("files.zip", archive.length));
    }

    private HttpHeaders crateHeader(String filename, int fileSize) {
        HttpHeaders header = new HttpHeaders();
        header.set("Content-Disposition", "attachment; filename=" + filename);
        header.setContentLength(fileSize);
        return header;
    }
}






