package filestorage.service;

import filestorage.dto.FileInfoDto;
import filestorage.exception.FileDataNotFoundException;
import filestorage.exception.FileTypeNotSupportedException;
import filestorage.exception.NullFileException;
import filestorage.model.FileInfo;
import filestorage.utils.ModelHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class FileService {

    public FileInfoDto getFileInfo(UUID uuid) {
        FileInfo fileInfo = ModelHelper.dataStorage.get(uuid);
        if (fileInfo == null)
            throw new FileDataNotFoundException("File information with UUID = " + uuid + " not found");
        return new FileInfoDto(uuid, fileInfo);
    }

    public void updateFileName(UUID uuid, String newName) {
        ModelHelper.dataStorage.get(uuid).setName(newName);
    }

    public void addFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isEmpty())
            throw new NullFileException("Empty filename or file not received");

        int lastDotPosition = filename.lastIndexOf(".") + 1;
        String fileType = filename.substring(lastDotPosition);
        if (lastDotPosition == 0 || !ModelHelper.supportedTypes.contains(fileType))
            throw new FileTypeNotSupportedException("Type '" + fileType + "' not supported");

        UUID uuid = UUID.randomUUID();
        ModelHelper.dataStorage.put(uuid, new FileInfo(file));
    }

    public void deleteFile(UUID uuid) {
        ModelHelper.dataStorage.remove(uuid);
    }

    public FileInfo getFile(UUID uuid) {
        return ModelHelper.dataStorage.get(uuid);
    }

    public List<String> getFileNames() {
        return ModelHelper.dataStorage.values().stream()
                .map(FileInfo::getName)
                .collect(Collectors.toList());
    }

    public List<FileInfoDto> getFilesInfo(String nameQuery, Timestamp fromDate, Timestamp tillDate, Set<String> types) {
        Stream<Map.Entry<UUID, FileInfo>> fileInfoStream = ModelHelper.dataStorage.entrySet().stream();

        if (nameQuery != null && !nameQuery.isEmpty())
            fileInfoStream = fileInfoStream.filter(uuidFileInfoEntry ->
                    uuidFileInfoEntry.getValue().getName()
                            .contains(nameQuery));

        if (fromDate != null)
            fileInfoStream = fileInfoStream.filter(uuidFileInfoEntry ->
                    uuidFileInfoEntry.getValue().getLastChangeTime()
                            .after(fromDate));

        if (tillDate != null)
            fileInfoStream = fileInfoStream.filter(uuidFileInfoEntry ->
                    uuidFileInfoEntry.getValue().getLastChangeTime()
                            .before(tillDate));

        if (types != null && !types.isEmpty())
            fileInfoStream = fileInfoStream.filter(uuidFileInfoEntry ->
                    types.contains(uuidFileInfoEntry.getValue().getType()));

        return fileInfoStream.map(uuidFileInfoEntry ->
                new FileInfoDto(uuidFileInfoEntry.getKey(), uuidFileInfoEntry.getValue()))
                .collect(Collectors.toList());
    }

    public byte[] getFilesAsArchive(UUID[] uuids) throws IOException {

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            try (ZipOutputStream zos = new ZipOutputStream(bos)) {
                for (UUID uuid : uuids) {
                    zos.putNextEntry(new ZipEntry(ModelHelper.dataStorage.get(uuid).getName()));
                    zos.write(ModelHelper.dataStorage.get(uuid).getFile());
                    zos.closeEntry();
                }
            }
            return bos.toByteArray();
        }
    }

}














