import router from "./routers";
import store from "../store";
import {queryAllMenu} from "../api/menu/sysMenu";
import {errorMsg} from "../utils/message";

//  定义路由白名单
const whiteList = ['/login', '/401', '/404']

//  创建路由守卫
router.beforeEach((to, from, next) => {
    console.log('请求路由：' + to.path)
    //  如果已登录
    if (store.state.token){
        //  在用户手动切换到根路由时，强制返回
        //  避免路由跳出当前主页面
        if (to.path === '/'){
            router.go(-1)
        }
        //  如果重复登录
        if (to.path === '/login'){
            //  则直接跳转到主页
            next({path: '/Layout'})
        } else {
            //  如果没有拉取过用户菜单列表
            if (!store.state.isLoadMenu){
                loadMenus(next, to)
            //  如果已经拉取过
            } else {
                //  如果请求路由不存在于系统路由中
                if (!hashRoute(to)){
                    //  将缓存中的路由添加到系统路由中
                    addRoute()
                    //  再次判断是否存在于新的系统路由中
                    //  如果存在，则进行访问
                    if (hashRoute(to)){
                        next(to.fullPath)
                        //  不存在就跳转到404页面
                    } else {
                        next({path: '/404'})
                    }
                //  否则
                } else {
                    //  直接访问
                    next()
                }
            }
        }
    } else {
        //  如果在白名单中
        if (whiteList.indexOf(to.path) !== -1){
            //  则直接访问
            next()
        //  不存在就跳转到登录页面
        } else {
            next({path: '/login'})
        }
    }
})

/**
 * 拉取用户菜单列表
 * @param next
 * @param to
 */
export function loadMenus(next, to){
    queryAllMenu().then(res => {
        if (res.success){
            if (res.data.length > 0){
                //  将所有菜单缓存到store中
                store.dispatch('routerAction', res.data)
                //  修改拉取菜单状态
                store.dispatch('loadMenuAction', true)
                //  中断当前导航，执行新的导航
                next({...to, replace: true})
            }
        } else {
            errorMsg(res.msg)
        }
    })
}

/**
 * 判断路由是否已存在
 * @param to
 */
export function hashRoute(to){
    let find = router.getRoutes().find(item => item.path === to.path)
    return !!find
}

/**
 * 将拉取的路由添加到系统路由中
 */
export function addRoute(){
    let routers = store.state.routers
    if (routers && routers.length > 0){
        console.info(routers)
        routers.forEach(item => {
            if (item.path){
                router.addRoute('Layout', {
                    path: item.path,
                    name: item.name,
                    component: item.component != null ? () => import(`@/views/${item.component}`) : null
                })
            }
        })
    }
}