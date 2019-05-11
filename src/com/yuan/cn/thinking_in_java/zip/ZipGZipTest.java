package com.yuan.cn.thinking_in_java.zip;

import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.*;

public class ZipGZipTest {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("D:\\新大纲大学英语四级词汇列表.txt"));
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.gz")));
        System.out.println("start writing.");
        int c;
        while ((c = reader.read()) != -1)
            out.write(c);
        reader.close();
        out.close();
        System.out.println("reading file.");
        BufferedReader reader1 = new BufferedReader(
                new InputStreamReader(
                        new GZIPInputStream(
                                new FileInputStream("test.gz"))));
        String s;
        while ((s = reader1.readLine()) != null)
            System.out.println(new String(s.getBytes(), "GBK"));
        String[] files = {"E:\\PanDownload\\data\\计算机网络系统方法  原书第4版.pdf",
                "E:\\PanDownload\\data\\计算机网络自顶向下方法.中文版.pdf",
                "E:\\PanDownload\\data\\Python3.6.5标准库文档(完整中文版).pdf"};
        ZipCompress(files);
    }
    private static void ZipCompress(String[] args) throws IOException{
        FileOutputStream f = new FileOutputStream("pdf.zip");
        // 这里有两个 CheckedSum类型：Adler32(它快一些)和CRC32(慢一些，但是更准确)
        CheckedOutputStream checkedOutputStream = new CheckedOutputStream(f, new Adler32());
        ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);
        zipOutputStream.setComment(new String("zip文件压缩示例".getBytes(), "GBK"));
        BufferedOutputStream out = new BufferedOutputStream(zipOutputStream);
        for (String arg : args) {
            System.out.println("Writing file : "+arg);
            BufferedInputStream reader = new BufferedInputStream(new FileInputStream(arg));
            zipOutputStream.putNextEntry(new ZipEntry(arg));
            int c;
            byte[] buf = new byte[1024];
            while ((c = reader.read(buf)) != -1)
                out.write(buf, 0, c);
            reader.close();
            out.flush();
        }
        out.close();
        System.out.println("CheckSum: "+checkedOutputStream.getChecksum().getValue());
        System.out.println("Reading File.");
        FileInputStream in = new FileInputStream("pdf.zip");
        CheckedInputStream checkedInputStream = new CheckedInputStream(in, new Adler32());
        ZipInputStream zipInputStream = new ZipInputStream(checkedInputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream);
        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null)
        {
            System.out.println("reading file: "+entry);
        }
        if(args.length == 1)
            System.out.println("CheckedSum: "+checkedInputStream.getChecksum().getValue());
        bufferedInputStream.close();
        ZipFile zf = new ZipFile("pdf.zip");
        Enumeration e = zf.entries();
        while (e.hasMoreElements())
        {
            ZipEntry zipEntry = (ZipEntry) e.nextElement();
            System.out.println("File : "+zipEntry);
        }
    }

    /** zip4j
     * 压缩文件
     * @throws net.lingala.zip4j.exception.ZipException
     */
    private static void zipFile() throws net.lingala.zip4j.exception.ZipException {
        // 生成的压缩文件
        net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile("D:\\test.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        // 要打包的文件夹
        File currentFile = new File("D:\\test");
        File[] fs = currentFile.listFiles();
        // 遍历test文件夹下所有的文件、文件夹
        assert fs != null;
        for (File f : fs) {
            if (f.isDirectory()) {
                zipFile.addFolder(f.getPath(), parameters);
            } else {
                zipFile.addFile(f, parameters);
            }
        }
    }

    /**
     * 解压缩
     */
    private static void unzip() {
        try {
            net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile("D:\\test.zip");
            zipFile.extractAll("D:\\test");
            // 如果解压需要密码
//            if(zipFile.isEncrypted()) {
//                zipFile.setPassword("111");
//            }
        } catch (net.lingala.zip4j.exception.ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加文件到压缩文件中
     */
    public static void addFile() {
        try {
            net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile("D:\\test.zip");
            ArrayList<File> addFiles = new ArrayList<>();
            addFiles.add(new File("D:\\addFile1.txt"));
            addFiles.add(new File("D:\\addFile2.txt"));
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            // 目标路径
            parameters.setRootFolderInZip("ks/");
            zipFile.addFiles(addFiles, parameters);
            // 可以添加单个文件
//            zipFile.addFile(new File("D:\\addFile2.txt"),parameters);
        } catch (net.lingala.zip4j.exception.ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * 流的方式添加文件
     */
    public static void addFile1(){
        InputStream is = null;
        try {
            net.lingala.zip4j.core.ZipFile zip = new net.lingala.zip4j.core.ZipFile("D:\\test.zip");
            ZipParameters para = new ZipParameters();
            para.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            para.setFileNameInZip("ks/add.txt");
            para.setSourceExternalStream(true);
            is = new ByteArrayInputStream("这是文件内容".getBytes());
            zip.addStream(is, para);
        } catch (net.lingala.zip4j.exception.ZipException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除压缩文件中的文件
     */
    public static void deleteFile() {
        try {
            net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile("D:\\test.zip");
            zipFile.removeFile("ks/add");
        } catch (net.lingala.zip4j.exception.ZipException e) {
            e.printStackTrace();
        }
    }
    /**
     * 创建带密码的压缩文件
     */
    private static void zipFile1() throws net.lingala.zip4j.exception.ZipException {
        // 生成的压缩文件
        net.lingala.zip4j.core.ZipFile zipFile = new net.lingala.zip4j.core.ZipFile("D:\\test.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles( true );
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("111");
        // 要打包的文件夹
        File currentFile = new File("D:\\test");
        File[] fs = currentFile.listFiles();
        // 遍历test文件夹下所有的文件、文件夹
        assert fs != null;
        for (File f : fs) {
            if (f.isDirectory()) {
                zipFile.addFolder(f.getPath(), parameters);
            } else {
                zipFile.addFile(f, parameters);
            }
        }
    }


}
