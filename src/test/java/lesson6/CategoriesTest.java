package lesson6;

import lesson6.db.dao.CategoriesMapper;
import lesson6.db.model.Categories;
import lesson6.db.model.CategoriesExample;
import lombok.SneakyThrows;
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
import static org.hamcrest.Matchers.equalTo;

public class CategoriesTest {
    static SqlSession session = null;

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
    void getCategoryByIdPositiveTest() {
        Integer iD = 1;
        CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);
        CategoriesExample example = new CategoriesExample();
        List<Categories> list = categoriesMapper.selectByExample(example);

        Categories categories = list.get(iD);
        System.out.println("Тест1: Категория с id=" + iD + " - " + categories.getTitle());
        assertThat(categories.getTitle(), equalTo("Electronic"));
    }

    @AfterAll
    static void after() {
        {
            session.close();
        }
    }

}
