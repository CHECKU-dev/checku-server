package dev.checku.checkuserver.domain.bookmark.application.port.out;

import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;

import java.awt.print.Book;

public interface CreateBookmarkPort {

    Bookmark create(Long userId, String subjectNumber);
}
