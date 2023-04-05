package edu.nuist.dao;

import cn.hutool.crypto.symmetric.AES;
import org.junit.jupiter.api.Test;

public class EncryptUtilTest {

    @Test
    public void testEncrypt() {
        AES aes = new AES("CBC", "PKCS5Padding",
                // 密钥，可以自定义
                "0123456789ABHAEQ".getBytes(),
                // iv加盐，按照实际需求添加
                "DYgjCEIMVrj2W9xN".getBytes());
        // 加密为16进制表示
        String encryptHex = aes.encryptHex("nuist@305");
        System.out.println(encryptHex);
        // 解密
        String decryptStr = aes.decryptStr(encryptHex);
        System.out.println(decryptStr);
    }

}
