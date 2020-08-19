package filestorage.dto;

import filestorage.model.FileInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class FileInfoDto {
    private UUID uuid;
    private String name;
    private String type;
    private long size;
    private Timestamp uploadTime;
    private Timestamp lastChangeTime;
    private URI downloadUrl;

    public FileInfoDto(UUID uuid, FileInfo fileInfo) {
        this.uuid = uuid;
        name = fileInfo.getName();
        type = fileInfo.getType();
        size = fileInfo.getSize();
        uploadTime = fileInfo.getUploadTime();
        lastChangeTime = fileInfo.getLastChangeTime();
        downloadUrl = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/api/file/" + uuid.toString())
                .build().toUri();
    }
}
