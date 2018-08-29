package ru.kamikadze_zm.zmedia.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.kamikadze_zm.zmedia.exception.UploadFileException;

@Component
public class ImageManager {

    private static final Logger LOG = LogManager.getLogger(ImageManager.class);

    private static final String IO_EXCEPTION_MESSAGE = "Произошла ошибка, повторите попытку";

    @Value("${resources-location}")
    private String basePath;

    /**
     * Сохраняет изображение на диске под указанным именем.
     *
     * @param path веб путь до папки в которую нужно сохранить изображение.
     * @param file файл с изображением.
     * @param imageName название файла с изображением (без расширения).
     * @return Веб путь до изображения.
     * @throws UploadFileException при пустом файле или если файл не является изображением.
     */
    public String addImage(String path, MultipartFile file, String imageName)
            throws UploadFileException {
        return addImage(path, file, imageName, ThumbnailType.NONE, false);
    }

    public String addImage(String path, MultipartFile inputFile, String imageName,
            ThumbnailType thumbnailType, boolean onlyThumbnail) throws UploadFileException {
        if (inputFile.isEmpty() || inputFile.getSize() <= 0) {
            throw new UploadFileException("Файл пуст или не выбран.");
        }
        String contentType = inputFile.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new UploadFileException("Файл не является изображением");
        }
        String realPath;
        try {
            realPath = webPathToRealPath(path) + File.separator;
        } catch (URISyntaxException e) {
            throw new UploadFileException(IO_EXCEPTION_MESSAGE);
        }
        String ext = FilenameUtils.getExtension(inputFile.getOriginalFilename());
        if (inputFile.getOriginalFilename() == null) {
            ext = "und";
        }
        ext = "." + ext;
        String fileName = imageName + ext;

        if (thumbnailType != ThumbnailType.NONE && onlyThumbnail) {
            fileName = thumbnailType.getPrefix() + fileName;
            generateThumbnail(inputFile, new File(realPath + fileName), thumbnailType);
        } else {
            if (thumbnailType != ThumbnailType.NONE) {
                String thumbnailFileName = thumbnailType.getPrefix() + fileName;
                generateThumbnail(inputFile, new File(realPath + thumbnailFileName), thumbnailType);
            }
            File img = new File(realPath + fileName);
            if (!img.exists()) {
                try {
                    inputFile.transferTo(img);
                } catch (IOException e) {
                    LOG.error("Save image exception: ", e);
                    throw new UploadFileException(IO_EXCEPTION_MESSAGE);
                }
            }
        }
        String webPath = path + "/" + fileName;
        return webPath;
    }

    /**
     * Сохраняет изображение на диске, в качестве имени используется md5 хеш.
     *
     * @param path веб путь до папки в которую нужно сохранить изображение.
     * @param file файл с изображением.
     * @return Веб путь до изображения.
     * @throws UploadFileException при пустом файле или если файл не является изображением.
     */
    public String addImage(String path, MultipartFile file) throws UploadFileException {
        return addImage(path, file, ThumbnailType.NONE, false);
    }

    public String addImage(String path, MultipartFile file, ThumbnailType thumbnailType, boolean onlyThumbnail) throws UploadFileException {
        return addImage(path, file, getMd5ForImage(file), thumbnailType, onlyThumbnail);
    }

    /**
     * @param fullPath полный веб путь до файла, включая имя и расширение файла.
     */
    public void deleteImage(String fullPath) {
        deleteImage(fullPath, false, null);
    }

    public void deleteImage(String fullPath, boolean deleteThumbnail, ThumbnailType thumbnailType) {
        if (fullPath != null && !fullPath.isEmpty()) {
            String realPath;
            try {
                realPath = webPathToRealPath(fullPath);
            } catch (URISyntaxException e) {
                return;
            }
            File file = new File(realPath);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            if (deleteThumbnail) {
                int index = realPath.lastIndexOf(File.separator);
                String thumbnailPath = realPath;
                if (index != -1) {
                    thumbnailPath = thumbnailPath.substring(0, index + 1) + thumbnailType.getPrefix() + thumbnailPath.substring(index + 1);
                }
                File thumbnail = new File(thumbnailPath);
                if (thumbnail.exists() && thumbnail.isFile()) {
                    thumbnail.delete();
                }
            }
        }
    }

    /**
     *
     * @param image изображение
     * @param thumbnailFile файл в который будет записана миниатюра
     * @param type тип миниатюры
     * @throws IOException при ошибке чтения изображения или записи миниатюры в файл
     */
    public void generateThumbnail(InputStream image, File thumbnailFile, ThumbnailType type) throws IOException {
        if (thumbnailFile.exists()) {
            return;
        }
        try {
            Thumbnails.Builder<? extends InputStream> builder = Thumbnails
                    .of(image)
                    .outputQuality(type.getQuality());
            if (type.getHeight() != 0) {
                builder.size(type.getWidth(), type.getHeight())
                        .keepAspectRatio(false);
            } else {
                builder.size(type.getWidth(), 150)
                        .keepAspectRatio(true);
            }
            builder.toFile(thumbnailFile);
        } catch (IOException e) {
            LOG.error("Generate thumbnail exception: ", e);
            throw e;
        }
    }

    private void generateThumbnail(MultipartFile inputFile, File outputFile, ThumbnailType type) throws UploadFileException {
        try (InputStream is = inputFile.getInputStream()) {
            generateThumbnail(is, outputFile, type);
        } catch (IOException e) {
            throw new UploadFileException(IO_EXCEPTION_MESSAGE);
        }
    }

    private String webPathToRealPath(String webPath) throws URISyntaxException {
        String fullPath = basePath;
        if (fullPath.endsWith("/") && webPath.startsWith("/")) {
            fullPath = fullPath.substring(0, fullPath.length() - 1);
        }
        fullPath += webPath;
        if (!fullPath.endsWith("/")) {
            fullPath += "/";
        }
        String uriPath = "";
        try {
            uriPath = new URI(fullPath).getPath();
        } catch (URISyntaxException e) {
            LOG.error("Wrong URI: ", e);
            throw e;
        }
        File file = new File(uriPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    private String getMd5ForImage(MultipartFile file) throws UploadFileException {
        try {
            return Md5Util.getMd5String(file.getBytes());
        } catch (IOException e) {
            LOG.error("Get image bytes exception: ", e);
            throw new UploadFileException(IO_EXCEPTION_MESSAGE);
        }
    }

    public static enum ThumbnailType {
        AVATAR(70, 70, 1, "ava_"),
        COVER(185, 207, 0.8, "cov_"),
        SCREENSHOT(300, 0, 0.5, "scr_"),
        NONE(-1, -1, -1, "none_");

        private final int width;
        private final int height;
        private final double quality;
        private final String prefix;

        private ThumbnailType(int width, int height, double quality, String prefix) {
            this.width = width;
            this.height = height;
            this.quality = quality;
            this.prefix = prefix;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public double getQuality() {
            return quality;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}
