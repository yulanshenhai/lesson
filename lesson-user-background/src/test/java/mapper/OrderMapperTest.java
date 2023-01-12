package mapper;

import com.xiao.UserBackgroundApp;
import com.xiao.entity.Order;
import com.xiao.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserBackgroundApp.class)
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void testInsert() {
        Order order = new Order();
        order.setNumber("999999");
        order.setState(0);
        order.setTotalFee(0.0D);
        order.setCreateTime(new Date(999999999L));
        order.setLastModify(new Date());
        order.setInfo("测试订单描述信息");
        System.out.println(orderMapper.insert(order) > 0 ? "成功" : "失败");
    }

    @Test
    public void testSelectByOrderId() {
        System.out.println("videoOrder: " + orderMapper.selectById(1));
    }

    @Test
    public void testDeleteByOrderId() {
        System.out.println(orderMapper.deleteByOrderId(8) > 0 ? "成功" : "失败");
    }
}
