package mapper;

import com.xiao.UserBackgroundApp;
import com.xiao.entity.VideoOrder;
import com.xiao.mapper.VideoOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author xiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserBackgroundApp.class)
public class VideoOrderMapperTest {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Test
    public void testInsert() {
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setVideoId(1);
        videoOrder.setOrderId(1);
        videoOrder.setUserId(1);
        videoOrder.setCreateTime(new Date(999999999L));
        videoOrder.setLastModify(new Date());
        videoOrder.setInfo("测试描述");
        System.out.println(videoOrderMapper.insert(videoOrder) > 0 ? "成功" : "失败");
    }

    @Test
    public void testDeleteByVideoOrderId() {
        System.out.println(videoOrderMapper.deleteByVideoOrderId(65) > 0 ? "成功" : "失败");
    }

    @Test
    public void testDeleteByUserId() {
        System.out.println(videoOrderMapper.deleteByUserId(999) > 0 ? "成功" : "失败");
    }

    @Test
    public void testDeleteByOrderId() {
        System.out.println(videoOrderMapper.deleteByOrderId(999) > 0 ? "成功" : "失败");
    }

    @Test
    public void testSelectByUserId() {
        videoOrderMapper.selectByUserId(1).forEach(System.out::println);
    }

    @Test
    public void testSelectById() {
        videoOrderMapper.selectByOrderId(19).forEach(System.out::println);
    }

    @Test
    public void testSelectDetailByOrderId() {
        VideoOrder videoOrder = videoOrderMapper.selectDetailByVideoOrderId(11);
        System.out.println("videoOrder: " + videoOrder);
        System.out.println("user: " + videoOrder.getUser());
        System.out.println("video: " + videoOrder.getVideo());
        System.out.println("order: " + videoOrder.getOrder());
    }

    @Test
    public void testSelectDetailByUserId() {
        videoOrderMapper.selectDetailByUserId(10).forEach(System.out::println);
    }

    @Test
    public void testSelectByUserIdAndVideoIds() {
        System.out.println(videoOrderMapper.selectByUserIdAndVideoIds(1, new Integer[]{2, 5, 6}));
    }
}
