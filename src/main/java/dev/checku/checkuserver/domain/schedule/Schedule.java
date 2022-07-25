package dev.checku.checkuserver.domain.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Getter
@NoArgsConstructor
public class Schedule{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private LocalDateTime deadline;


//    @Builder
//    public Schedule(String title, String date, LocalDate deadline) {
//        this.title = title;
//        this.date =date;
//        this.deadline = deadline;
//    }

}
