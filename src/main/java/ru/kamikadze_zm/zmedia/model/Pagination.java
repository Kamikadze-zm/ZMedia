package ru.kamikadze_zm.zmedia.model;

public class Pagination {

    private final int currentPage;
    private final int pages;

    /**
     * Создает объект Pagination и проверяет корректность {@code pageNumber}.
     *
     * @param pageNumber номер текущей страницы.
     * @param pageSize кол-во элементов на страницу.
     * @param totalCount общее кол-во елементов.
     */
    public Pagination(int pageNumber, int pageSize, long totalCount) {
        this.pages = (int) Math.ceil((double) totalCount / (double) pageSize);
        if (pageNumber < 1) {
            this.currentPage = 1;
        } else if (pageNumber <= pages) {
            this.currentPage = pageNumber;
        } else {
            this.currentPage = pages;
        }
    }

    /**
     * Возвращает корректный номер текущей страницы.
     *
     * @return номер текущей страницы.
     */
    public int getCurrentPage() {
        return currentPage;
    }

    public int getPages() {
        return pages;
    }
}
