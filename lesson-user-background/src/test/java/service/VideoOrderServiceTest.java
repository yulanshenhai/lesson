package service;

import com.xiao.UserBackgroundApp;
import com.xiao.param.VideoOrderDeleteParam;
import com.xiao.service.VideoOrderService;
import com.xiao.vo.OrderPageVo;
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
public class VideoOrderServiceTest {

    @Autowired
    private VideoOrderService videoOrderService;

    @Test
    public void testDeleteByVideoOrderId() {
        VideoOrderDeleteParam videoOrderDeleteParam = new VideoOrderDeleteParam();
        videoOrderDeleteParam.setVideoOrderId(1);
        videoOrderDeleteParam.setOrderId(1);
        System.out.println(videoOrderService.deleteByVideoOrderId(videoOrderDeleteParam));
    }
}
