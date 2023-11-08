package dev.checku.checkuserver.domain.portal.application.service;

import dev.checku.checkuserver.domain.common.domain.SubjectNumber;
import dev.checku.checkuserver.domain.subject.domain.Department;
import dev.checku.checkuserver.domain.subject.domain.SubjectCategory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@RequiredArgsConstructor
public class PortalRequestBodyFactory {

    public MultiValueMap<String, String> create(String year, String semester, SubjectCategory subjectCategory, Department department, SubjectNumber subjectNumber) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("Oe2Ue", "#9e4ki");
        body.add("Le093", "e&*\biu");
        body.add("AWeh_3", "W^_zie");
        body.add("Hd,poi", "_qw3e4");
        body.add("EKf8_/", "Ajd%md");
        body.add("WEh3m", "ekmf3");
        body.add("rE\fje", "JDow871");
        body.add("JKGhe8", "NuMoe6");
        body.add("_)e7me", "ne+3|q");
        body.add("3kd3Nj", "Qnd@%1");
        body.add("_AUTH_MENU_KEY", "1130420");
        body.add("@d1#argDeptFg", "1"); // 수강, 제청, 개설
        body.add("@d#", "@d1#");
        body.add("@d1#", "dsParam");
        body.add("@d1#tp", "dm");
        body.add("@d1#ltYy", year);
        body.add("@d1#ltShtm", semester); // 학기 구분
        body.add("@d1#pobtDiv", subjectCategory == null ? Strings.EMPTY : subjectCategory.getValue()); // 이수 구분
        body.add("@d1#openSust", department == null ? Strings.EMPTY : department.getValue()); // 학과 고유 번호
        body.add("@d1#sbjtId", subjectNumber == null ? Strings.EMPTY : subjectNumber.getValue()); // 과목 번호

        return body;
    }
}
