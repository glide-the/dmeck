/*
 * #%L
 * %%
 * Copyright (C) 2015 Trustsystems Desenvolvimento de Sistemas, LTDA.
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Trustsystems Desenvolvimento de Sistemas, LTDA. nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package com.trustsystems.elfinder.core2_0;

import com.trustsystems.elfinder.core2_0.Target;

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
    void createFile( Target target) throws IOException;

    /**
     * 创建文件夹
     * @param target
     * @throws IOException
     */
    void createFolder( Target target) throws IOException;

    /**
     * 删除文件
     * @param target
     * @throws IOException
     */
    void deleteFile( Target target) throws IOException;

    /**
     * 删除文件夹
     * @param target
     * @throws IOException
     */
    void deleteFolder( Target target) throws IOException;

    /**
     * 检测是否退出
     * @param target
     * @return
     */
    boolean exists( Target target);

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
    long getLastModified( Target target) throws IOException;

    /**
     * 获取文件类型
     * @param target
     * @return
     * @throws IOException
     */
    String getMimeType( Target target) throws IOException;


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

    void writeStream(Target target,InputStream is) throws IOException;

    //重命名
    void rename(Target origin, Target destination) throws IOException;

    //搜索给定的路径以获取给定的目标。
    List<Target> search(String target) throws IOException;
}
