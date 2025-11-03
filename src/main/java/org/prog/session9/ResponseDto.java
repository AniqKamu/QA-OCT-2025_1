package org.prog.session9;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private List<PersonDto> results;
}
