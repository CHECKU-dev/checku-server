package dev.checku.checkuserver.domain.bookmark.application.port.out;

import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;

import java.util.List;

public interface GetBookmarkPort {

    List<Bookmark> getAllByUserId(Long userId);
}
