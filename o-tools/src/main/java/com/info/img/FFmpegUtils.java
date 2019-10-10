package com.info.img;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 功能描述: FFMpeg工具类
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/3/28 16:14
 */
public class FFmpegUtils {

    public static final FFmpegUtils INSTANCE = new FFmpegUtils();
    private static String ffmpegpath;

    private static final int MILLIS = 500;

    private Date dt;
    private long begintime;

    public FFmpegUtils() {

    }

    /**
     * 转换视频格式
     *
     * @return
     */
    public Map<String, File> beginConver(File source) throws Exception {
        System.out.println("----接收到文件需要转换-------------------------- ");
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }
        dt = new Date();
        begintime = dt.getTime();
        System.out.println("----开始转文件-------------------------- ");

        // 压缩转码后视频文件
        File video = process(source);

        Date dt2 = new Date();
        System.out.println("转换成功 ");
        long endtime = dt2.getTime();
        long timecha = (endtime - begintime);
        // String totaltime = sumTime(timecha);
        System.out.println("转换视频格式共用了:" + timecha + " ");

        //视频缩略图
        File img = processImg(source);

        Map<String, File> map = new HashMap<>(0);
        map.put("video", video);
        map.put("img", img);
        source = null;
        return map;

    }

    /**
     * 对视频进行截图
     *
     * @param folderPath
     * @param source
     * @return
     * @throws Exception
     */
    public File processImg(final String folderPath, final File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        if (StringUtils.isEmpty(folderPath)) {
            throw new Exception("视频封面文件目录不能为空");
        }

        String path = folderPath;

        if (!folderPath.endsWith(File.separator)) {
            path = folderPath.concat(File.separator);
        }

        checkTempFolder(path);

        File desc = new File(path + UUID.randomUUID().toString() + ".jpg");
        desc = new File(desc.getAbsolutePath());

        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-ss");
        commend.add("00:00:03");
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        commend.add("-f");
        commend.add("image2");
        commend.add("-y");
        commend.add(desc.getAbsolutePath());
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 对视频进行截图
     *
     * @return
     * @see #processImg(String, File)
     * @deprecated
     */
    public File processImg(File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        File desc = new File(UUID.randomUUID().toString() + ".jpg");
        desc = new File(desc.getAbsolutePath());

        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-ss");
        commend.add("00:00:03");
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        commend.add("-f");
        commend.add("image2");
        commend.add("-y");
        commend.add(desc.getAbsolutePath());
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 剥离视频中的音乐
     *
     * @param source     视频文件
     * @param folderPath 音乐文件存储文件夹路径
     * @return
     * @throws Exception
     */
    public File processMusic(final String folderPath, final File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        if (StringUtils.isEmpty(folderPath)) {
            throw new Exception("音乐文件目录不能为空");
        }

        String path = folderPath;

        if (!folderPath.endsWith(File.separator)) {
            path = folderPath.concat(File.separator);
        }

        checkTempFolder(path);

        File desc = new File(path + UUID.randomUUID().toString() + ".mp3");
        desc = new File(desc.getAbsolutePath());

        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-y");
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        commend.add("-vn");
        commend.add(desc.getAbsolutePath());
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void checkTempFolder(String path) {
        final File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     * 剥离视频中的音乐
     *
     * @param source 视频文件
     * @return
     * @see #processMusic(String, File)
     * @deprecated
     */
    public File processMusic(File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        File desc = new File(UUID.randomUUID().toString() + ".mp3");
        desc = new File(desc.getAbsolutePath());

        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegpath);
        commend.add("-y");
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        commend.add("-vn");
        commend.add(desc.getAbsolutePath());
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 实际转换视频格式的方法
     *
     * @return
     */
    public File process(File source) throws Exception {
        int type = checkContentType(source);
        File status = null;
        if (type == 0) {
            // 如果type为0用ffmpeg直接转换
            status = processVideoFormat(source);
        } else if (type == 1) {
            throw new Exception("视频格式不支持转换");
        }
        return status;
    }

    static {
        try {
            if ("\\".equalsIgnoreCase(File.separator)) {
                ffmpegpath = new ClassPathResource("ffmpeg.exe").getFile().getAbsolutePath();
            } else {
                ffmpegpath = new ClassPathResource("ffmpeg").getFile().getAbsolutePath();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(new String[]{"/bin/chmod", "755", ffmpegpath});
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ffmpeg 加载失败");
        }

    }

    /**
     * 检查文件是否存在
     *
     * @param file
     * @return
     */
    private boolean checkfile(File file) {
        if (!file.isFile()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 转换为指定格式 ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
     *
     * @return
     */
    private File processVideoFormat(File source) throws Exception {
        if (!checkfile(source)) {
            throw new Exception("文件不存在");
        }

        File desc = new File(UUID.randomUUID().toString() + ".mp4");
        desc = new File(desc.getAbsolutePath());
        // ffmpeg -i FILE_NAME.flv -ar 22050 NEW_FILE_NAME.mp4
        List<String> commend = new ArrayList<>();
        commend.add(ffmpegpath);
        commend.add("-i");
        commend.add(source.getAbsolutePath());
        // 增加以下两行代码, 使得视频支持在浏览器播放
        commend.add("-vcodec");
        commend.add("h264");

        /*commend.add("-vcodec");
        commend.add("h264");
        commend.add("-b");
        commend.add("600000");
        commend.add("-r");
        commend.add("15");
        commend.add("-acodec");
        commend.add("aac");
        commend.add("-ab");
        commend.add("128000");
        commend.add("-ac");
        commend.add("2");
        commend.add("-ar");
        commend.add("44100");
        commend.add("-f");
        commend.add("mp4");
        commend.add("-y");*/
        commend.add(desc.getAbsolutePath());
        System.err.println(commend);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            doWaitFor(p);
            p.destroy();
            return desc;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 检查文件类型
     *
     * @return
     */
    private int checkContentType(File source) {
        String path = source.getAbsolutePath();
        String type = path.substring(path.lastIndexOf(".") + 1, path.length())
                .toLowerCase();
        // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
        if ("avi".equals(type)) {
            return 0;
        } else if ("mpg".equals(type)) {
            return 0;
        } else if ("wmv".equals(type)) {
            return 0;
        } else if ("3gp".equals(type)) {
            return 0;
        } else if ("mov".equals(type)) {
            return 0;
        } else if ("mp4".equals(type)) {
            return 0;
        } else if ("asf".equals(type)) {
            return 0;
        } else if ("asx".equals(type)) {
            return 0;
        } else if ("flv".equals(type)) {
            return 0;
        }
        // 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
        // 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
        else if ("wmv9".equals(type)) {
            return 1;
        } else if ("rm".equals(type)) {
            return 1;
        } else if ("rmvb".equals(type)) {
            return 1;
        }
        return 9;
    }

    public int doWaitFor(Process p) {
        InputStream in = null;
        InputStream err = null;
        // returned to caller when p is finished
        int exitValue = -1;
        try {
            System.out.println("comeing");
            in = p.getInputStream();
            err = p.getErrorStream();
            // Set to true when p is finished
            boolean finished = false;

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        Character c = new Character((char) in.read());
                        System.out.print(c);
                    }
                    while (err.available() > 0) {
                        Character c = new Character((char) err.read());
                        System.out.print(c);
                    }

                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.sleep(MILLIS);
                }
            }
        } catch (Exception e) {
            System.err.println("doWaitFor();: unexpected exception - " + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return exitValue;
    }

}
