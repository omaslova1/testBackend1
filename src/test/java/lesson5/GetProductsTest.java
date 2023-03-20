package lesson5;


import com.github.javafaker.Faker;
import lesson5.api.CategoryService;
import lesson5.api.ProductService;
import lesson5.dto.GetCategoryResponse;
import lesson5.dto.GetProductsResponse;
import lesson5.dto.Product;
import lesson5.utils.RetrofitUtils;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetProductsTest {
    static ProductService productService;

    @BeforeAll
    static void beforeAll() {
        productService =
                RetrofitUtils.getRetrofit().create(ProductService.class);
    }

    @SneakyThrows
    @Test
    void getProductsPositiveTest() {
        Response<ResponseBody> response =
                productService.getProducts("Food").execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
    }
}




