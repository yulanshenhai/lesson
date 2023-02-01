import com.yulanshenhai.util.Result;
import org.junit.Test;

/**
 * @author JoeZhou
 */
public class ResultTest {

    @Test
    public void testOk() {
        System.out.println(Result.ok());
        System.out.println(Result.ok("我是数据"));
    }

    @Test
    public void testFail() {
        System.out.println(Result.fail(-1, "不存在"));
    }
}
