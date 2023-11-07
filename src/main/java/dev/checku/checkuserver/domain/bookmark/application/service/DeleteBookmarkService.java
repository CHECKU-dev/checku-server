package dev.checku.checkuserver.domain.bookmark.application.service;

import dev.checku.checkuserver.common.UseCase;
import dev.checku.checkuserver.domain.bookmark.application.port.in.DeleteBookmarkUseCase;
import dev.checku.checkuserver.domain.bookmark.application.port.out.DeleteBookmarkPort;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteBookmarkService implements DeleteBookmarkUseCase {

    private final DeleteBookmarkPort deleteBookmarkPort;

    @Override
    public void delete(Long userId, String subjectNumber) {
        deleteBookmarkPort.delete(userId, subjectNumber);
    }
}
