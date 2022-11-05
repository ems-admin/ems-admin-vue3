import {useStore} from "../store";

const store = useStore()
/**
 * 重置表彰
 * @param ref
 */

export function resetForm(ref){
    ref.value.resetFields()
}

/**
 * 判断是否拥有权限
 * @param per
 * @returns {boolean}
 */
export function hasPer(per){
    const permission = store.permission
    return permission.indexOf(per) > -1
}