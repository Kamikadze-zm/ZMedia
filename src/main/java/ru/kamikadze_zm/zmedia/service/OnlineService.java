package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.model.StreamInfo;

public interface OnlineService {

    /**
     *
     * @return информацию о трансляции, если активной трансляции нет {@code null}
     */
    public StreamInfo getStreamInfo();

}
