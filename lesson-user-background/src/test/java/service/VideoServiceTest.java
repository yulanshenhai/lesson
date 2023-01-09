package service;

import com.xiao.UserBackgroundApp;
import com.xiao.entity.Video;
import com.xiao.param.VideoSearchParam;
import com.xiao.service.VideoService;
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
public class VideoServiceTest {

    @Autowired
    private VideoService videoService;

    @Test
    public void testSelectDetailById() {
        Video video = videoService.selectDetailById(1);
        System.out.println("video: " + video);
        video.getChapters().forEach(chapter -> {
            System.out.println("\tchapter: " + chapter);
            chapter.getEpisodes().forEach(episode -> System.out.println("\t\tepisode: " + episode));
        });
    }

    @Test
    public void testSearchByTitle() {
        VideoSearchParam videoSearchParam = new VideoSearchParam();
        videoSearchParam.setTitle("JB");
        videoSearchParam.setPage(2);
        videoSearchParam.setSize(2);
        System.out.println(videoService.searchByTitle(videoSearchParam));
    }

}
