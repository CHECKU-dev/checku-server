package dev.checku.checkuserver.domain.bookmark.application.port.in;

import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;

import java.util.List;

public interface GetBookmarkUseCase {

    List<Bookmark> getAllByUserId(Long userId);
}
