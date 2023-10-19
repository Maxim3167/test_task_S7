package app.entity;

import javax.persistence.Embeddable;
import java.time.LocalDate;
@Embeddable
public class PersonalInfo {
        String firstName;
        String lastName;
        LocalDate birthDate;
}
