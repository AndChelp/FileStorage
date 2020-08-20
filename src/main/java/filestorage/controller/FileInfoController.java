package filestorage.controller;

import filestorage.dto.FileInfoDto;
import filestorage.dto.ListDto;
import filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@RequestMapping("/api/file/info")
@RestController
public class FileInfoController {
    @Autowired
    private FileService fileService;

    @GetMapping("/names")
    public ResponseEntity<ListDto> getFileNames() {
        return new ResponseEntity<>(
                new ListDto(fileService.getFileNames()),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ListDto> getFilesInfo(@RequestParam(required = false, name = "name_contains")
                                                        String nameContains,
                                                @RequestParam(required = false, name = "from_date")
                                                        Long fromDate,
                                                @RequestParam(required = false, name = "till_date")
                                                        Long tillDate,
                                                @RequestParam(required = false)
                                                        Set<String> types) {

        return new ResponseEntity<>(
                new ListDto(fileService.getFilesInfo(nameContains, fromDate, tillDate, types)),
                HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public FileInfoDto getFileInfo(@PathVariable UUID uuid) {
        return fileService.getFileInfo(uuid);
    }

    @PutMapping("/{uuid}")
    public void updateFileName(@PathVariable UUID uuid, @RequestParam String name) {
        fileService.updateFileName(uuid, name);
    }
}
