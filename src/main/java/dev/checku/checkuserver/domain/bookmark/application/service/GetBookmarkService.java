package dev.checku.checkuserver.domain.bookmark.application.service;


import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.bookmark.application.port.in.GetBookmarkUseCase;
import dev.checku.checkuserver.domain.bookmark.application.port.out.GetBookmarkPort;
import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetBookmarkService implements GetBookmarkUseCase {

    private final GetBookmarkPort getBookmarkPort;

    @Override
    public List<Bookmark> getAllByUserId(Long userId) {
        return getBookmarkPort.getAllByUserId(userId);
    }
}
