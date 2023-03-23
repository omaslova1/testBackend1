package lesson6;

import com.github.javafaker.Faker;
import lesson6.db.dao.ProductsMapper;
import lesson6.db.model.Products;
import lesson6.db.model.ProductsExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class ProductsTest {
    static SqlSession session = null;
    static Faker faker = new Faker();

    @BeforeAll
    static void beforeAll() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        SqlSessionFactory sqlSessionFactory;
        {
            try {
                inputStream = Resources.getResourceAsStream(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
        session = sqlSessionFactory.openSession();
    }
    @Test
    void createDeleteProduct() {
        Long idNewProduct;

        Products products = new Products();
        products.setTitle(faker.food().ingredient());
        products.setCategory_id(1L);
        products.setPrice((int) (Math.random() * 10000));
        ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
        productsMapper.insert(products);
        idNewProduct=products.getId();
        System.out.println("Смотрим новый продукт: " + idNewProduct +
                "\t" + products.getTitle() + "\t" + products.getPrice());
        session.commit();

        productsMapper.deleteByPrimaryKey(idNewProduct);
        session.commit();

        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(idNewProduct);
        List<Products> list = productsMapper.selectByExample(example);
        System.out.println("Смотрим удалили ли продукт с " + idNewProduct+"? Да, если ответ пустой -"+list);
        assertThat(list.isEmpty(), is(true));
    }
    @Test
    void getProductById() {
        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(1L);
        ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
        List<Products> list = productsMapper.selectByExample(example);
        Products products = list.get(0);

        assertThat(products.getTitle(),
                anyOf(containsString("New Product"), containsString("Milk")));
        System.out.println("Было в getProductById-"+products.getId()+"\t"+products.getTitle()+
                "\t"+products.getPrice()+"\t"+products.getCategory_id());
    }
    @Test
    void modifyProduct() {
        ProductsExample example = new ProductsExample();
        example.createCriteria().andIdEqualTo(1L);
        ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
        List<Products> list = productsMapper.selectByExample(example);
        Products products = list.get(0);
        products.setTitle("New Product");

        products.setPrice(123456);
        productsMapper.updateByPrimaryKey(products);
        session.commit();

        System.out.println("Стало после modifyProduct-"+products.getId()+"\t"+products.getTitle()+
                "\t"+products.getPrice()+"\t"+products.getCategory_id());
        assertThat(products.getTitle(), equalTo("New Product"));
    }

    @AfterAll
    static void after() {
        {
            session.close();
        }
    }
}
