import request from "../../utils/request";

//  登录
export function login(data){
    return request({
        url: '/auth/login',
        method: 'post',
        data
    })
}

//  获取验证码
export function getVerifyCode(params){
    return request({
        url: '/api/auth/code',
        method: 'get',
        params
    })
}