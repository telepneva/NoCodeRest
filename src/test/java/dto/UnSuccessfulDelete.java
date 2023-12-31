package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UnSuccessfulDelete {
    private String code;
    private String message;
    private String instance;
    private String status;
    private String title;
    private String type;
    private String source;
}
