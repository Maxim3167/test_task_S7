package app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
@Embeddable
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
     private String firstName;
     private String lastName;
     private LocalDate birthDate;
}
