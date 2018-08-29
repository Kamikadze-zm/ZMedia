package ru.kamikadze_zm.zmedia.exception;

public class UploadFileException extends Exception {

    public UploadFileException() {
        super("Ошибка загрузки файла");
    }

    public UploadFileException(String message) {
        super(message);
    }
}
