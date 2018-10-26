
package com.trustsystems.elfinder.core2_1;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 操作文件接口
 */
public interface Volume {

    /**
     * 创建文件夹
     * @param target
     * @throws IOException
     */
    void createFile(Target target) throws IOException;

    /**
     * 创建文件夹
     * @param target
     * @throws IOException
     */
    void createFolder(Target target) throws IOException;

    /**
     * 删除文件
     * @param target
     * @throws IOException
     */
    void deleteFile(Target target) throws IOException;

    /**
     * 删除文件夹
     * @param target
     * @throws IOException
     */
    void deleteFolder(Target target) throws IOException;

    /**
     * 检测是否退出
     * @param target
     * @return
     */
    boolean exists(Target target);

    /**
     * 根据路径获取一个Volume包装接口
     * @param path
     * @return
     */
     Target fromPath(String path);

    /**
     * 最后修改时间
     * @param target
     * @return
     * @throws IOException
     */
    long getLastModified(Target target) throws IOException;

    /**
     * 获取文件类型
     * @param target
     * @return
     * @throws IOException
     */
    String getMimeType(Target target) throws IOException;


    String getAlias();

    String getName(Target target);

    Target getParent(Target target);

    String getPath(Target target) throws IOException;

    Target getRoot();

    long getSize(Target target) throws IOException;

    boolean hasChildFolder(Target target) throws IOException;

    boolean isFolder(Target target);

    boolean isRoot(Target target) throws IOException;

    Target[] listChildren(Target target) throws IOException;

    InputStream openInputStream(Target target) throws IOException;


    OutputStream openOutputStream(Target target) throws IOException;

    void writeStream(Target target, InputStream is) throws IOException;

    //重命名
    void rename(Target origin, Target destination) throws IOException;

    //搜索给定的路径以获取给定的目标。
    List<Target> search(String target) throws IOException;
}
