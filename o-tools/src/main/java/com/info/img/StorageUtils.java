package com.info.img;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname StorageUtils
 * @Description 上传公用类
 * @Date 2019/3/21 17:04
 * @Created by liudan
 */
@Component
public class StorageUtils {
    /**
     * 接口方法
     */
    public static final String METHOD_GETTOKEN = "restful/storageapi/storage/getToken"; // 获取操作密钥token
    public static final String METHOD_UPLOAD = "restful/storageapi/file/uploadFile"; // 上传文件
    public static final String METHOD_GETURL = "restful/storageapi/file/getFileUrl"; // 获取文件访问链接
    public static final String METHOD_SUB_FOLDERANDFILES = "restful/storageapi/folder/getSubFoldersAndFiles";//获取当前目录下子目录及其文件
    public static final String METHOD_DELFILE = "restful/storageapi/file/deleteFile"; // 删除文件
    public static final String METHOD_DELETE_FOLDER = "restful/storageapi/folder/deleteFolder";// 删除文件夹
    public static final String METHOD_RENAME_FOLDER = "restful/storageapi/folder/renameFolder";// 重命名文件夹
    public static final String METHOD_RENAME_FILE = "restful/storageapi/file/renameFile";// 重命名文件
    public static final String METHOD_COPYFILE = "restful/storageapi/file/copyFile";//复制文件
    public static final String METHOD_COPYFOLDER = "restful/storageapi/folder/copyFolder";//复制文件夹
    // 用户秘钥对：开通快云存储时的Access_Key，可在会员中心获取
    @Value("${kuaiyun.ACCESS_KEY}")
    private String ACCESS_KEY;
    // 用户秘钥对：开通快云存储时的Secret_Key，可在会员中心获取
    @Value("${kuaiyun.SECRET_KEY}")
    private String SECRET_KEY;
    // API调用来源，可在会员中心点击获取
    @Value("${kuaiyun.RESOURCE}")
    private String RESOURCE;
    // 凭证，用户通过accesskey和secretkey获取的，可在会员中心点击，以邮件形式获取
    @Value("${kuaiyun.VOUCHER}")
    private String VOUCHER;
    // 接口API地址  dev外网 pro内网
    @Value("${storage.url}")
    private String apiUrl;
    // 用户的空间名称
    @Value("${storage.bucket_name}")
    private String BUCKET_NAME;

