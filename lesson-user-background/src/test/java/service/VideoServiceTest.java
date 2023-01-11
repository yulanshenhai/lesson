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
        Video video = videoService.selectDetailByVideoId(1);
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

    @Test
    public void testSelectFirstByVideoId() {
        System.out.println("1号视频的第一集: " + videoService.selectFirstByVideoId(1));
        System.out.println("2号视频的第一集: " + videoService.selectFirstByVideoId(2));
        System.out.println("3号视频的第一集: " + videoService.selectFirstByVideoId(3));
    }

}
