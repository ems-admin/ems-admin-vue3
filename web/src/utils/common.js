import store from "../store";
/**
 * 重置表彰
 * @param formName
 */

export function resetForm(formName){
    this.$refs[formName].resetFields()
}

/**
 * 判断是否拥有权限
 * @param per
 * @returns {boolean}
 */
export function hasPer(per){
    const permission = store.state.permission
    return permission.indexOf(per) > -1
}