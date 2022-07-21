package dev.checku.checkuserver.domain.subject;

import dev.checku.checkuserver.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {


    List<Subject> findAllByByUser(User user);
}
