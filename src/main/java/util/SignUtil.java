package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 请求校验工具类
 *
 * @auther ZhengTianle
 * @Date: 18-7-10
 */
public class SignUtil {

    //与接口信息中的Token要一致
    private static String token = "sdu1";

    /**
     *验证签名
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce){

        String[] array = new String[]{token,timestamp,nonce};
        //将token、timestamp、nonce三个参数按照字典序排序
        Arrays.sort(array);
        StringBuilder content = new StringBuilder();
        for(String s : array){
            content.append(s);
        }
        MessageDigest md = null;
        String tempStr = null;

        try{
            md = MessageDigest.getInstance("SHA-1");
            //将三个参数字符串拼接成一个字符串进行SHA1加密
            byte[] byteDigest = md.digest(content.toString().getBytes());
            tempStr = byteToStr(byteDigest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        content = null;
        //将SHA1加密后的子复查u嗯可与signature对比，标识该请求来源于微信
        return tempStr != null && tempStr.equals(signature.toUpperCase());

    }//checkSignature

    /**
     * 字节数组转换成16进制
     */
    private static String byteToStr(byte[] byteArray){
        String strDigest = "";
        for(byte b : byteArray){
            strDigest += byteToHexStr(b);
        }

        return strDigest;
    }

    /**
     * 将字节转换成16进制字符串
     */
    private static String byteToHexStr(byte mByte){
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;

    }

}//END
