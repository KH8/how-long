package com.h8.howlong.domain;

import com.h8.howlong.converters.LocalDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StartWorkTimestamp {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime timestamp;

}
