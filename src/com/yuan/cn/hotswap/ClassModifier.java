package com.yuan.cn.hotswap;

/**
 * 修改Class文件，暂时只提供修改常量池常量的功能
 */
public class ClassModifier {
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;
    private static final int CONSTANT_Utf8_info = 1;
    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};
    private static  final int u1 = 1;
    private static  final int u2 = 1;

    private byte[] classBytes;

    public ClassModifier(byte[] bytes)
    {
        this.classBytes = bytes;
    }

    /**
     * 修改常量池中CONSTANT_Utf8_info常量的内容
     */
    public byte[] modifyUTF8Constant(String oldStr, String newStr)
    {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for(int i = 0; i < cpc; i++)
        {
            int tag = ByteUtils.bytes2Int(classBytes, offset, u1);
            if(tag == CONSTANT_Utf8_info)
            {
                int len = ByteUtils.bytes2Int(classBytes, offset + u1, u2);
                offset +=(u1+u2);
                String str = ByteUtils.bytes2String(classBytes, offset, len);
                if(str.equalsIgnoreCase(oldStr))
                {
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    byte[] strlen = ByteUtils.int2Byte(newStr.length(), u2);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset -u2, u2, strlen);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset, len, strBytes);
                    return classBytes;
                }else
                {
                    offset += len;
                }
            }else
            {
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classBytes;
    }
    public int getConstantPoolCount()
    {
        return ByteUtils.bytes2Int(classBytes, CONSTANT_POOL_COUNT_INDEX, u2);
    }

}
