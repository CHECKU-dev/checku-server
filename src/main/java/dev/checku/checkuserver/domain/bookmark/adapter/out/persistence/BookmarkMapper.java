package dev.checku.checkuserver.domain.bookmark.adapter.out.persistence;

import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;
import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

    public Bookmark mapToDomainEntity(BookmarkJpaEntity bookmarkJpaEntity) {
        return Bookmark.builder()
                .id(bookmarkJpaEntity.getId())
                .subjectNumber(new SubjectNumber(bookmarkJpaEntity.getSubjectNumber()))
                .build();
    }
}
