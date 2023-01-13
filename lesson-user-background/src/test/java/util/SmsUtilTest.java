package util;

import com.xiao.util.SmsUtil;
import org.junit.Test;

/**
 * @author xiao
 */
public class SmsUtilTest {

    @Test
    public void testSendSms() {
        System.out.println(SmsUtil.sendSms("15146025081", "5683"));
    }
}
