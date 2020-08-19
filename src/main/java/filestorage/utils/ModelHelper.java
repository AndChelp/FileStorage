package filestorage.utils;

import filestorage.model.FileInfo;

import java.util.*;

public class ModelHelper {
    public static Map<UUID, FileInfo> dataStorage = new LinkedHashMap<>();
    public static Set<String> supportedTypes;

    static {
        supportedTypes = new HashSet<>();
        //Изображения
        supportedTypes.addAll(Arrays.asList("png", "jpg", "gif", "bmp", "psd", "webp", "svg"));
        //Текст
        supportedTypes.addAll(Arrays.asList("log", "txt", "doc", "docx", "html", "pdf", "json", "xml", "xhtml", "css"));
        //Архивы
        supportedTypes.addAll(Arrays.asList("7z", "gz", "rar", "tar", "zip"));
        //Видео
        supportedTypes.addAll(Arrays.asList("mp4", "avi", "mpg", "mov"));
        //Аудио
        supportedTypes.addAll(Arrays.asList("mp3", "m4a", "wav"));
        //Презентации
        supportedTypes.addAll(Arrays.asList("ppt", "pptx", "key"));
        //Таблицы
        supportedTypes.addAll(Arrays.asList("xls", "xlsx", "xlsm", "xl"));
    }
}
