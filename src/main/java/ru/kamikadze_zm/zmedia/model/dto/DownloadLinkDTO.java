package ru.kamikadze_zm.zmedia.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import ru.kamikadze_zm.zmedia.util.StringUtil;

public class DownloadLinkDTO {

    @Size(max = 300, message = "Описание ссылки должно содержать не более {max} символов")
    private String description;
    @NotBlank(message = "Укажите ссылку")
    @Size(max = 750, message = "Ссылка должна содержать не более {max} символов")
    private String link;

    public DownloadLinkDTO() {
    }

    public DownloadLinkDTO(String description, String link) {
        this.description = description;
        this.link = link;
    }

    public String getDescription() {
        if (StringUtil.isEmptyString(description)) {
            return null;
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "DownloadLinkDTO{" + "description=" + description + ", link=" + link + '}';
    }
}