    /**
     * HTTP请求块（POST）
     *
     * @param url        请求链接
     * @param JsonString 请求体中的参数
     * @param input      请求文件流
     * @param hander     请求头里的参数
     * @return
     */
    private static String sendPost(String url, String JsonString, InputStream input, Map<String, String> hander) {
        String result = "";
        // 创建连接
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性	
            conn.setRequestProperty("Content-Type", " application/json");// 设定请求格式,json，也可以设定xml格式的
            conn.setRequestProperty("Accept-Charset", "UTF-8"); // 设置编码语言
            conn.setRequestProperty("X-Auth-Token", "token"); // 设置请求的token
            conn.setRequestProperty("Connection", "keep-alive"); // 设置连接的状态
            conn.setRequestProperty("Transfer-Encoding", "chunked");// 设置传输编码
            // post请求，无缓存
            conn.setRequestMethod("POST"); // 可以根据需要提交
            conn.setUseCaches(false);// 设置缓存，注意设置请求方法为post不能用缓存
            conn.setChunkedStreamingMode(0);// 设置该选项，则不使用HttpURLConnection的缓存机制，直接将流提交到服务器上
            conn.setInstanceFollowRedirects(true);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 请求参数在请求头中
            if (hander != null) {
                for (Map.Entry<String, String> entry : hander.entrySet()) {
                    conn.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 建立实际的连接
            conn.connect();

            int code = 0;
            //
            if (JsonString != null) {
                OutputStream out = conn.getOutputStream();
                out.write(JsonString.getBytes());
                code = conn.getResponseCode();
                out.flush();
                out.close();
            }

            if (input != null) {
                OutputStream out = conn.getOutputStream();
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = input.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                code = conn.getResponseCode();
                input.close();
                out.flush();
                out.close();
            }

            // 读取响应
            if (code == 200) {
                BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuffer stb = new StringBuffer();
                String line = "";
                while ((line = read.readLine()) != null) {
                    stb.append(line);
                }
                read.close();
                String message = stb.toString();
                if (message != null && !"".equals(message)) {
                    JSONObject returnObject = JSONObject.fromObject(message);
                    result = returnObject.toString();
                } else {
                }
            } else {
                result = conn.getResponseMessage();
            }

            // 断开连接
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            result = "调用post方法异常，异常原因：" + e.getMessage();
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", "9908b50ebcd9b40436cb177b8e34713d");
            jsStr.put("resource", "VG3OQ81wtrNn7lCSdBAwe+gg9rORx+3K");
            jsStr.put("bucketName", "devimg");
            jsStr.put("fileName", "201903221456581554171573.jpg");
            sendPost("http://api.storagesdk.com/" + METHOD_DELFILE, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getToken() {
        JSONObject jsStr = new JSONObject();
        jsStr.put("voucher", VOUCHER);
        jsStr.put("accessKey", ACCESS_KEY);
        jsStr.put("secretKey", SECRET_KEY);
        jsStr.put("resource", RESOURCE);
        String msg = sendPost(apiUrl + METHOD_GETTOKEN, jsStr.toString(), null, null);
        System.out.println(msg);
        String token = JSONObject.fromObject(msg).get("message").toString().split(":")[1];
        return token;
    }

    /**
     * 上传文件
     * localFile 本地图片路径
     *
     * @return
     */
    public String uploadFile(String token, String fileName) {
        try {
            File file = new File(DictionaryTemplate.getFileParentPath() + fileName);
            InputStream in = null;
            try {
                in = new FileInputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BASE64Encoder encoder = new BASE64Encoder();
            fileName = encoder.encode(fileName.getBytes("utf-8"));
            Map<String, String> hander = new HashMap<String, String>();
            hander.put("token", token);
            hander.put("resource", RESOURCE);
            hander.put("bucketName", BUCKET_NAME);
            hander.put("fileName", fileName);
            hander.put("length", in.available() + "");
            return sendPost(apiUrl + METHOD_UPLOAD, null, in, hander);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件访问链接
     *
     * @return
     */
    public String getFileUrl(String token, String keyName/*,String minutes,Integer leng*/) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", token);
            jsStr.put("resource", RESOURCE);
            jsStr.put("bucketName", BUCKET_NAME);
            jsStr.put("fileName", keyName); // 拼接文件路径
            /*jsStr.put("minutes", minutes);// 可以不传，默认是无时效。
            jsStr.put("leng", leng);// 可以不传，默认是短链接。*/
            return sendPost(apiUrl + METHOD_GETURL, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @return
     */
    public String delFile(String token, String delfileName) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", token);
            jsStr.put("resource", RESOURCE);
            jsStr.put("bucketName", BUCKET_NAME);
            jsStr.put("fileName", delfileName);
            return sendPost(apiUrl + METHOD_DELFILE, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重命名文件夹
     *
     * @return
     */
    public String renamefolder(String token, String oldFolderName, String newFolderName) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", token);
            jsStr.put("bucketName", BUCKET_NAME);
            jsStr.put("resource", RESOURCE);
            jsStr.put("oldFolderName", oldFolderName);
            jsStr.put("newFolderName", newFolderName);
            return sendPost(apiUrl + METHOD_RENAME_FOLDER, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 重命名文件
     *
     * @return
     */
    public String renamefile(String token, String oldFileName, String newFileName) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", token);
            jsStr.put("bucketName", BUCKET_NAME);
            jsStr.put("resource", RESOURCE);
            jsStr.put("oldFileName", oldFileName);
            jsStr.put("newFileName", newFileName);
            return sendPost(apiUrl + METHOD_RENAME_FILE, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件夹
     *
     * @return
     */
    public String deletefolder(String token, String folderName) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", token);
            jsStr.put("bucketName", BUCKET_NAME);
            jsStr.put("resource", RESOURCE);
            jsStr.put("folderName", folderName);
            return sendPost(apiUrl + METHOD_DELETE_FOLDER, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复制文件夹
     *
     * @return
     */
    public String copyFolder(String token, String oldBucketName, String oldFolderName, String newBucketName, String newFolderName) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", token);
            jsStr.put("resource", RESOURCE);
            jsStr.put("oldBucketName", oldBucketName);
            jsStr.put("oldFolderName", oldFolderName);
            jsStr.put("newBucketName", newBucketName);
            jsStr.put("newFolderName", newFolderName);
            return sendPost(apiUrl + METHOD_COPYFOLDER, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 复制文件
     *
     * @return
     */
    public String copyFile(String token, String oldBucketName, String oldFileName, String newBucketName, String newFileName) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("token", token);
            jsStr.put("resource", RESOURCE);
            jsStr.put("oldBucketName", oldBucketName);
            jsStr.put("oldFileName", oldFileName);
            jsStr.put("newBucketName", newBucketName);
            jsStr.put("newFileName", newFileName);
            return sendPost(apiUrl + METHOD_COPYFILE, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * isGetFile 是否获取文件[true.是,false.否]
     * 获取当前目录下子目录及文件
     *
     * @return
     */
    public String getSubFoldersAndFiles(String token, String folderName, boolean isGetFile) {
        try {
            JSONObject jsStr = new JSONObject();
            jsStr.put("resource", RESOURCE);
            jsStr.put("token", token);
            jsStr.put("bucketName", BUCKET_NAME);
            jsStr.put("folderName", folderName);
            jsStr.put("isGetFile", isGetFile);
            return sendPost(apiUrl + METHOD_SUB_FOLDERANDFILES, jsStr.toString(), null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
