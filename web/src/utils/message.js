import {Message} from "element-ui";

/**
 * 成功消息
 * @param msg
 */
export function successMsg(msg){
    Message.success(msg)
}

/**
 * 错误消息
 * @param msg
 */
export function errorMsg(msg){
    Message.error(msg)
}

/**
 * 警告消息
 * @param msg
 */
export function warningMsg(msg){
    Message.warning(msg)
}

/**
 * 提示消息
 * @param msg
 */
export function infoMsg(msg){
    Message.info(msg)
}