package com.iqilu.utils.file;

import java.io.File;
import java.io.IOException;

/**
 * 文件处理工具类
 *
 * @author zhangyicheng
 * @date 2021/04/18
 */
public class FileUtils {

    /**
     * 创建文件
     *
     * @param filePath 文件路径 + 文件名称
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean createFile(String filePath) {
        File f = new File(filePath);
        try {
            return f.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 文件/目录重命名
     *
     * @param oldPath 老名称
     * @param newPath 新名称
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean renameFileOrDir(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        return oldFile.renameTo(new File(newPath));
    }

    /**
     * 单个文件的删除
     *
     * @param filePath 文件路径
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean deleteFile(String filePath) {
        File f = new File(filePath);
        if (f.isFile() && f.exists()) {
            return f.delete();
        }
        return false;
    }

    /**
     * 创建一级目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean createDir(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            return dir.mkdir();
        }
        return false;
    }

    /**
     * 创建多级目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean createDirs(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists() || dir.isDirectory()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 删除目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static boolean deleteEmptyDir(String dirName) {
        File dir = new File(dirName);
        if (dir.isDirectory()) {
            return dir.delete();
        }
        return false;
    }

    /**
     * 递归删除目录
     *
     * @param dirName 目录名
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static void deleteDirs(String dirName) {
        File dir = new File(dirName);
        File[] dirs = dir.listFiles();
        for (int i = 0; dirs != null && i < dirs.length; i++) {
            File f = dirs[i];
            // 如果是文件直接删除
            if (f.isFile()) {
                f.delete();
            }
            // 如果是目录继续遍历删除
            if (f.isDirectory()) {
                deleteDirs(f.getAbsolutePath());
            }
        }
        // 删除本身
        dir.delete();
    }

    /**
     * 获取项目绝对路径路径
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public String projectPath() {
        File f = new File(this.getClass().getResource("/").getPath());
        return String.valueOf(f);
    }

    /**
     * 获取当前类的所在工程路径
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public String allPath() {
        File f2 = new File(this.getClass().getResource("").getPath());
        return String.valueOf(f2);
    }

}
