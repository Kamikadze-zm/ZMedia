package ru.kamikadze_zm.zmedia.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.kamikadze_zm.zmedia.exception.UploadFileException;
import ru.kamikadze_zm.zmedia.exception.ValidationFieldsException;
import static ru.kamikadze_zm.zmedia.util.Constants.PATH_IMAGES;
import ru.kamikadze_zm.zmedia.util.ImageManager;
import ru.kamikadze_zm.zmedia.util.ImageManager.ThumbnailType;

@RestController
@PreAuthorize("hasAnyAuthority('ADMIN', 'MODER')")//, 'UPLOADER')")
@RequestMapping("/api/files/")
public class FileController {

    private static final Logger LOG = LogManager.getLogger(FileController.class);

    @Autowired
    private ImageManager im;

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam(name = "upload") MultipartFile image, @RequestParam(name = "type") String type) {
        ImageType imageType;
        try {
            imageType = ImageType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOG.warn("Unknown image type: " + type);
            imageType = ImageType.OTHER;
        }
        try {
            return new ResponseEntity<>("\"" + saveImage(image, imageType) + "\"", HttpStatus.OK);
        } catch (UploadFileException e) {
            throw new ValidationFieldsException(ValidationFieldsException.createError("upload", "upload", null, e.getMessage()));
        }
    }

    private String saveImage(MultipartFile image, ImageType type) throws UploadFileException {
        switch (type) {
            case AVATAR:
                return im.addImage(PATH_IMAGES, image, ThumbnailType.AVATAR, true);
            case COVER:
                return im.addImage(PATH_IMAGES, image, ThumbnailType.COVER, true);
            case SCREENSHOT:
                return im.addImage(PATH_IMAGES, image, ThumbnailType.SCREENSHOT, false);
            case OTHER:
            default:
                return im.addImage(PATH_IMAGES, image);
        }
    }

    private static enum ImageType {
        AVATAR,
        COVER,
        SCREENSHOT,
        OTHER;
    }
}
