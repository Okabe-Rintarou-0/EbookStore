package com.catstore.utils.fileProcessor;


import java.io.*;

public class CodedFormatUtil {
    private static byte[] readByteArrayData(File file) {
        byte[] rebyte = null;
        BufferedInputStream bis;
        ByteArrayOutputStream output;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            output = new ByteArrayOutputStream();
            byte[] byt = new byte[1024 * 4];
            int len;
            try {
                while ((len = bis.read(byt)) != -1) {
                    if (len < 1024 * 4) {
                        output.write(byt, 0, len);
                    } else
                        output.write(byt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            rebyte = output.toByteArray();
            if (bis != null) {
                bis.close();
            }
            if (output != null) {
                output.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rebyte;
    }

    public static Boolean isUtf8(File file) {
        boolean isUtf8 = true;
        byte[] buffer = readByteArrayData(file);
        int end = buffer.length;
        for (int i = 0; i < end; i++) {
            byte temp = buffer[i];
            if ((temp & 0x80) == 0) {// 0xxxxxxx
                continue;
            } else if ((temp & 0xC0) == 0xC0 && (temp & 0x20) == 0) {// 110xxxxx 10xxxxxx
                if (i + 1 < end && (buffer[i + 1] & 0x80) == 0x80 && (buffer[i + 1] & 0x40) == 0) {
                    i = i + 1;
                    continue;
                }
            } else if ((temp & 0xE0) == 0xE0 && (temp & 0x10) == 0) {// 1110xxxx 10xxxxxx 10xxxxxx
                if (i + 2 < end && (buffer[i + 1] & 0x80) == 0x80 && (buffer[i + 1] & 0x40) == 0
                        && (buffer[i + 2] & 0x80) == 0x80 && (buffer[i + 2] & 0x40) == 0) {
                    i = i + 2;
                    continue;
                }
            } else if ((temp & 0xF0) == 0xF0 && (temp & 0x08) == 0) {// 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx
                if (i + 3 < end && (buffer[i + 1] & 0x80) == 0x80 && (buffer[i + 1] & 0x40) == 0
                        && (buffer[i + 2] & 0x80) == 0x80 && (buffer[i + 2] & 0x40) == 0
                        && (buffer[i + 3] & 0x80) == 0x80 && (buffer[i + 3] & 0x40) == 0) {
                    i = i + 3;
                    continue;
                }
            }
            isUtf8 = false;
            break;
        }
        return isUtf8;
    }
}
