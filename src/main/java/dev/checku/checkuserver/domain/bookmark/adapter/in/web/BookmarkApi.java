package dev.checku.checkuserver.domain.bookmark.adapter.in.web;

import dev.checku.checkuserver.domain.bookmark.application.port.in.CreateBookmarkUseCase;
import dev.checku.checkuserver.domain.bookmark.application.port.in.DeleteBookmarkUseCase;
import dev.checku.checkuserver.domain.bookmark.application.port.in.GetBookmarkUseCase;
import dev.checku.checkuserver.domain.bookmark.domain.Bookmark;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkApi {

    private final CreateBookmarkUseCase createBookmarkUseCase;
    private final GetBookmarkUseCase getBookmarkUseCase;
    private final DeleteBookmarkUseCase deleteBookmarkUseCase;

    @PostMapping

    public ResponseEntity<CreateBookmarkResponse> create(
            @Valid @RequestBody CreateBookmarkRequest request
    ) {
        Bookmark bookmark = createBookmarkUseCase.create(request.getUserId(), request.getSubjectNumber());
        return ResponseEntity.ok(new CreateBookmarkResponse(bookmark.getId()));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(
            @Valid @RequestBody DeleteBookmarkRequest request
    ) {
        deleteBookmarkUseCase.delete(request.getUserId(), request.getSubjectNumber());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetBookmarkResponse>> getAll(
            @RequestParam("userId") Long userId
    ) {
        List<Bookmark> bookmarks = getBookmarkUseCase.getAllByUserId(userId);
        return ResponseEntity.ok(null); //TODO
    }
}
