打包AAR:
1、new module -> Android Library;
2、gradle 窗口 找到该 Android Library，执行 Task-assemble
3、在该 Android Library 的 build/outputs/aar/ 目录下即可看到生成的AAR 文件
   androidlib-debug.aar 、androidlib-release.aar