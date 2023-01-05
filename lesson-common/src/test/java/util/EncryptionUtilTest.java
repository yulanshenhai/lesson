package util;

import com.xiao.util.EncryptionUtil;
import org.junit.Test;

/**
 * @author xiao
 */
public class EncryptionUtilTest {

    @Test
    public void testEncryptPhone() {
        System.out.println(EncryptionUtil.encryptPhone("18210210122"));
    }

    @Test
    public void testEncryptCard() {
        System.out.println(EncryptionUtil.encryptIdCard("230107199999990210"));
    }

    @Test
    public void testEncryptPassword() {
        System.out.println(EncryptionUtil.encryptPassword("admin"));
    }
}
