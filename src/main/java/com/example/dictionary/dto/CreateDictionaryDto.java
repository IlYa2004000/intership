package com.example.dictionary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO for creating a new dictionary record")
public class CreateDictionaryDto {
    @Schema(description = "Code of the dictionary", example = "ENG", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "Description of the dictionary", example = "English Dictionary", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;
}
