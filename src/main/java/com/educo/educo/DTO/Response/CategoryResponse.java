package com.educo.educo.DTO.Response;

import com.educo.educo.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class CategoryResponse {
    private String id;
    private String title;
    private String description;
    private long count;

    static CategoryResponse transformFromEntity(Category category) {
        return new CategoryResponse(category.getId(), category.getTitle(), category.getDescription(), category.getCount());
    }
}
