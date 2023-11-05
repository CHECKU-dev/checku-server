package dev.checku.checkuserver.domain.bookmark.adapter.in.web;

import dev.checku.checkuserver.domain.bookmark.application.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmarks")
public class BookmarkApi {

    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<RegisterBookmarkResponse> register(
            @Valid @RequestBody RegisterBookmarkRequest request
    ) {
        Long id = bookmarkService.register(request);
        return ResponseEntity.ok(new RegisterBookmarkResponse(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeBookmark(
            @Valid @RequestBody RemoveBookmarkRequest request
    ) {
        bookmarkService.remove(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<GetBookmarkResponse>> getBookmarks(
            @RequestParam("userId") Long userId
    ) {
        List<GetBookmarkResponse> response = bookmarkService.getAllBy(userId);
        return ResponseEntity.ok(response);
    }
}
