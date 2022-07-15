package dev.checku.checkuserver.checku.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestPart;

@Getter @Setter
public class LoginRequest {

    @JsonProperty("Oe2Ue")
    private String Oe2Ue;

    @JsonProperty("Le093")
    private String Le;

    @JsonProperty("AWeh_3")
    private String AW;

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
    public LoginRequest(String oe2Ue, String le, String AW, String hd, String ek, String WE,
                        String rE, String JK, String e7, String k3, String id, String pwd,
                        String locale, String d, String d1, String d2) {
        this.Oe2Ue = oe2Ue;
        this.Le = le;
        this.AW = AW;
        this.Hd = hd;
        this.Ek = ek;
        this.WE = WE;
        this.rE = rE;
        this.JK = JK;
        this.e7 = e7;
        this.k3 = k3;
        this.id = id;
        this.pwd = pwd;
        this.locale = locale;
        this.d = d;
        this.d1 = d1;
        this.d2 = d2;
    }

    public static LoginRequest of(String id, String pwd) {

        return LoginRequest.builder()
                .oe2Ue("#9e4ki").le("e&*\biu").AW("W^_zie").hd("_qw3e4").ek("Ajd%md")
                .WE("ekmf3").rE("JDow871").JK("NuMoe6").e7("ne+3|q").k3("Qnd@%1").id(id).pwd(pwd).locale("ko").d("@d1#").d1("dsParam").d2("dm")
                .build();

    }
}
