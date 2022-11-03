import request from "../../utils/request";

//  登录
export function login(data){
    return request({
        url: '/auth/login',
        method: 'post',
        data
    })
}