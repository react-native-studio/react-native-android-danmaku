# react-native-android-danmaku
## 第一步
工程目录下运行 npm install --save react-native-android-danmaku 或者 yarn add react-native-android-danmaku(已经安装了yarn)
## 第二步
运行 react-native link react-native-android-danmaku
## 第三部使用
在工程中导入：
`import DanmakuView from 'react-native-android-danmaku';`
注意在使用中时应设置组件的宽高;
## 属性：
### 1 speed 弹幕的速度
### 2 maxLines 弹幕的最大行数

## 方法
### pause（）暂停弹幕
### resume() 重启弹幕
### hide() 隐藏弹幕
### show() 显示弹幕
### addDanmaku(params) 发送弹幕
params为：`{text,
isLive,
fontSize,
padding,
color,
time}`
----
text:弹幕内容
isLive:是否为直播
fontSize：弹幕字体大小
padding：弹幕的内边距
color：弹幕颜色
time：弹幕出现时间


