package service;

import com.xiao.UserBackgroundApp;
import com.xiao.entity.VideoOrder;
import com.xiao.param.OrderDeleteParam;
import com.xiao.param.OrderInsertParam;
import com.xiao.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xiao
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserBackgroundApp.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testInsert() {
        OrderInsertParam orderInsertParam = new OrderInsertParam();
        orderInsertParam.setVideoIds(new Integer[]{2, 3});
        orderInsertParam.setUserId(1);
        orderInsertParam.setTotalFee(6500.00);
        System.out.println(orderService.insert(orderInsertParam) > 0 ? "成功" : "失败");
    }

    @Test
    public void testSelectDetailByUserId() {
        List<VideoOrder> videoOrders = orderService.selectDetailByUserId(1);
        if (videoOrders.isEmpty()) {
            System.out.println("暂无中间表数据");
        } else {
            videoOrders.forEach(videoOrder -> {
                System.out.println("videoOrder: " + videoOrder);
                System.out.println("video: " + videoOrder.getVideo());
                System.out.println("user: " + videoOrder.getUser());
                System.out.println("order: " + videoOrder.getOrder());
            });
        }
    }

    @Test
    public void testDeleteById() {
        OrderDeleteParam orderDeleteParam = new OrderDeleteParam();
        orderDeleteParam.setOrderId(1);
        System.out.println(orderService.deleteById(orderDeleteParam) > 0 ? "成功" : "失败");
    }
}
