package com.example.dictionary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Schema(description = "Entity representing a record in a dictionary")
public class DictionaryDto {
    @Schema(description = "Unique identifier of the dictionary record", example = "123e4567-e89b-12d3-a456-556642440001")
    private UUID id;

    @Schema(description = "Code of the data record", example = "001")
    private String code;

    @Schema(description = "Value of the data record", example = "Hello")
    private String value;
}
