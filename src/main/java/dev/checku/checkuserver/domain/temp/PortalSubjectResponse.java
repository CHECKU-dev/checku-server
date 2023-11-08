package dev.checku.checkuserver.domain.temp;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PortalSubjectResponse {

    @JsonProperty(value = "DS_SUSTTIMETABLE")
    private List<SubjectDetail> subjectDetails;

    @Getter
    @Setter
    @JsonIncludeProperties(value = {"OPEN_SHYR", "KOR_NM", "TYPL_KOR_NM", "TLSN", "REMK", "ROOM_NM", "POBT_DIV_NM", "DMND_SUST", "SBJT_ID"})
    public static class SubjectDetail {

        // 학년
        @JsonProperty(value = "OPEN_SHYR")
        private Integer grade;

        // 교수명
        @JsonProperty(value = "KOR_NM")
        private String professor;

        // 과목명
        @JsonProperty(value = "TYPL_KOR_NM")
        private String subjectName;

        // 현재인원/제한인원
        @JsonProperty(value = "TLSN")
        private String numberOfPeople;

        // 비고
        @JsonProperty(value = "REMK")
        private String remark;

        // 강의요시(강의실)
        @JsonProperty(value = "ROOM_NM")
        private String timeAndPlace;

        // 이수구분 (기교, 심교, ...)
        @JsonProperty(value = "POBT_DIV_NM")
        private String subjectType;

        // 학과
        @JsonProperty(value = "DMND_SUST")
        private String department;

        // 과목번호
        @JsonProperty(value = "SBJT_ID")
        private String subjectNumber;
    }

    public boolean isSuccess() {
        return !isFail();
    }

    public boolean isFail() {
        return CollectionUtils.isEmpty(subjectDetails);
    }
}


