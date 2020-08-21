package com.imooc.oa.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source){
        return DigestUtils.md5Hex(source);
    }

    public static String md5Digest(String source, Integer salt){
        char[] ch = source.toCharArray();
        for(int i=0; i < ch.length; i++){
            ch[i] = (char) (ch[i] + salt);
        }
        String res = new String(ch);
        return DigestUtils.md5Hex(res);
    }
}
