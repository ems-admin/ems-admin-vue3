import request from "../../utils/request";

/**
 * 获取角色列表
 * @param params
 * @returns {AxiosPromise}
 */
export function getRoleList(params){
    return request({
        url: '/sys/role/table',
        method: 'get',
        params
    })
}

/**
 * 编辑角色
 * @param data
 * @returns {AxiosPromise}
 */
export function editRole(data){
    return request({
        url: '/sys/role/edit',
        method: 'post',
        data
    })
}

/**
 * 角色授权
 * @param data
 * @returns {AxiosPromise}
 */
export function authorizeRole(data){
    return request({
        url: '/sys/role/menu/edit',
        method: 'post',
        data
    })
}

/**
 * 删除角色
 * @param params
 * @returns {AxiosPromise}
 */
export function delRole(params){
    return request({
        url: '/sys/role/del',
        method: 'delete',
        params
    })
}

/**
 * 获取角色已授权菜单
 * @param params
 * @returns {AxiosPromise}
 */
export function getMenusByRoleId(params){
    return request({
        url: '/sys/role/menu/list',
        method: 'get',
        params
    })
}