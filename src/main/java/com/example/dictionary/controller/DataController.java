package com.example.dictionary.controller;

import com.example.dictionary.dto.CreateDataDto;
import com.example.dictionary.dto.DataDto;
import com.example.dictionary.model.Data;
import com.example.dictionary.model.Dictionary;
import com.example.dictionary.service.DataService;
import com.example.dictionary.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private DictionaryService dictionaryService;

    @PostMapping("/records")
    @Operation(summary = "Create a new record in a dictionary",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Record created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DataDto.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Dictionary not found",
                            content = @Content(schema = @Schema())
                    )
            })
    public DataDto createDataRecord(@RequestBody CreateDataDto dataDto) {
        Dictionary dictionary = dictionaryService.getDictionaryById(dataDto.getDictionary());

        if (dictionary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dictionary record not found with id: %s", dataDto.getDictionary()));
        }

        Data data = Data.builder().code(dataDto.getCode()).value(dataDto.getValue()).dictionary(dictionary).build();
        Data createdData = dataService.createData(data);
        return new DataDto(
            createdData.getId(),
            createdData.getDictionary().getId(),
            createdData.getCode(),
            createdData.getValue()
        );
    }

    @GetMapping("/records")
    @Operation(summary = "Get all data records",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of data records",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema( schema = @Schema(implementation = DataDto.class))
                            )
                    )
            })
    public List<DataDto> getAllDataRecords() {
        List<Data> dataRecords = dataService.getAllData();
        List<DataDto> dataDtos = new ArrayList<>();

        for (Data data : dataRecords) {
            dataDtos.add(
                new DataDto(
                    data.getId(),
                    data.getDictionary().getId(),
                    data.getCode(),
                    data.getValue()
                )
            );
        }

        return dataDtos;
    }

    @GetMapping("/dictionaries/{id}/records")
    @Operation(summary = "Get all data records by dictionary id",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of data records",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema( schema = @Schema(implementation = DataDto.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Dictionary not found",
                            content = @Content(schema = @Schema())
                    )
            })
    public List<DataDto> getAllDataRecordsByDictionaryId(@PathVariable UUID id) {
        Dictionary dictionary = dictionaryService.getDictionaryById(id);

        if (dictionary == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Dictionary record not found with id: %s", id));
        }

        List<Data> dataRecords = dataService.getAllDataByDictionaryId(id);
        List<DataDto> dataDtos = new ArrayList<>();

        for (Data data : dataRecords) {
            dataDtos.add(
                    new DataDto(
                            data.getId(),
                            data.getDictionary().getId(),
                            data.getCode(),
                            data.getValue()
                    )
            );
        }

        return dataDtos;
    }

    @GetMapping("/records/{id}")
    @Operation(summary = "Get data record by ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Data record found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DataDto.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Data not found",
                            content = @Content(schema = @Schema())
                    )
            })
    public DataDto getDataRecordById(@PathVariable UUID id) {
        Data data = dataService.getDataById(id);

        if (data == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Data record not found with id: %s", id));
        }

        return new DataDto(data.getId(), data.getDictionary().getId(), data.getCode(), data.getValue());
    }

    @DeleteMapping("/records/{id}")
    @Operation(summary = "Delete data record by ID",
            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Data record deleted successfully"),
                    @ApiResponse(responseCode = "404",
                            description = "Data record not found")
            })
    public void deleteDataRecord(@PathVariable UUID id) {
        Data data = dataService.getDataById(id);

        if (data == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Data record not found with id: %s", id));
        }

        dataService.deleteData(id);
    }
}