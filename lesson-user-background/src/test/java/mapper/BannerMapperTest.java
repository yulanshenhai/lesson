package mapper;

import com.xiao.UserBackgroundApp;
import com.xiao.mapper.BannerMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserBackgroundApp.class)
public class BannerMapperTest {

    @Autowired
    private BannerMapper bannerMapper;

    @Test
    public void testList() {
        bannerMapper.list().forEach(System.out::println);
    }

}
