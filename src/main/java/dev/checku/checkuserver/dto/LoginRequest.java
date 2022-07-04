package dev.checku.checkuserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @JsonProperty("Oe2Ue")
    private String Oe2Ue;

    @JsonProperty("Le093")
    private String Le093;

    @JsonProperty("AWeh_3")
    private String AWeh_3;

    @JsonProperty("Hd,poi")
    private String Hd;

    @JsonProperty("EKf8_/")
    private String Ek;

    @JsonProperty("WEh3m")
    private String WE;

    @JsonProperty("rE\fje")
    private String rE;

    @JsonProperty("JKGhe8")
    private String JK;

    @JsonProperty("_)e7me")
    private String e7;

    @JsonProperty("3kd3Nj")
    private String k3;

    @JsonProperty("@d1#SINGLE_ID")
    private String id;

    @JsonProperty("@d1#PWD")
    private String pwd;

    @JsonProperty("@d1#default.locale")
    private String locale;

    @JsonProperty("@d#")
    private String d;

    @JsonProperty("@d1#")
    private String d1;

    @JsonProperty("@d1#tp")
    private String d2;

    @Builder
    public LoginRequest(String id, String pwd) {
        this.Oe2Ue = "#9e4ki";
        this.Le093 = "e&*\biu";
        this.AWeh_3 = "W^_zie";
        this.Hd = "_qw3e4";
        this.Ek = "Ajd%md";
        this.WE = "ekmf3";
        this.rE = "JDow871";
        this.JK = "NuMoe6";
        this.e7 = "ne+3|q";
        this.k3 = "Qnd@%1";
        this.id = id;
        this.pwd = pwd;
        this.locale = "ko";
        this.d = "@d1#";
        this.d1 = "dsParam";
        this.d2 = "dm";
    }

    public static LoginRequest of(String id, String pwd) {
        return LoginRequest.builder()
                .id(id)
                .pwd(pwd)
                .build();
    }
}
