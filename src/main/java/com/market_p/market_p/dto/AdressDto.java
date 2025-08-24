package com.market_p.market_p.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdressDto {
    @NotBlank
    private String title;
    @NotBlank
    private String district;
    @NotBlank
    private String avunue;
    @NotBlank
    private String city;
    @NotBlank
    private String street;
    @NotBlank
    private int eno;
    @NotBlank
    private int ino;
}
