package dev.checku.checkuserver.domain.bookmark.application.port.out;

public interface DeleteBookmarkPort {

    void delete(Long userId, String subjectNumber);
}
