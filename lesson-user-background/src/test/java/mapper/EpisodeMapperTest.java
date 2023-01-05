package mapper;

import com.xiao.UserBackgroundApp;
import com.xiao.mapper.EpisodeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserBackgroundApp.class)
public class EpisodeMapperTest {

    @Autowired
    private EpisodeMapper episodeMapper;

    @Test
    public void testSelectByChapterId() {
        episodeMapper.selectByChapterId(1).forEach(System.out::println);
    }
}