import request from "../../utils/request";

/**
 * 拉取当前用户的所有菜单
 * @returns {AxiosPromise}
 */
export function queryAllMenu(){
    return request({
        url: '/api/sys/menu/all',
        method: 'get'
    })
}

/**
 * 获取当前登录用户菜单树
 * @returns {AxiosPromise}
 */
export function getMenuTree(){
    return request({
        url: '/api/sys/menu/tree',
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
        url: '/api/sys/menu/table',
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
        url: '/api/sys/menu/edit',
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
        url: '/api/sys/menu/del',
        method: 'delete',
        params
    })
}

/**
 * 获取按钮权限列表
 * @returns {AxiosPromise}
 */
export function getPermission(){
    return request({
        url: '/api/sys/menu/permission',
        method: 'get'
    })
}


export function getMenuTreeSelect(){
    return request({
        url: '/api/sys/menu/select',
        method: 'get'
    })
}
