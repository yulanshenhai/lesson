package util;


import com.xiao.util.BuildUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author xiao
 */
@Slf4j
public class BuildUtilTest {
    @Test
    public void testBuildUuid() {
        System.out.println(BuildUtil.buildUuid());
    }

    @Test
    public void testBuildNickName() {
        System.out.println(BuildUtil.buildNickName());
    }

    @Test
    public void testBuildVerificationCode(){
        System.out.println(BuildUtil.buildVerificationCode(4));
        System.out.println(BuildUtil.buildVerificationCode(6));
        System.out.println(BuildUtil.buildVerificationCode(8));
    }
}
