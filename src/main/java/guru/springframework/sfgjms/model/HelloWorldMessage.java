package guru.springframework.sfgjms.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelloWorldMessage implements Serializable
{
    static final long serialVersionUID = 1348758567911118131L;  // Good practice, even though we are going to use JSON

    private UUID id;
    private String message;

}
