package com.market_p.market_p.dto.Category;

public class CategoryResDto {
    private String name;
    private String description;

    public CategoryResDto() {}

    public CategoryResDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
