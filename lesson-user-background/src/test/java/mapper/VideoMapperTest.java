package mapper;

import com.xiao.UserBackgroundApp;
import com.xiao.entity.Video;
import com.xiao.mapper.VideoMapper;
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
public class VideoMapperTest {

    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void testList() {
        videoMapper.list().forEach(System.out::println);
    }

    @Test
    public void testSelectById() {
        System.out.println(videoMapper.selectById(1));
    }

    @Test
    public void testSelectByIds() {
        System.out.println(videoMapper.selectByIds(new Integer[]{1, 2, 3, 4}));
        System.out.println(videoMapper.selectByIds(new Integer[]{}));
        System.out.println(videoMapper.selectByIds(null));
    }

    @Test
    public void testSelectDetailById() {
        Video video = videoMapper.selectDetailById(1);
        System.out.println("video: " + video);
        video.getChapters().forEach(chapter -> {
            System.out.println("\tchapter: " + chapter);
            chapter.getEpisodes().forEach(episode -> System.out.println("\t\tepisode: " + episode));
        });
    }

}