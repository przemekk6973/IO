package pl.edu.agh.io.dzikizafrykibackend.db.jsonb;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResults implements Serializable {

    Map<UUID, List<UUID>> dateToStudents;
}
