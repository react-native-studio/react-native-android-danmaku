## react-native-android-danmaku [![npm version](https://badge.fury.io/js/react-native-android-danmaku.svg)](https://badge.fury.io/js/react-native-android-danmaku)
## 第一步
工程目录下运行 npm install --save react-native-android-danmaku 或者 yarn add react-native-android-danmaku(已经安装了yarn)
## 第二步
运行 react-native link react-native-android-danmaku
## 第三部使用
在工程中导入：
```bash
import DanmakuView from 'react-native-android-danmaku';
<DanmakuView style={{flex:1}} speed={1.2} maxLines={5} ref={ref=>this.danmaView=ref}/>
this.danmakuView.pause(); //暂停弹幕
this.danmakuView.resume(); //重启弹幕
this.danmakuView.hide(); //隐藏弹幕
this.danmakuView.show(); //显示弹幕
this.danmakuView.addDanmaku({text:'000000',color:'red',padding:2,isLive:true,time:2000,fontSize:36})
//发送弹幕
```
<br/>
注意在使用中时应设置组件的宽高;
## 属性：
1 speed 弹幕的速度
2 maxLines 弹幕的最大行数
## 方法
pause（）暂停弹幕<br/>
resume() 重启弹幕<br/>
hide() 隐藏弹幕<br/>
show() 显示弹幕<br/>
addDanmaku(params) 发送弹幕
<br/>
params为：`{text,
isLive,
fontSize,
padding,
color,
time}`<br/>
text:弹幕内容
isLive:是否为直播
fontSize：弹幕字体大小
padding：弹幕的内边距
color：弹幕颜色
time：弹幕出现时间
## 示例
<!--![image](https://github.com/2534290808/react-native-android-danmaku/blob/master/images/Screenshot_1513176625.png)-->
<img src="https://github.com/2534290808/react-native-android-danmaku/blob/master/images/Screenshot_1513176625.png" width = "300"  alt="图片名称" align=center />
