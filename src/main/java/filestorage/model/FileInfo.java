package filestorage.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
public class FileInfo {
    private byte[] file;
    private String name;
    private String type;
    private long size;
    private Timestamp uploadTime;
    private Timestamp lastChangeTime;

    public FileInfo(MultipartFile file) throws IOException {
        this.file = file.getBytes();
        name = file.getOriginalFilename();
        assert name != null;
        type = name.substring(name.lastIndexOf(".") + 1);
        size = file.getSize();
        uploadTime = new Timestamp(System.currentTimeMillis());
        lastChangeTime = new Timestamp(System.currentTimeMillis());
    }

    public void setName(String name) {
        this.name = name;
        String type = name.substring(name.lastIndexOf(".") + 1);
        if (!this.type.equals(type))
            this.type = type;
        lastChangeTime = new Timestamp(System.currentTimeMillis());
    }
}
