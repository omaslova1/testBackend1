package lesson5.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data

public class GetProductsResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private String price;

    @JsonProperty("products")
    private List<Product> products = new ArrayList<>();

    @JsonProperty("categoryTitle")
    private String categoryTitle;

}
