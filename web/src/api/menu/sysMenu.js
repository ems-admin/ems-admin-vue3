import request from "../../utils/request";

/**
 * 拉取当前用户的所有菜单
 * @returns {AxiosPromise}
 */
export function queryAllMenu(){
    return request({
        url: '/sys/menu/all',
        method: 'get'
    })
}

/**
 * 获取当前登录用户菜单树
 * @returns {AxiosPromise}
 */
export function getMenuTree(){
    return request({
        url: '/sys/menu/tree',
        method: 'get'
    })
}

/**
 * 获取菜单列表
 * @param params
 * @returns {AxiosPromise}
 */
export function getMenuTable(params){
    return request({
        url: '/sys/menu/table',
        method: 'get',
        params
    })
}

/**
 * 编辑菜单
 * @param data
 * @returns {AxiosPromise}
 */
export function editMenu(data){
    return request({
        url: '/sys/menu/edit',
        method: 'post',
        data
    })
}

/**
 * 根据ID删除菜单
 * @param params
 * @returns {AxiosPromise}
 */
export function delMenu(params){
    return request({
        url: '/sys/menu/del',
        method: 'delete',
        params
    })
}


export function getPermission(){
    return request({
        url: '/sys/menu/permission',
        method: 'get'
    })
}
