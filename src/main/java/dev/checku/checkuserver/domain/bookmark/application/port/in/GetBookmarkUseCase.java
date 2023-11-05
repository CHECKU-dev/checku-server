package dev.checku.checkuserver.domain.bookmark.application.port.in;

import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;

public interface GetBookmarkUseCase {

    Bookmark getByUserId(Long userId);
}
