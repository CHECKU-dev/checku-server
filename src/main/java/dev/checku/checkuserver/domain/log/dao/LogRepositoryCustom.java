package dev.checku.checkuserver.domain.log.dao;

import dev.checku.checkuserver.domain.log.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LogRepositoryCustom {

    Page<Log> findLog(Pageable pageable);

}
