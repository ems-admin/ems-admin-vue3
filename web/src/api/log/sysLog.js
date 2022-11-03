import request from "../../utils/request";

/**
 * 获取日志列表
 * @param params
 * @returns {AxiosPromise}
 */
export function getLogList(params){
    return request({
        url: '/sys/log/list',
        method: 'get',
        params
    })
}