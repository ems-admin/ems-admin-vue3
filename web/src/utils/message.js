import {ElMessage} from "element-plus";

/**
 * 成功消息
 * @param msg
 */
export function successMsg(msg){
    ElMessage.success(msg)
}

/**
 * 错误消息
 * @param msg
 */
export function errorMsg(msg){
    ElMessage.error(msg)
}

/**
 * 警告消息
 * @param msg
 */
export function warningMsg(msg){
    ElMessage.warning(msg)
}

/**
 * 提示消息
 * @param msg
 */
export function infoMsg(msg){
    ElMessage.info(msg)
}