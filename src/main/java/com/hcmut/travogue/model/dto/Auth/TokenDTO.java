package com.hcmut.travogue.model.dto.Auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenDTO {
    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMCIsImVtYWlsIjoibmhvYW5ndGhpbmhAdG1hLmNvbS52biIsInR5cGUiOiJBQ0NFU1MiLCJpYXQiOjE2ODgxMDgyODIsImV4cCI6MTY4ODEwODMxMn0.NODnMQQTUHv7ctfn9mhAQvDnUHwbyb3-fMSvg-5R6BY")
    private String token;

    @Schema(example = "2023-06-30T06:58:32.000+00:00")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expires;
}
