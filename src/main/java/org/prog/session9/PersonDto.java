package org.prog.session9;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PersonDto {
    private String gender;
    private String nat;
    private NameDto name;
}
