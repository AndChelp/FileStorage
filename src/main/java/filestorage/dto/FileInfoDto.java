package filestorage.dto;

import filestorage.model.FileInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class FileInfoDto {
    private UUID uuid;
    private String name;
    private String type;
    private long size;
    private long uploadTime;
    private long lastChangeTime;
    private String downloadUrl;

    public FileInfoDto(UUID uuid, FileInfo fileInfo) {
        this.uuid = uuid;
        name = fileInfo.getName();
        type = fileInfo.getType();
        size = fileInfo.getSize();
        uploadTime = fileInfo.getUploadTime().getTime();
        lastChangeTime = fileInfo.getLastChangeTime().getTime();
        downloadUrl = "/api/file/" + uuid.toString();
    }
}
