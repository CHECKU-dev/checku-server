package dev.checku.checkuserver.domain.bookmark.adapter.out.persistence;

import dev.checku.checkuserver.common.PersistenceAdapter;
import dev.checku.checkuserver.domain.bookmark.application.port.out.CreateBookmarkPort;
import dev.checku.checkuserver.domain.bookmark.application.port.out.DeleteBookmarkPort;
import dev.checku.checkuserver.domain.bookmark.application.port.out.GetBookmarkPort;
import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class BookmarkPersistenceAdapter implements CreateBookmarkPort, GetBookmarkPort, DeleteBookmarkPort {

    private final BookmarkSpringDataRepository bookmarkSpringDataRepository;
    private final BookmarkMapper bookmarkMapper;

    @Override
    public Bookmark create(Long userId, String subjectNumber) {
        BookmarkJpaEntity bookmarkJpaEntity = bookmarkSpringDataRepository.save(BookmarkJpaEntity.create(userId, subjectNumber));
        return bookmarkMapper.mapToDomainEntity(bookmarkJpaEntity);
    }

    @Override
    public void delete(Long id) {
        bookmarkSpringDataRepository.deleteById(id);
    }

    @Override
    public List<Bookmark> getAllByUserId(Long userId) {
        List<BookmarkJpaEntity> bookmarkJpaEntities = bookmarkSpringDataRepository.findAllById(List.of(userId));
        return bookmarkJpaEntities.stream()
                .map(bookmarkMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
