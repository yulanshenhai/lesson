package service;

import com.xiao.UserBackgroundApp;
import com.xiao.param.OrderDeleteParam;
import com.xiao.param.OrderInsertParam;
import com.xiao.param.OrderPageParam;
import com.xiao.service.OrderService;
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
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testInsert() {
        OrderInsertParam orderInsertParam = new OrderInsertParam();
        orderInsertParam.setVideoIds(new Integer[]{1,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31});
        orderInsertParam.setUserId(10);
        orderInsertParam.setTotalFee(6500.00);
        System.out.println(orderService.insert(orderInsertParam) > 0 ? "成功" : "失败");
    }

    @Test
    public void testPageDetailByUserId() {
        OrderPageVo orderPageVo = orderService.pageDetailByUserId(10,1,5);
        if (orderPageVo.getTotal() <= 0) {
            System.out.println("该用户暂无订单记录");
        } else {
            System.out.println("total: " + orderPageVo.getTotal());
            System.out.println("pageNum: " + orderPageVo.getPageNum());
            System.out.println("pageSize: " + orderPageVo.getPageSize());
            orderPageVo.getVideoOrders().forEach(videoOrder -> {
                System.out.println("videoOrder: " + videoOrder);
                System.out.println("video: " + videoOrder.getVideo());
                System.out.println("user: " + videoOrder.getUser());
                System.out.println("order: " + videoOrder.getOrder());
            });
        }
    }

    @Test
    public void testDeleteByOrderId() {
        OrderDeleteParam orderDeleteParam = new OrderDeleteParam();
        orderDeleteParam.setOrderId(7);
        System.out.println(orderService.deleteByOrderId(orderDeleteParam) > 0 ? "成功" : "失败");
    }
}
