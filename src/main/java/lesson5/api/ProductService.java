package lesson5.api;


import lesson5.dto.Product;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.concurrent.Executor;

public interface ProductService {

    @POST("products")
    Call<Product> createProduct(@Body Product createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<Product> modifyProduct(@Body Product ModifyProductRequest);

    @GET("products/{id}")
    Call<Product> getProductById(@Path("id") Product id);

    @GET("products")
    Call<ResponseBody> getProducts(String food);

    Executor getProduct();
}
