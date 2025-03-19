package nbc.sma.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    private Long id;
    private String username;
    private String password;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
