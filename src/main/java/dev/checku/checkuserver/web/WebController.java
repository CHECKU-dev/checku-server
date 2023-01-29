package dev.checku.checkuserver.web;

import dev.checku.checkuserver.domain.log.application.LogService;
import dev.checku.checkuserver.domain.log.dto.LogSearchDto;
import dev.checku.checkuserver.domain.log.enums.OrderBy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class WebController {


//    private final LogService logService;

//    @GetMapping("/")
//    public String main(@Valid LogSearchDto.Request request, Optional<Integer> page, Model model) {
//
//        Pageable pageable = PageRequest.of(
//                page.orElse(0),
//                20,
//                request.getOrderBy() == null ?
//                        Sort.by(Sort.Direction.DESC, OrderBy.CREATE_TIME.name()) :
//                        Sort.by(Sort.Direction.DESC, request.getOrderBy())
//        );
//
//        Page<LogSearchDto.Response> logList = logService.getLogList(pageable);
//        System.out.println(logList.getSize());
//        model.addAttribute("logs", logList);
//        return "index";
//    }
//
//    @GetMapping("/tables")
//    public String getLogs(
//            @Valid LogSearchDto.Request request, Optional<Integer> page, Model model) {
//
//        Pageable pageable = PageRequest.of(
//                page.orElse(0),
//                20,
//                request.getOrderBy() == null ?
//                        Sort.by(Sort.Direction.DESC, OrderBy.CREATE_TIME.name()) :
//                        Sort.by(Sort.Direction.DESC, request.getOrderBy())
//        );
//
//        Page<LogSearchDto.Response> logList = logService.getLogList(pageable);
//        model.addAttribute("logs", logList);
//
//        return "/tables";
//    }
//
//    @RequestMapping("/tables.html")
//    public String table() {
//        return "tables";
//    }


}