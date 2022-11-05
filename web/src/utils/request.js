import axios from "axios";
import routers from '../router/routers'
import {useStore} from "../store";
import {errorMsg} from "./message";

const store = useStore()

//  创建axios实例
const instance = axios.create({
    baseURL: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_URL : 'http://localhost:8415',
    timeout: 60000   //  请求超时时间（毫秒）
})

//  request拦截器
instance.interceptors.request.use(
    config => {
        //  如果已登录
        if (store.token){
            //  在请求头添加token
            config.headers['Authorization'] = 'Bearer ' + store.token
        }
        //  统一请求类型json
        config.headers['Content-Type'] = 'application/json'
        return config
    },
    error => {
        Promise.reject(error)
    }
)

//  response拦截器
instance.interceptors.response.use(
    response => {
        //  请求成功，直接返回数据
        return response.data
    },
    error => {
        if (!error.response){
            errorMsg(error.message)
        } else {
            //  请求返回码
            let code;
            if (error.response){
                code = error.response.status
            }
            //  请求返回错误
            const data = error.response.data
            if (code){
                //  如果是未授权
                if (code === 401){
                    //  说明token过期，使用refreshToken对当前token进行刷新
                    const refresh = store.refreshToken
                    //  如果存在
                    if (refresh){
                        return againRequest(refresh, error)
                    //  否则
                    } else {
                        //  清空token
                        store.tokenAction(null)
                        //  并跳转到登录页面，进行重新登录
                        routers.push({
                            path: '/login',
                            query: {
                                backto: routers.currentRoute.fullPath
                            }
                        })
                    }
                    //  如果是没有权限
                } else if (code === 403){
                    //  直接跳转至401页面
                    routers.replace({path: '/401'})
                    //  如果是服务器异常或其他异常
                } else {
                    //  如果存在异常信息，显示异常信息
                    if (data){
                        errorMsg(data.detail)
                    }
                }
            } else {
                errorMsg('接口请求失败')
            }
        }
        return Promise.reject(error)
    }
)

/**
 * 重新请求
 * @param error
 * @returns {Promise<void>}
 */
async function againRequest(refresh, error){
    await refreshToken(refresh)
    const config = error.response.config
    config.headers['Authorization'] = 'Bearer ' + store.state.token
    const res = await axios.request(config)
    return res.data
}

/**
 * 刷新token
 * @param refresh
 * @param config
 */
export function refreshToken(refresh){
    //  刷新token
    return axios({
        url: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_BASE_URL : 'http://localhost:8415' + '/auth/refresh',
        method: 'put',
        headers: {
            Authorization: `Bearer ${refresh}`
        }
    }).then(res => {
        if (res.data.success){
            //  刷新token
            store.tokenAction(res.data.data)
        } else {
            errorMsg(res.msg)
            //  清空token
            store.tokenAction(null)
        }
    })
}

export default instance