package com.whh.javaio.jobex;

import com.whh.javaio.classTop.PathUtils;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 筛选出同一时间文件作为model，组成model集合
 */
public class ScreenFiles {

    public static void main(String[] args) {
//        System.out.println("5".compareTo("2")); //大于0则前者大 结果：3
//        String n1 = "9A42C8A4800FA8DA5FD1358499227294-A8DA5FD1C6C128F0-20130118165347-F.jpg";
//        String n2 = "17D4F7FFE3D6A8DA5FD1358499225237-A8DA5FD1C6C128F0-20130118165345-F.jpg";
//        String time1 = n1.split("-")[2];
//        String time2 = n2.split("-")[2];
//        System.out.println("time1=" + time1);
//        System.out.println("time2=" + time2);
//        System.out.println(time1.compareTo(time2)); //大于0则前者大 结果：2

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = "20130118165345";
        try {
            Date date = format.parse(time);
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("20130118165345==>" + format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        List<CaptureImgModel> captureImgs = getCaptureImgs();
//        if (captureImgs.size() > 0) {
////            for (int i = 0; i < captureImgs.size(); i++) {
////                System.out.println("model:" + String.format("%02d", i)
////                        + ", captureImgs:" + captureImgs.toString());
////            }
//            System.out.println("");
//            CaptureImgModel captureImgModel = captureImgs.get(5);
//            System.out.println("model-time:" + captureImgModel.time);
//            System.out.println("model-facePicPath:" + captureImgModel.facePicPath);
//            System.out.println("model-leftEyePicPath:" + captureImgModel.leftEyePicPath);
//            System.out.println("model-rightEyePicPath:" + captureImgModel.rightEyePicPath);
//        }

        compareStr();
    }

    /**
     * 获取最近采集的30份图像
     */
    private static List<CaptureImgModel> getCaptureImgs() {
        List<CaptureImgModel> list = new ArrayList<>();

        File image3500 = new File(PathUtils.ai3500imgPath);
        if (image3500.exists() && image3500.listFiles().length > 0) {
            File[] files = image3500.listFiles();
            int length = files.length;
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File lhs, File rhs) {
                    String lhst = lhs.getName().split("-")[2];
                    String rhst = rhs.getName().split("-")[2];
                    //JDK1.7 在sort排序中重写的方法一定要满足:可逆比较
                    if (lhst.compareTo(rhst) < 0) {
                        return 1;
                    }
                    if (lhst.compareTo(rhst) > 0) {
                        return -1;
                    }
                    return 0;
                }
            });


            List<File> fileList = new ArrayList<>(); //保存有效时间内的文件
            List<String> times = new ArrayList<>(); //保存有效时间的时间集合
            for (int i = 0; i < length; i++) { //筛选出最新的30个时间
                //EC90774E58C4A8DA5FD1358499221254-A8DA5FD1C6C128F0-20130118165341-L
                String fileName = files[i].getName();
                String fileTime = fileName.split("-")[2];
//                System.out.println("排序后文件时间i:" + String.format("%02d", i) + ", fileTime:" + fileTime);

                if (times.size() < 30) {
                    if (!times.contains(fileTime)) {
                        times.add(fileTime);
                    }
                    fileList.add(files[i]);
                }
            }

            //通过筛选后的时间再来获取同一时间采集的文件

            for (int i = 0; i < times.size(); i++) {
                String itemTime = times.get(i);
                System.out.println(String.format("itemTime%02d:", i) + itemTime);
                CaptureImgModel model = new CaptureImgModel(itemTime);

                int count = 0;
                for (int j = 0; j < fileList.size(); j++) {
                    String fileName = fileList.get(j).getName();
                    String fileTime = fileName.split("-")[2];
                    String filePath = PathUtils.ai3500imgPath + "/" + fileName;
                    if (itemTime.equals(fileTime)) {
                        count++;
                        System.out.println("itemTime:" + itemTime + "; fileName:" + fileName);
                        if (fileName.endsWith("-F.jpg")) {
                            model.facePicPath = filePath;
                        }
                        if (fileName.endsWith("-L.bmp")) {
                            model.leftEyePicPath = filePath;
                        }
                        if (fileName.endsWith("-R.bmp")) {
                            model.rightEyePicPath = filePath;
                        }
                    }
                    if (count > 2) {
                        continue; //如果比较了3次，则进入下次循环
                    }
                }
                list.add(model); //获取到同时间的文件，加入集合
            }
        }
        return list;
    }

    /**
     * 图像采集的bean
     */
    public static class CaptureImgModel implements Serializable {
        public String time;
        public String leftEyePicPath;
        public String rightEyePicPath;
        public String facePicPath;

        public CaptureImgModel(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "CaptureImgModel{" +
                    "time='" + time + '\'' +
                    ", leftEyePicPath='" + leftEyePicPath + '\'' +
                    ", rightEyePicPath='" + rightEyePicPath + '\'' +
                    ", facePicPath='" + facePicPath + '\'' +
                    '}';
        }
    }


    /**
     * 删除旧文件夹，且仅保留最近三个文件夹
     */
    private static void compareStr() {
        //yyyy-MM-dd
        String[] array = new String[]{"2019-05-12", "2020-05-13", "2019-05-11", "2021-05-13", "2019-06-15"};
        Arrays.sort(array, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                //JDK1.7 在sort排序中重写的方法一定要满足:可逆比较
                if (lhs.compareTo(rhs) < 0) {
                    return 1;
                }
                if (lhs.compareTo(rhs) > 0) {
                    return -1;
                }
                return 0;
            }
        });
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
