package com.memory.beautifulbride.dtos.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileMainInfoDTO {
    private String memName;
    private Date memWeddingDate;
}
