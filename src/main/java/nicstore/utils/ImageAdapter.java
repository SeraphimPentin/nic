package nicstore.utils;

import nicstore.exceptions.ImageUploadException;
import nicstore.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

@Component
public class ImageAdapter {

    private static final Logger logger = LogManager.getLogger(ReviewService.class);


    public void saveImage(MultipartFile file, String path, String fileName) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            // Генерируем путь к файлу
            String fullPath = path + File.separator + fileName;
            File imageFile = new File(fullPath);
            // Сохраняем содержимое MultipartFile в файл
            file.transferTo(imageFile);
        } catch (IOException e) {
            logger.error("Ошибка при сохранении фото: " + e.getMessage());
            throw new ImageUploadException("Ошибка при загрузке изображения");
        }
    }


    public void deleteFolder(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException e) {
            logger.error("Ошибка при удалении папки: " + path);
            throw new RuntimeException("Ошибка при удалении папки");
        }
    }


}
