/**
 * Created by lmy on 2017/12/13.
 */
import React, {Component} from 'react';
import {StyleSheet, View, Text, requireNativeComponent, findNodeHandle,UIManager,ReactNative} from 'react-native';
import PropTypes from 'prop-types';
const RCTDanmakuView = requireNativeComponent("RCTDanmakuView", DanmakuView);
export default class DanmakuView extends Component {
    constructor(props) {
        super(props);
        this.findNode = this.findNode.bind(this);
         this.dispatchCommand = this.dispatchCommand.bind(this);
         this.defualtParams = {text: '', padding: 5, isLive: true, time: 2000, fontSize: -1, color: ''}
         this.resume = this.resume.bind(this);
         this.pause = this.pause.bind(this);
         this.hide = this.hide.bind(this);
         this.show = this.show.bind(this);
         this.addDanmaku = this.addDanmaku.bind(this)
    }

    /**
     * 重启
     */
    resume() {
        this.dispatchCommand('resume')
    }

    /**
     * 暂停
     */
    pause() {
        this.dispatchCommand('pause')
    }

    /**
     * 隐藏
     */
    hide() {
        this.dispatchCommand('hide')
    }

    /**
     * 显示
     */
    show() {
        this.dispatchCommand('show')
    }

    /**
     * 发送数据
     * @param text 弹幕内容
     * @param padding 弹幕的padding
     * @param isLive  是否是直播
     * @param time   弹幕出现时间
     * @param fontSize 弹幕尺寸
     * @param color 弹幕颜色
     */
    addDanmaku({text = '', padding = 5, isLive = true, time = 2000, fontSize = -1, color = ''} = this.defualtParams) {
        let params = [text, padding, 0, isLive, time, fontSize, color];
        this.dispatchCommand('addDanmaku', params);
    }

    dispatchCommand(commandName, params) {
        UIManager.dispatchViewManagerCommand(this.findNode(), UIManager.RCTDanmakuView.Commands[commandName], params);
    }

    findNode() {

        return findNodeHandle(this.refs.danmakuView);
    }

    render() {
        const nativeProps = {...this.props}
        return (<RCTDanmakuView ref={'danmakuView'} {...nativeProps}/>)//测试这儿用ref=>this.danmaView=ref不能实现
    }
}

DanmakuView.propTypes = {
    speed:PropTypes.number,//设置弹幕速度，默认为1.2，值越小速度越快
    maxLines:PropTypes.number,//设置弹幕的最大行数，默认为5
    isOverlap:PropTypes.bool,//设置弹幕是否重叠
    ...View.propTypes
}
module.exports = DanmakuView
